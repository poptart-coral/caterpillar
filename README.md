# Adopt a Caterpillar

An Android application for learning about cat breeds and facts before adoption. Built with Jetpack Compose and modern Android architecture.

## Features

- Browse 60+ cat breeds with detailed information
- Learn random cat facts
- View breed details including temperament, origin, and lifespan
- Offline-first architecture with local caching
- Dynamic theme switching (Light/Dark/System)

## Screenshots

### Main Screens

// TODO Add screenshots of each screen
![Random Cat Screen](screenshots/random_cat.png)
Random cat photos

![Breed List Screen](screenshots/breed_list.png)
List of all cat breeds

![Breed Detail Screen](screenshots/breed_detail.png)
Detailed breed information

![Facts Screen](screenshots/facts.png)
Random cat facts

![About Screen](screenshots/about.png)
About page with app information

## Technical Stack

### Architecture
- MVVM (Model-View-ViewModel)
- Clean Architecture with separation of concerns
- Offline-first approach

### Libraries & Frameworks
- Jetpack Compose for UI
- Hilt for dependency injection
- Retrofit for API calls
- Room for local database caching
- Kotlin Coroutines & Flow for asynchronous operations
- Kotlin Serialization for JSON parsing
- Material 3 for design system
- Navigation Compose for screen navigation

### Caching
- Room database stores breed data locally
- Data persists between app sessions
- Cache refresh on network availability
- Falls back to cached data when offline

### API
- The Cat API (https://api.thecatapi.com/v1/breeds) to fetch cat breeds
- The Cat as a Service API (https://cataas.com/cat) to fetch random pictures of cats
- Random cat facts API (https://meowfacts.herokuapp.com/) to fetch random cat facts

### Dependency Injection
Hilt modules provide:
- Retrofit instance with OkHttp client
- API service interfaces
- Repository implementations
- ViewModels with injected dependencies

### Navigation
- Bottom navigation bar with 4 main sections
- Deep linking support for breed details
- Back stack management with state preservation

## Project Structure

```bash
app/
├── data/
│ ├── local/
│ │ ├── dao/ # Room DAOs
│ │ ├── entity/ # Room entities
│ │ └── AppDatabase.kt
│ ├── remote/
│ │ ├── api/ # Retrofit API interfaces
│ │ └── dto/ # Data transfer objects
│ └── repository/ # Repository implementations
├── domain/
│ ├── model/ # Domain models
│ └── repository/ # Repository interfaces
├── di/ # Hilt dependency injection modules
├── ui/
│ ├── navigation/ # Navigation setup
│ ├── screens/ # Composable screens
│ ├── theme/ # Material theme configuration
│ └── viewmodel/ # ViewModels
├── AdoptACaterpillar/ # Navigation setup
└── MainActivity/ # The app window
```

## Build & Run

1. Clone the repository
2. Open in Android Studio
3. Sync Gradle dependencies
4. Run on emulator or physical device (Android SDK 36, Android 16)

## Code Quality

- Spotless for code formatting
- Pre-commit hooks for automatic formatting
- KSP for annotation processing
