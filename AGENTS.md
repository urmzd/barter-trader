# Barter Trader — Agent Context

## Project Overview

Android barter marketplace app. Users exchange items as Providers (offer items) or Receivers (browse and request trades). Built with Java 8 on Android SDK 30, Firebase backend, Clean Architecture + MVVM.

## Tech Stack

- **Language**: Java 8 (no Kotlin)
- **Android**: compileSdk 30, minSdk 24, targetSdk 30
- **Build**: Gradle 6.5 (wrapper), AGP 4.1.1
- **Backend**: Firebase Auth, Firestore, Cloud Storage (BOM 28.4.2)
- **Reactive**: RxJava3 + LiveData
- **UI**: Data Binding, Material Design 1.2.1, Glide 4.11.0
- **DI**: Manual factory pattern (no Dagger/Hilt)
- **Dev environment**: Nix flake (JDK 11, Android SDK, Firebase Tools)

## Build & Run

```bash
direnv allow          # One-time: auto-activates dev shell, generates local.properties
nix run .#emulator &  # Launch Android emulator (background)
./gradlew emulators & # Start Firebase emulators (background)
./gradlew dev         # Build + install + seed test data
./gradlew test        # Run unit tests
```

- direnv auto-activates the Nix dev shell and generates `local.properties` (points Gradle to the Nix-provided Android SDK). Without direnv, run `nix develop` manually.
- `nix run .#emulator` launches an Android emulator with API 30 arm64 via `androidenv.emulateApp`
- `./gradlew dev` chains `installDebug` + `seed` — requires a running Android emulator/device and Firebase emulators
- `./gradlew seed` populates Firebase emulators with test users (`provider@test.com` / `receiver@test.com`) and sample posts
- Requires `app/google-services.json` (gitignored). Debug builds auto-connect to Firebase emulators at `10.0.2.2`.

## Architecture

```
View → ViewModel → UseCase → Repository → DataSource
```

- `app/src/main/java/ca/dal/bartertrader/presentation/` — Fragments, ViewModels, Adapters
- `app/src/main/java/ca/dal/bartertrader/domain/` — Use Cases, Models, Repository interfaces
- `app/src/main/java/ca/dal/bartertrader/data/` — Repository impls, DataSources, Firestore models
- `app/src/main/java/ca/dal/bartertrader/di/` — Factory classes for DI
- `app/src/main/java/ca/dal/bartertrader/utils/` — Handlers, validation, location, Resource/LiveEvent

## Key Patterns

- **Resource<T>**: Wraps async results with PENDING/FULFILLED/REJECTED status
- **LiveEvent<T>**: Single-fire MutableLiveData (for toasts, navigation — consumed once)
- **SingleTaskHandler / CompletableTaskHandler**: Bridge Firebase Task API → RxJava3
- **BarterTraderInjector**: Central DI container, constructed in `BarterTradeApplication.onCreate()`

## Conventions

- Java class names: `UpperCamelCase`
- Methods/variables: `lowerCamelCase`
- Constants: `UPPER_CASE`
- Layout files: `container_name` (e.g., `fragment_login.xml`)
- Element IDs: `name_container_element_name`
- Test classes: `ClassNameUnitTest` (JUnit), `ClassNameInstrumentedTest` (Espresso)
- String resources prefixed by type: `error_`, `message_`, `actions_`, `title_`

## Sensitive Files

- `app/google-services.json` — Firebase config (gitignored, must be provided locally)
- Test credentials via `gradle.properties` (`TEST_EMAIL`, `TEST_PASSWORD`) — never hardcode
- `.env` files — gitignored

## CI/CD

GitLab CI (`.gitlab-ci.yml`) with stages: lintDebug, assembleDebug, unit_tests. Uses `dalfcs_gitlab_docker_ci` runner tag.
