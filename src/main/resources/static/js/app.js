import { api, ApiError } from './api.js';
import { h, clear, debounce } from './dom.js';
import * as fmt from './format.js';

// ------------------------------------------------------------------ state ---

const $ = (selector) => document.querySelector(selector);

const state = {
  view: 'catalog',
  page: 0,
  totalPages: 0,
  allModels: null, // lazily loaded for the compare pickers
};

const VIEWS = ['catalog', 'timeline', 'compare'];
const DEFAULT_COMPARE = ['macintosh-128k', 'macbook-air-m1'];

// ---------------------------------------------------------------- routing ---
// #catalog | #timeline | #compare=a,b | #model=slug (opens on top of the current view)

function parseHash() {
  const raw = decodeURIComponent(location.hash.slice(1));
  const [key, value = ''] = raw.split('=');
  if (key === 'model' && value) return { slug: value };
  if (key === 'compare') return { view: 'compare', compare: value.split(',').filter(Boolean) };
  return { view: VIEWS.includes(key) ? key : 'catalog' };
}

async function route() {
  const { view, slug, compare } = parseHash();
  if (view) {
    showView(view);
    if (detail.open) detail.close();
  }
  if (view === 'timeline') await loadTimeline();
  if (view === 'compare') await loadCompare(compare);
  if (slug) await showDetail(slug);
}

function navigate(hash) {
  history.pushState({ fromApp: true }, '', hash);
  route();
}

function showView(view) {
  state.view = view;
  state.viewHash = location.hash.startsWith('#model=') ? `#${view}` : (location.hash || '#catalog');
  document.querySelectorAll('[data-view]').forEach((el) => {
    el.hidden = el.dataset.view !== view;
  });
  document.querySelectorAll('[data-view-link]').forEach((link) => {
    if (link.dataset.viewLink === view) link.setAttribute('aria-current', 'page');
    else link.removeAttribute('aria-current');
  });
}

// ------------------------------------------------------------------ alert ---

function showAlert(message) {
  $('#alert-text').textContent = message;
  $('#alert').hidden = false;
  $('#alert-ok').focus();
}

function handleError(error) {
  console.error(error);
  showAlert(error instanceof ApiError
    ? error.message
    : 'The museum could not be reached. Check your connection and try again.');
}

// ---------------------------------------------------------- shared pieces ---

function modelMeta(model) {
  return `${model.releaseYear} · ${model.family.name}`;
}

/** Badge for non-Apple machines (OS/2-era contemporaries), so they are never mistaken for Apple products. */
function makerChip(model) {
  if (!model.manufacturer || model.manufacturer === 'Apple') return null;
  return h('span', { class: 'chip chip-maker', title: 'Not an Apple product: shown for context' }, `${model.manufacturer} · contemporary`);
}

function cpuChip(model) {
  return h('span', { class: 'chip', dataset: { cpu: model.cpuArchitecture } }, fmt.cpuLabel(model.cpuArchitecture));
}

function modelLink(slug) {
  return () => navigate(`#model=${encodeURIComponent(slug)}`);
}

// --------------------------------------------------------- mac of the day ---

async function loadMacOfTheDay(shuffle = false) {
  const box = $('#motd');
  try {
    const model = await api.random(shuffle ? undefined : fmt.today());
    $('#motd-title').textContent = shuffle ? 'Random Mac' : 'Mac of the Day';
    clear(box).append(
      h('h3', {}, model.name),
      h('p', { class: 'muted' }, `${modelMeta(model)} · ${fmt.cpuLabel(model.cpuArchitecture)}`),
      h('p', {}, model.summary),
      h('div', { class: 'motd-actions' },
        h('button', { type: 'button', class: 'btn btn-default', onclick: modelLink(model.slug) }, 'Open'),
        h('button', { type: 'button', class: 'btn', onclick: () => loadMacOfTheDay(true) }, 'Shuffle')),
    );
  } catch (error) {
    clear(box).append(h('p', { class: 'muted' }, 'No Mac today — the catalog is empty.'));
    if (!(error instanceof ApiError && error.status === 404)) handleError(error);
  }
}

// ---------------------------------------------------------------- catalog ---

const filters = $('#filters');

function filterParams() {
  const data = new FormData(filters);
  return {
    q: data.get('q')?.trim(),
    family: data.get('family'),
    cpu: data.get('cpu'),
    fromYear: data.get('fromYear'),
    toYear: data.get('toYear'),
    sort: data.get('sort'),
    page: state.page,
    size: 24,
  };
}

async function loadCatalog() {
  const status = $('#catalog-status');
  status.textContent = 'Loading…';
  try {
    const result = await api.models(filterParams());
    state.totalPages = result.totalPages;
    renderCards(result.content);
    status.textContent = result.totalElements === 1 ? '1 model' : `${result.totalElements} models`;
    $('#page-info').textContent = result.totalPages ? `Page ${result.page + 1} of ${result.totalPages}` : '';
    $('#prev-page').disabled = result.first;
    $('#next-page').disabled = result.last;
  } catch (error) {
    status.textContent = '';
    handleError(error);
  }
}

function renderCards(models) {
  const list = clear($('#cards'));
  if (models.length === 0) {
    list.append(h('li', { class: 'muted' }, 'No models match these filters.'));
    return;
  }
  for (const model of models) {
    list.append(h('li', {},
      h('button', { type: 'button', class: 'card', onclick: modelLink(model.slug) },
        h('span', { class: 'card-title' }, model.name),
        h('span', { class: 'card-meta' }, modelMeta(model)),
        h('span', { class: 'card-meta' }, `${model.cpuName} · ${fmt.clock(model.clockSpeedMhz)}`),
        cpuChip(model),
        makerChip(model))));
  }
}

async function loadFamilies() {
  try {
    const families = await api.families();
    const select = filters.elements.family;
    for (const family of families) {
      select.append(h('option', { value: family.slug }, `${family.name} (${family.modelCount})`));
    }
  } catch (error) {
    handleError(error);
  }
}

function setupCatalog() {
  const reload = () => {
    state.page = 0;
    loadCatalog();
  };
  filters.addEventListener('input', debounce(reload, 300));
  filters.addEventListener('change', reload);
  filters.addEventListener('submit', (event) => event.preventDefault());
  filters.addEventListener('reset', () => setTimeout(reload));
  $('#prev-page').addEventListener('click', () => {
    state.page = Math.max(0, state.page - 1);
    loadCatalog();
  });
  $('#next-page').addEventListener('click', () => {
    state.page = Math.min(state.totalPages - 1, state.page + 1);
    loadCatalog();
  });
}

// ----------------------------------------------------------------- detail ---

const detail = $('#detail');

async function showDetail(slug) {
  try {
    const model = await api.model(slug);
    $('#detail-title').textContent = model.name;
    clear($('#detail-body')).append(
      h('p', {}, model.summary),
      h('table', { class: 'specs' },
        h('tbody', {},
          specRow('Released', model.releaseYear),
          specRow('Maker', model.manufacturer ?? 'Apple'),
          specRow('Family', model.family.name),
          specRow('Architecture', fmt.cpuLabel(model.cpuArchitecture)),
          specRow('Processor', model.cpuName),
          specRow('Clock speed', fmt.clock(model.clockSpeedMhz)),
          specRow('Base RAM', fmt.ram(model.baseRamKb)),
          specRow('Launch price', fmt.price(model.launchPriceUsd)))),
      h('div', { class: 'detail-actions' },
        h('button', {
          type: 'button',
          class: 'btn btn-default',
          onclick: () => navigate(`#compare=${compareTargets(model.slug).join(',')}`),
        }, 'Compare…'),
        h('button', { type: 'button', class: 'btn', onclick: () => detail.close() }, 'Close')),
    );
    if (!detail.open) detail.showModal();
  } catch (error) {
    handleError(error);
  }
}

function specRow(label, value) {
  return h('tr', {}, h('th', { scope: 'row' }, label), h('td', {}, value));
}

function compareTargets(slug) {
  const other = DEFAULT_COMPARE.find((s) => s !== slug);
  return [slug, other];
}

function setupDetail() {
  $('#detail-close').addEventListener('click', () => detail.close());
  detail.addEventListener('click', (event) => {
    if (event.target === detail) detail.close(); // click on backdrop
  });
  detail.addEventListener('close', () => {
    if (!parseHash().slug) return;
    if (history.state?.fromApp) history.back();
    else history.replaceState(null, '', state.viewHash || `#${state.view}`);
  });
}

// --------------------------------------------------------------- timeline ---

let timelineLoaded = false;

async function loadTimeline() {
  if (timelineLoaded) return;
  try {
    const { eras } = await api.timeline();
    const list = clear($('#timeline'));
    for (const era of eras) {
      const years = era.startYear === era.endYear ? `${era.startYear}` : `${era.startYear}–${era.endYear}`;
      list.append(h('li', { class: 'era' },
        h('h3', {}, era.label),
        h('span', { class: 'era-years' }, `${years} · ${era.modelCount} models`),
        h('p', {}, era.description),
        h('ol', {}, era.models.map((m) => h('li', {},
          h('button', { type: 'button', class: 'era-entry', onclick: modelLink(m.slug) },
            h('b', {}, m.releaseYear),
            h('span', {}, m.name)))))));
    }
    timelineLoaded = true;
  } catch (error) {
    handleError(error);
  }
}

// ---------------------------------------------------------------- compare ---

const compareForm = $('#compare-form');

async function loadCompare(requested = []) {
  try {
    if (!state.allModels) {
      state.allModels = await api.allModels();
      fillPicker(compareForm.elements.a);
      fillPicker(compareForm.elements.b);
    }
    const known = new Set(state.allModels.map((m) => m.slug));
    const [a, b] = requested.length === 2 && requested.every((s) => known.has(s))
      ? requested
      : DEFAULT_COMPARE.filter((s) => known.has(s)).concat(state.allModels.map((m) => m.slug)).slice(0, 2);
    compareForm.elements.a.value = a;
    compareForm.elements.b.value = b;
    await renderComparison();
  } catch (error) {
    handleError(error);
  }
}

function fillPicker(select) {
  const byFamily = new Map();
  for (const m of state.allModels) {
    if (!byFamily.has(m.family.name)) byFamily.set(m.family.name, []);
    byFamily.get(m.family.name).push(m);
  }
  const families = [...byFamily.keys()].sort();
  for (const family of families) {
    select.append(h('optgroup', { label: family },
      byFamily.get(family)
        .sort((x, y) => x.releaseYear - y.releaseYear)
        .map((m) => h('option', { value: m.slug }, `${m.name} (${m.releaseYear})`))));
  }
}

async function renderComparison() {
  const a = compareForm.elements.a.value;
  const b = compareForm.elements.b.value;
  const out = clear($('#compare-result'));
  history.replaceState(history.state, '', `#compare=${a},${b}`);
  state.viewHash = location.hash;
  if (a === b) {
    out.append(h('p', { class: 'muted' }, 'Pick two different models.'));
    return;
  }
  const { models: [left, right], comparisons: [delta] } = await api.compare([a, b]);
  out.append(
    h('table', { class: 'specs compare-table' },
      h('thead', {}, h('tr', {},
        h('th', { scope: 'col' }, ''),
        h('th', { scope: 'col' }, left.name),
        h('th', { scope: 'col' }, right.name))),
      h('tbody', {},
        compareRow('Year', left.releaseYear, right.releaseYear, String),
        compareRow('Maker', left.manufacturer ?? 'Apple', right.manufacturer ?? 'Apple'),
        compareRow('Family', left.family.name, right.family.name),
        compareRow('CPU', fmt.cpuLabel(left.cpuArchitecture), fmt.cpuLabel(right.cpuArchitecture)),
        compareRow('Processor', left.cpuName, right.cpuName),
        compareRow('Clock', left.clockSpeedMhz, right.clockSpeedMhz, fmt.clock),
        compareRow('RAM', left.baseRamKb, right.baseRamKb, fmt.ram),
        compareRow('Price', left.launchPriceUsd, right.launchPriceUsd, fmt.price, false))),
    h('ul', { class: 'compare-summary' }, summaryLines(left, right, delta).map((line) => h('li', {}, line))),
  );
}

/**
 * One table row. When `higherWins` and both values are numbers, the bigger one is highlighted.
 */
function compareRow(label, x, y, format = String, higherWins = true) {
  const numeric = typeof x === 'number' && typeof y === 'number';
  const winX = higherWins && numeric && x > y;
  const winY = higherWins && numeric && y > x;
  return h('tr', {},
    h('th', { scope: 'row' }, label),
    h('td', { class: winX ? 'win' : null }, format(x)),
    h('td', { class: winY ? 'win' : null }, format(y)));
}

function summaryLines(left, right, delta) {
  const lines = [];
  const years = Math.abs(delta.yearsApart);
  if (years > 0) {
    lines.push(`${right.name} came out ${years} year${years === 1 ? '' : 's'} ${delta.yearsApart > 0 ? 'later' : 'earlier'}.`);
  }
  const clock = multiple(left, right, 'clockSpeedMhz');
  if (clock) lines.push(`${clock.bigger.name} has ${clock.times} the clock speed of the ${clock.smaller.name}.`);
  const memory = multiple(left, right, 'baseRamKb');
  if (memory) lines.push(`${memory.bigger.name} has ${memory.times} the base RAM of the ${memory.smaller.name}.`);
  if (delta.priceDifferenceUsd !== null) {
    const diff = Number(delta.priceDifferenceUsd);
    lines.push(diff === 0
      ? 'Both launched at the same price.'
      : `${right.name} cost ${fmt.price(Math.abs(diff))} ${diff > 0 ? 'more' : 'less'} at launch.`);
  }
  return lines;
}

/** "Bigger has ×N the X of smaller", always phrased with N ≥ 1. Null if unknown or equal. */
function multiple(left, right, field) {
  const x = left[field];
  const y = right[field];
  if (!x || !y || x === y) return null;
  const [bigger, smaller] = x > y ? [left, right] : [right, left];
  return { bigger, smaller, times: fmt.ratio(Math.round((bigger[field] / smaller[field]) * 10) / 10) };
}

function setupCompare() {
  compareForm.addEventListener('change', () => renderComparison().catch(handleError));
  $('#swap').addEventListener('click', () => {
    const { a, b } = compareForm.elements;
    [a.value, b.value] = [b.value, a.value];
    renderComparison().catch(handleError);
  });
}

// ------------------------------------------------------------------ clock ---

function startClock() {
  const clock = $('#clock');
  const tick = () => {
    clock.textContent = new Date().toLocaleTimeString([], { hour: 'numeric', minute: '2-digit' });
  };
  tick();
  setInterval(tick, 30_000);
}

// ------------------------------------------------------------------- boot ---

function init() {
  $('#alert-ok').addEventListener('click', () => { $('#alert').hidden = true; });
  setupCatalog();
  setupDetail();
  setupCompare();
  startClock();
  // popstate fires for back/forward and for in-page #links, so one listener covers both.
  window.addEventListener('popstate', route);
  loadFamilies();
  loadMacOfTheDay();
  loadCatalog();
  route();
}

init();
