# WeatherScope

Android weather app. Single screen. Clean Architecture (domain / data / presentation).
Package: `com.axehai.weatherscope` | Min SDK 29 | Kotlin + Jetpack Compose + Material3

## Stack
- DI: Hilt | HTTP: Ktor + OkHttp | Storage: DataStore | Async: Coroutines + Flow
- Serialization: kotlinx.serialization | Remote: Open-Meteo (forecast + geocoding)

## Architecture Rules
- Domain layer is framework-free. No Android imports.
- Repositories: interfaces in domain, implementations in data.
- DTOs and storage models never leak into presentation.
- `Resource` sealed class is the standard async result wrapper.
- Use cases are the primary unit-test targets.
- Inject coroutine dispatchers (never hardcode Dispatchers.IO/Main in testable code).

## v1 Scope (locked)
One screen: featured weather card + highlights card + 7-day forecast card + search bar.
Location sources: DEFAULT (New Delhi hardcoded), DEVICE (GPS), SEARCH (geocoding).
No AQI. No multi-screen. No user accounts.

## Issue Board
GitHub project: `axehai/weatherscope` — "WeatherScope — v1" (project #4, owner: axehai)
Run `gh project item-list 4 --owner axehai --format json` for current status.

## How I'm Used Here
- Issue scoping: what needs to be done, what to watch out for, cross-issue dependencies
- Issue alignment: adjusting phrasing/scope to match practical dev order
- Targeted help when stuck — not driving development

## Conventions
- One issue = one PR, branch named `issue/<number>-short-desc`
- Acceptance criteria in issues are the source of truth for done/not-done
- Don't add comments, docstrings, or handling for things not in scope
- Inject coroutine dispatchers (never hardcode Dispatchers.IO/Main in testable code)
