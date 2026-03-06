# WeatherScope

[![Platform](https://img.shields.io/badge/platform-Android-3DDC84)](#)
[![UI](https://img.shields.io/badge/UI-Compose-7F52FF)](#)
[![Status](https://img.shields.io/badge/status-v1%20scope%20locked-0EA5E9)](#)

A tiny weather app with *strict v1 scope*: **7-day forecast + highlights + AQ**, built to learn real-world API plumbing (geocoding + caching + quota safety) without turning into a meteorology PhD.

---

## Data Sources

**Weather + Air Quality:** Tomorrow.io
- Daily forecast (7 days)
- Highlights: **wind, real feel, rain chance %, UV**
- Air quality: **AQI, PM2.5, NO₂**

**Geocoding (search → lat/lon):** Open-Meteo Geocoding API
- Type `"del"` → list of places → select → store `lat/lon`

**Icons:** Local assets
- Weather condition icons are bundled locally (no remote icon URLs)
- Daily rows use **day** icons
- Unknown codes → fallback icon
- If using an external icon pack: add attribution in Credits/About

---

## v1 Scope (Locked)

### Forecast
- Show **next 7 days** for the **active location**
- Each day includes:
    - date
    - min / max temperature
    - rain chance (%)
    - condition icon (local)

### Highlights (single group)
- Wind
- RealFeel (apparent temperature)
- Rain chance (%)
- UV index
- Air Quality: **AQI + PM2.5 + NO₂**

### Location
- **Single active location** (v1)
- **Default on first install:** New Delhi (hardcoded lat/lon)
- On launch: show **cached data immediately**, then refresh via TTL
- Location can be set by:
    - GPS (optional)
    - Search (geocoding)

---

## Location Permission Flow

- If location permission is **not granted** on launch:
    - land on a dedicated **rationale screen** (with animation + explanation)
    - user can grant permission or continue without it
- If permission is **denied**:
    - GPS entry point is **disabled everywhere**
    - app continues using last active location (or default New Delhi)

---

## Caching & Quota Safety

- Cache per active location:
    - 7-day forecast
    - highlights
    - air quality
- Refresh policy (TTL):
    - forecast: **6–12 hours**
    - air quality: **1–3 hours** (or same as forecast to simplify)
- On offline / API failure / rate limit:
    - show cached data + “last updated”
    - show a non-blocking error indicator

---

## Out of Scope (v1)

Hourly forecast, “live now” screen, alerts/notifications, maps/radar, widgets, multiple saved locations, accounts/sync.

---

## v2 Hooks

Multi-location favorites, hourly forecast, widgets, alerts, provider fallback.

---

## Credits

- Weather & Air Quality data: Tomorrow.io
- Geocoding: Open-Meteo
- Icon attribution (only if using a licensed pack): listed in Credits/About in-app