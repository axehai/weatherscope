# WeatherScope

WeatherScope is a lightweight weather data service designed to expose a **stable weather data contract** while integrating with external weather providers.

The project follows **Layered Architecture with Domain-Driven Design (DDD)** principles and is built around **use cases** that encapsulate application behavior.

---

# V1 Scope

The first version focuses on delivering **core current weather data**.

### Included

* Current weather conditions
* Temperature
* Humidity
* Wind speed
* Wind direction
* Precipitation
* Weather condition description

### Excluded (Out of Scope for v1)

* Air Quality Index (AQI)
* Historical weather
* Long-range forecasts
* Multi-provider failover

AQI was intentionally removed due to provider pricing and API rate limits.

---

# Architecture

The system follows **Layered Architecture combined with Domain-Driven Design**.

```
src/
  domain/
  use-cases/
  services/
  providers/
  contracts/
```

### Domain

Contains **core business models and logic**.

Examples:

* Weather
* Location
* Measurement units

Domain objects should remain **independent of external providers**.

---

### Use Cases

Use cases define **application behavior**.

Examples:

```
GetCurrentWeather
RefreshWeatherCache
```

Each use case:

* orchestrates domain logic
* calls services/providers
* returns contract responses

Use cases are the **primary unit testing target**.

---

### Services

Application services coordinate interactions between:

* providers
* domain models
* caching policies

Services should contain **minimal business logic**.

---

### Providers

Providers integrate with external weather APIs.

Example structure:

```
providers/
  tomorrow/
    tomorrow-client.ts
    tomorrow-mapper.ts
```

Provider responses are mapped into **domain models**.

---

### Contracts

Contracts define the **stable response structure** returned by the API.

Example:

```
WeatherResponseV1
```

Contracts should change **very rarely** once published.

---

# Testing Strategy

The project follows **use-case driven testing**.

Each feature is developed by:

1. Writing the use case
2. Writing unit tests for the use case
3. Implementing supporting services/providers

Example:

```
use-cases/
  get-current-weather.ts
  get-current-weather.test.ts
```

Tests validate:

* domain behavior
* use case orchestration
* provider mapping

External API calls should always be **mocked in tests**.

---

# Provider Rate Limits

Current provider limits:

* **3 requests per second**
* **25 requests per hour**
* **500 requests per day**

These constraints will require:

* caching
* refresh TTL policies
* request deduplication

---

# Development Workflow

Development is **issue-driven**.

Typical flow:

1. Create an issue
2. Create a branch
3. Implement the feature
4. Open a pull request
5. Automated PR review runs

Branch naming convention:

```
type/<issue-number>-<short-description>
```

Examples:

```
feature/5-domain-models
fix/11-readme-scope
```

---

# Pull Request Reviews

Pull requests are automatically reviewed by:

CodeRabbit

It provides:

* PR summaries
* bug detection
* improvement suggestions

---

# Local Development

Clone the repository:

```
git clone <repo-url>
cd weatherscope
```

Install dependencies:

```
npm install
```

Run the project:

```
npm run dev
```

---

# Future Scope

Planned improvements after v1:

* Air Quality (AQI)
* Forecast APIs
* Historical weather
* Multi-provider failover
* Advanced caching layer
* Rate-limit aware scheduler

---

# License

MIT
