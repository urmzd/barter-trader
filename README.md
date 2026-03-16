<p align="center">
  <h1 align="center">Barter Trader</h1>
  <p align="center">
    An Android marketplace for exchanging items through barter — no currency needed.
    <br /><br />
    <a href="https://github.com/urmzd/barter-trader/releases">Download</a>
    &middot;
    <a href="https://github.com/urmzd/barter-trader/issues">Report Bug</a>
    &middot;
    <a href="https://github.com/urmzd/barter-trader">Source</a>
  </p>
</p>

## Features

- Two-role system: **Providers** offer items, **Receivers** browse and request trades
- Post creation with image uploads and paginated browsing
- Offer management — accept, decline, and review trades
- Location-based services with geocoding
- Firebase-backed authentication, Firestore database, and Cloud Storage
- Firebase Emulator support for local development

## Quick Start

### Prerequisites

- [Nix](https://nixos.org/download.html) (recommended) — provides JDK 11, Android SDK, Gradle, Node.js, and Firebase Tools
- Or manually: JDK 11, Android SDK 30, Gradle 6.5+

### Setup

```bash
# Enter the dev shell (installs all dependencies)
nix develop

# Add your Firebase config
cp /path/to/your/google-services.json app/google-services.json

# Start Firebase emulators
firebase emulators:start

# Build the debug APK
./gradlew assembleDebug
```

### Running Tests

```bash
# Unit tests
./gradlew test

# Instrumented tests (requires emulator or device)
# Set test credentials in gradle.properties:
#   TEST_EMAIL=your-test@example.com
#   TEST_PASSWORD=YourTestPass123!
./gradlew connectedAndroidTest
```

## Architecture

Clean Architecture + MVVM with the following layers:

```
View → ViewModel → UseCase → Repository → DataSource
```

| Layer | Directory | Responsibility |
|---|---|---|
| Presentation | `presentation/` | Fragments, ViewModels, Adapters |
| Domain | `domain/` | Use Cases, Models, Repository interfaces |
| Data | `data/` | Repository implementations, Data Sources, Firestore models |
| DI | `di/` | Manual dependency injection via factory pattern |
| Utils | `utils/` | Handlers, validation, location services |

## Configuration

### Firebase Emulators

Configured in `firebase.json`:

| Service | Port |
|---|---|
| Auth | 9099 |
| Firestore | 8080 |
| Storage | 9199 |
| Emulator UI | 4000 |

Debug builds automatically connect to emulators at `10.0.2.2` (Android emulator loopback).

### Build Variants

- **Debug**: Connects to Firebase emulators, cleartext allowed to localhost
- **Release**: R8 minification enabled, cleartext blocked, ProGuard rules applied

## License

[Apache 2.0](LICENSE)
