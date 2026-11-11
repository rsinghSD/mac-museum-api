// Human-friendly formatting of model specs. null always means "unknown".

const usd = new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD', maximumFractionDigits: 2 });

export const UNKNOWN = 'Unknown';

export const CPU_LABELS = {
  MOS_6502: 'MOS 6502',
  M68K: 'Motorola 68k',
  ARM: 'ARM (licensed)',
  POWERPC: 'PowerPC',
  INTEL_X86: 'Intel x86',
  APPLE_SILICON: 'Apple silicon',
};

export function price(value) {
  if (value === null || value === undefined) return UNKNOWN;
  const n = Number(value);
  return usd.format(n).replace(/\.00$/, '');
}

export function clock(mhz) {
  if (mhz === null || mhz === undefined) return UNKNOWN;
  if (mhz >= 1000) return `${trim(mhz / 1000)} GHz`;
  return `${mhz} MHz`;
}

export function ram(kb) {
  if (kb === null || kb === undefined) return UNKNOWN;
  if (kb >= 1024 * 1024) return `${trim(kb / (1024 * 1024))} GB`;
  if (kb >= 1024) return `${trim(kb / 1024)} MB`;
  return `${kb} KB`;
}

export function cpuLabel(architecture) {
  return CPU_LABELS[architecture] ?? architecture;
}

/** "×29.1" style multiplier, or null when unknown. */
export function ratio(value) {
  if (value === null || value === undefined) return null;
  return `×${trim(value)}`;
}

function trim(n) {
  return Number.isInteger(n) ? String(n) : n.toFixed(2).replace(/0+$/, '').replace(/\.$/, '');
}

/** Local date as YYYY-MM-DD, used for a stable "Mac of the day". */
export function today(date = new Date()) {
  const pad = (n) => String(n).padStart(2, '0');
  return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())}`;
}
