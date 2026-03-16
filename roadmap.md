# Barter-Trader Code Quality Roadmap

## Assessment Summary (2.4 / 5)

| Dimension              | Rating | Key Finding                                                                 |
|------------------------|--------|-----------------------------------------------------------------------------|
| Architecture & Design  | 3/5    | Good MVVM/Clean Architecture intent, violated in places                     |
| Code Organization      | 3/5    | Reasonable package structure with inconsistencies                           |
| Error Handling         | 2/5    | Pervasive NPE risks, silent failures, missing RxJava error handlers         |
| Testing                | 2/5    | All tests use Thread.sleep, no unit tests, tests hit real Firebase          |
| Security               | 3/5    | Hardcoded Firebase URL, orphaned listener, client-side role toggle          |
| Dependencies & Build   | 2/5    | targetSdk 30 (Play Store non-compliant), all deps 3-5 years old            |
| Code Smells            | 2/5    | Dead variables, public mutable state, string-based enum lookups             |
| Android Best Practices | 2/5    | GPS never stopped, dispose() vs clear() bug, Geocoder on main thread       |

---

## Remaining Issues

### Architecture & Design

- [ ] **ProfileFragment bypasses architecture** — Directly instantiates `FirebaseAuth`, `FirebaseFirestoreDataSource`, and `FirebaseUserRepository` inside `onViewCreated` instead of using a ViewModel. Should be refactored to use a `ProfileViewModel` with proper DI.
  - `presentation/view/profile/ProfileFragment.java`

- [ ] **ProviderOfferViewModel defines its own repository interface** — `OfferRepository` is declared inside the ViewModel (presentation layer) instead of the domain layer, inverting the dependency direction.
  - `presentation/view_model/ProviderOfferViewModel.java`

- [ ] **Domain use cases leak Firebase types** — `GetPostsUseCase` returns `Single<QuerySnapshot>` (a Firebase Firestore type). Use cases should return domain models, not infrastructure types.
  - `domain/use_case/posts/GetPostsUseCase.java`

### Code Organization

- [ ] **ProviderOfferAdapter in wrong package** — Lives in `ca.dal.bartertrader.adapter` while other adapters are under `presentation/adapter` or `presentation/view`.
  - `adapter/ProviderOfferAdapter.java`

- [ ] **Review model in view package** — `Review.java` is a data model but lives in `presentation/view/profile` instead of `domain/model` or `data/model`.
  - `presentation/view/profile/Review.java`

### Security

- [ ] **Hardcoded Firebase Storage URL** — The Firebase Storage bucket URL is hardcoded in production code. Should be constructed from a config constant or `BuildConfig` field.
  - `presentation/view_model/provider_home/ProviderHomeViewModel.java:63`

- [ ] **Orphaned Firestore snapshot listener** — In `ProviderOfferRepositoryCallback.setStatus`, an `addSnapshotListener` is attached when status is `"ACCEPTED"` but the `ListenerRegistration` is never stored or removed, causing a memory/resource leak.
  - `data/repository/ProviderOfferRepositoryCallback.java`

- [ ] **Client-side role switch without server enforcement** — `switchRole()` allows a client to toggle their own `provider` boolean in Firestore. Firestore Security Rules must restrict this to prevent privilege escalation.
  - `data/data_source/FirebaseFirestoreDataSource.java:57`

### Testing

- [ ] **Tests use Thread.sleep for synchronization** — Every instrumented test uses `Thread.sleep(1000)` through `Thread.sleep(5000)`. Replace with `IdlingResource` or `CountDownLatch` to eliminate flakiness.
  - `androidTest/.../ProviderOffersInstrumentedTest.java`
  - `androidTest/.../ProfileFragmentInstrumentedTest.java`
  - `androidTest/.../ReceiverHomeFragmentTest.java`

- [ ] **Tests hardcode UI strings** — Tests use `withText("LOG IN")`, `withHint("Email")`, etc. instead of referencing string resources. Will break on localization or string changes.
  - `androidTest/.../ProviderOffersInstrumentedTest.java`

- [ ] **Integration tests use real Firebase** — `ReceiverHomeFragmentTest` creates `new BarterTraderInjector()` directly with no mocks, requiring specific backend state for tests to pass.
  - `androidTest/.../ReceiverHomeFragmentTest.java`

- [ ] **No unit tests** — The `domain` and `utils` layers (`FormValidatorTools`, `TransformedLiveData`, `LiveEvent`, use cases) have zero unit test coverage despite being pure Java trivially testable with JUnit/Mockito.

### Dependencies & Build

- [ ] **All dependencies severely outdated** — Update to current stable versions:
  - `compileSdkVersion 30` → 35 (Google Play requires `targetSdk >= 34`)
  - `lifecycle 2.2.0` → 2.8.x
  - `nav 2.3.1` → 2.8.x
  - `fragment 1.3.0-beta01` → stable release
  - `paging 3.0.0-alpha09` → stable release
  - `camerax 1.0.0-beta12` → stable release
  - `firebase-bom 28.4.2` → 33.x
  - `glide 4.11.0` → 4.16.x
  - `material 1.2.1` → 1.12.x
  - `build.gradle`

- [ ] **Mixed Support Library and AndroidX** — `com.android.support:support-annotations:28.0.0` is listed alongside AndroidX. Replace with `androidx.annotation:annotation`.
  - `build.gradle:85`

- [ ] **lintOptions abortOnError false** — Suppresses all lint errors project-wide instead of addressing them.
  - `build.gradle:38-40`

### Code Smells

- [ ] **OfferStatus.valueOf() used with string literals** — `OfferStatus.valueOf("ACCEPTED")`, `OfferStatus.valueOf("PENDING")`, etc. should use enum constants directly (`OfferStatus.ACCEPTED`) for compile-time safety.
  - `adapter/ProviderOfferAdapter.java:41-54`

### Android Best Practices

- [ ] **Fragment constructor injection fragile across process death** — All fragments take dependencies through custom constructors. While `CustomFragmentFactory` is set before `super.onCreate()`, this pattern is fragile. Consider migrating to Hilt/Dagger/Koin.
  - `di/fragment/CustomFragmentFactory.java`

- [ ] **ProviderOfferFragment accumulates duplicate offers** — `onPause()` clears offers, but LiveData re-emits all snapshots when re-activated, and `addProduct` unconditionally appends, leading to duplicates.
  - `presentation/view/ProviderOfferFragment.java:84-86, 117-120`

- [ ] **Geocoder.getFromLocation called on main thread** — `getCityFromCurrentLocation()` and `getProvinceFromCurrentLocation()` perform network/IO on the main thread, risking ANR. Move to a background thread.
  - `utils/LocationServiceManager.java:59-68`
