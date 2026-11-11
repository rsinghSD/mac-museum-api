// Thin client for the Mac Museum REST API. Every call resolves to parsed JSON or
// rejects with an ApiError carrying the server's RFC 7807 problem detail.

const BASE = 'api/v1';

export class ApiError extends Error {
  constructor(problem, status) {
    super(problem?.detail || problem?.title || `Request failed (${status})`);
    this.status = status;
    this.problem = problem;
  }
}

async function get(path, params = {}) {
  const query = new URLSearchParams();
  for (const [key, value] of Object.entries(params)) {
    if (value !== undefined && value !== null && value !== '') query.append(key, value);
  }
  const qs = query.toString();
  const response = await fetch(`${BASE}${path}${qs ? `?${qs}` : ''}`, {
    headers: { Accept: 'application/json, application/problem+json' },
  });
  const body = await response.json().catch(() => null);
  if (!response.ok) throw new ApiError(body, response.status);
  return body;
}

export const api = {
  models: (params) => get('/models', params),
  model: (slug) => get(`/models/${encodeURIComponent(slug)}`),
  families: () => get('/families'),
  timeline: () => get('/timeline'),
  compare: (slugs) => get('/compare', { slugs: slugs.join(',') }),
  random: (day) => get('/random', { day }),

  /** Fetches every model by walking all pages (the catalog is small). */
  async allModels() {
    const all = [];
    for (let page = 0; ; page++) {
      const result = await get('/models', { page, size: 100, sort: 'name,asc' });
      all.push(...result.content);
      if (result.last) return all;
    }
  },
};
