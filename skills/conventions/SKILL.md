---
name: conventions
description: Barter Trader coding conventions — Java, XML, testing, and architecture patterns. Use when writing or reviewing code in this project.
user-invocable: true
allowed-tools: Read, Grep, Glob
metadata:
  title: Barter Trader Conventions
  category: development
  order: 1
---

# Barter Trader Conventions

## Java

- **Class names**: `UpperCamelCase`
- **Methods and variables**: `lowerCamelCase`
- **Constants**: `UPPER_CASE`

## XML Resources

- **File names**: `lower_underscore_case`
- **Style names**: `UpperCamelCase`
- **Layout files**: `container_name` — the container type followed by the class name in lower_underscore_case. Example: `LoginFragment.java` → `fragment_login.xml`
- **Element IDs**: `name_container_element_name` — the parent name, container type, element type, and purpose. Example: Username `TextView` in `LoginFragment` → `@id/login_fragment_text_username`
- **Navigation fragment IDs**: `UpperCamelCase` — matches Java class name. Example: `LoginFragment.java` → `@id/LoginFragment`
- **String resources**: `type_name` — prefixed with one of:
  - `error_` — error messages
  - `message_` — informational text
  - `actions_` — interactive element labels
  - `title_` — UI headings and labels

## Testing

### JUnit (Unit Tests)

- Class naming: `ClassNameUnitTest` — Example: `FormValidatorTools.java` → `FormValidatorToolsUnitTest.java`
- Method naming: `lowerCamelCase` describing expected behavior
- Location: `app/src/test/`

### Espresso (Instrumented Tests)

- Class naming: `ClassNameInstrumentedTest` — Example: `LoginFragment.java` → `LoginFragmentInstrumentedTest.java`
- Method naming: `lowerCamelCase` describing expected behavior
- Location: `app/src/androidTest/`
- Test credentials: use `BuildConfig.TEST_EMAIL` / `BuildConfig.TEST_PASSWORD` — never hardcode

## Architecture Patterns

### Clean Architecture + MVVM

Flow: `View` → `ViewModel` → `UseCase` → `Repository` → `DataSource`

| Layer | Package | Contains |
|---|---|---|
| Presentation | `presentation/view/` | Fragments (View) |
| Presentation | `presentation/view_model/` | ViewModels |
| Presentation | `presentation/adapter/` | RecyclerView Adapters |
| Domain | `domain/use_case/` | Use Cases (business logic) |
| Domain | `domain/model/` | Request models |
| Domain | `domain/repository/` | Repository interfaces |
| Data | `data/repository/` | Repository implementations |
| Data | `data/data_source/` | Firebase data sources |
| Data | `data/model/` | Response/Firestore models |
| DI | `di/` | Factory classes |
| Utils | `utils/` | Shared utilities |

### Key Abstractions

- **`Resource<T>`**: Wraps async results with `Status` enum (`PENDING`, `FULFILLED`, `REJECTED`). Use this to propagate async state from ViewModel to View.
- **`LiveEvent<T>`**: Single-fire `MutableLiveData`. Use for one-shot UI actions: toasts, navigation, snackbars. Consumed on first observation after `setValue`.
- **`SingleTaskHandler`**: Bridges `Task<T>` (Firebase) → `SingleEmitter<T>` (RxJava3).
- **`CompletableTaskHandler`**: Bridges `Task<Void>` (Firebase) → `CompletableEmitter` (RxJava3).

### DI Convention

- One factory class per dependency type: `LoginViewModelFactory`, `FirebaseUserRepositoryFactory`, etc.
- All factories constructed in `BarterTraderInjector` (singleton via `BarterTradeApplication`).
- Fragment construction uses `CustomFragmentFactory` which injects ViewModel factories.
