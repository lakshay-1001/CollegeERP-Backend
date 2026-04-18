/**
 * Single place for API base URL. Set at build time via VITE_API_BASE_URL (see .env.*).
 * - Dev: use full URL (e.g. http://localhost:8080) so DevTools shows the real backend URL; CORS must allow the Vite origin.
 * - Prod same-origin: empty string → paths like "/auth/login" hit the same host as the SPA.
 * - Prod split hosting: https://api.yourdomain.com (no trailing slash).
 */
const rawBase = import.meta.env.VITE_API_BASE_URL ?? "";

export function apiBaseUrl(): string {
  return String(rawBase).replace(/\/$/, "");
}

/** Path must start with "/" e.g. apiUrl("/auth/login") */
export function apiUrl(path: string): string {
  const p = path.startsWith("/") ? path : `/${path}`;
  const base = apiBaseUrl();
  if (!base) return p;
  if (base.startsWith("http://") || base.startsWith("https://")) {
    return `${base}${p}`;
  }
  return `${base}${p}`;
}

export async function readJson<T = unknown>(res: Response): Promise<T> {
  const text = await res.text();
  if (!text) return {} as T;
  try {
    return JSON.parse(text) as T;
  } catch {
    throw new Error(`Expected JSON but got: ${text.slice(0, 120)}…`);
  }
}
