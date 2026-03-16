# Contributing

## Setup

```bash
# One-time: allow direnv (auto-activates on cd, generates local.properties)
direnv allow

# Add your Firebase config
cp /path/to/your/google-services.json app/google-services.json

# Launch Android emulator (background)
nix run .#emulator &

# Start Firebase emulators (background)
./gradlew emulators &

# Build, install, and seed test data
./gradlew dev
```

direnv auto-activates the Nix dev shell and generates `local.properties` (points Gradle to the Android SDK) when you `cd` into the project. If you don't use direnv, run `nix develop` manually.

## Conventions

### Java

- Class names: `UpperCamelCase`
- Methods and variables: `lowerCamelCase`
- Constants: `UPPER_CASE`

### XML Resources

- File names: `lower_underscore_case`
- Style names: `UpperCamelCase`
- Layout names: `container_name` (e.g., `LoginFragment.java` → `fragment_login.xml`)
- Element IDs: `name_container_element_name` (e.g., `@id/login_fragment_text_username`)
- Navigation fragment IDs: `UpperCamelCase` (e.g., `@id/LoginFragment`)
- String resources: `type_name` where type is one of: `error`, `message`, `actions`, `title`

### Testing

- Espresso test classes: `ClassNameInstrumentedTest`
- JUnit test classes: `ClassNameUnitTest`
- Test method names: `lowerCamelCase` describing expected behavior
- Test credentials: use `BuildConfig.TEST_EMAIL` / `BuildConfig.TEST_PASSWORD` — never hardcode

### Patterns

- Use `Resource<T>` to propagate async state (PENDING, FULFILLED, REJECTED)
- Use `LiveEvent<T>` for single-fire events (toasts, navigation)
- RxJava3 for async Firebase operations, LiveData for UI observation
