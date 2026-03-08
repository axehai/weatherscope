# 🌦️ Weatherscope

<p align="center">
<img src="https://img.shields.io/badge/Kotlin-1.9+-7F52FF?logo=kotlin&logoColor=white" alt="Kotlin 1.9+"/>
<img src="https://img.shields.io/badge/Jetpack%20Compose-UI-4285F4?logo=android&logoColor=white" alt="Jetpack Compose UI"/>
<img src="https://img.shields.io/badge/Min%20SDK-24-3DDC84?logo=android&logoColor=white" alt="Min SDK 24"/>
<img src="https://img.shields.io/github/actions/workflow/status/axehai/weatherscope/android-ci.yml?label=Build&logo=githubactions" alt="Build Status"/>
</p>

**Weatherscope** is a modern Android application built with **Jetpack Compose** that demonstrates how to structure scalable Android apps using **Clean Architecture** and modern Android development practices.

The goal of this project is not just building a weather UI, but showcasing **production-style architecture, separation of concerns, and maintainable code structure**.

---

# 🚀 Tech Stack

- Kotlin
- Coroutines & Flow
- Jetpack Compose
- Dagger Hilt
- Retrofit & OkHttp
- Coil
- GitHub Actions (CI)

---

# 🧱 Architecture

The project follows **Clean Architecture** with **Unidirectional Data Flow (UDF)**.

Core business logic lives in the **Domain layer**, ensuring it stays independent from both UI frameworks and data implementations.

```
Presentation ──► Domain ◄── Data
```

This structure ensures:

- UI does not know how data is fetched
- Data sources can be swapped without affecting business logic
- Business logic remains testable and framework-independent

---

# 📂 Project Structure

```
app/
├── data/
│   ├── remote/         # Retrofit API interfaces & DTOs
│   ├── repository/     # Concrete repository implementations
│   └── mapper/         # DTO ↔ Domain model conversions
│
├── domain/
│   ├── model/          # Pure Kotlin domain entities
│   ├── repository/     # Repository interfaces
│   └── use_case/       # Business logic units
│
├── presentation/
│   ├── feature/        # Feature modules (UI state, ViewModel, screens)
│   ├── components/     # Reusable Compose UI components
│   └── theme/          # Material3 theming
│
└── di/
    └── modules/        # Hilt dependency modules
```

---

# 🔄 Example Data Flow

A typical request in the application flows like this:

```
WeatherScreen
      │
      ▼
WeatherViewModel
      │
      ▼
GetWeatherUseCase
      │
      ▼
WeatherRepository
      │
      ▼
WeatherApi (Retrofit)
```

Each layer only depends on the layer **closer to the domain**, enforcing a strict separation of responsibilities.

---

# 🧪 CI Pipeline

The repository uses **GitHub Actions** to run automated checks on pull requests:

- Project build validation
- Unit test execution
- Static analysis (if configured)

This ensures the `main` branch remains stable.

---

# Testing Strategy

The project follows **use-case-driven testing**.

Each feature is developed by:

1. Writing the use case
2. Writing unit tests for the use case
3. Implementing supporting services/providers

Example:

```text
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

# Development Workflow

Development is **issue-driven**.

Typical flow:

1. Create an issue
2. Create a branch
3. Implement the feature
4. Open a pull request
5. Automated PR review runs

Branch naming convention:

```text
type/<issue-number>-<short-description>
```

Examples:

```text
feature/5-domain-models
fix/11-cache-ttl
docs/15-update-readme
```

---

# Pull Request Reviews

Pull requests are automatically reviewed by CodeRabbit.

It provides:

* PR summaries
* potential bug detection
* improvement suggestions

---

# Local Development

Clone the repository:

```bash
git clone https://github.com/axehai/weatherscope.git
```

Open the project in **Android Studio** and run it on an emulator or a physical device.

---

# 📄 License

MIT License
