# Android Architectural Standards

## 1. Layering Rules
- **Domain Layer:** Must be pure Kotlin. No Android dependencies.
- **Data Layer:** Use Repository pattern. Hide Room/Retrofit details.
- **UI Layer:** Compose only. Access data via ViewModels, never Repositories.

## 2. Dependency Injection (Hilt)
- Always use constructor injection.
- Do not use `EntryPoint` unless absolutely necessary.

## 3. State & Concurrency
- Use `StateFlow` for UI state.
- Use `collectAsStateWithLifecycle()` in Composables.
- Use `viewModelScope` for launching coroutines.

## 4. Testing Requirements
- New ViewModels must have unit tests in `src/test`.
- Use **Turbine** for Flow verification.
- Use **Mockito** for mocking dependencies.