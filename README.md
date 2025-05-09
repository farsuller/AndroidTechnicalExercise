# AndroidTechnicalExercise

# 🌤️ WeatherApp

A simple weather application built with **Jetpack Compose**, following **MVVM Clean Architecture**, and powered by **Koin** for dependency injection. It supports geolocation, city-based weather lookups, and weather history tracking.

## 🧱 Tech Stack

### 🔧 Jetpack Components Used
- **ViewModel**: Manages UI-related data in a lifecycle-conscious way.
- **Lifecycle**: Ensures UI updates respond properly to lifecycle changes.
- **Navigation**: Handles navigation between different screens using the `Navigation Compose` library.

### 🎨 UI Framework
- **Jetpack Compose**: Modern declarative UI toolkit used to build the UI for all screens.

### 🧠 Architecture
- **MVVM Clean Architecture**:
    - **Presentation Layer**: Jetpack Compose + ViewModel
    - **Domain Layer**: UseCases for business logic
    - **Data Layer**: Repositories
    - Promotes testability, separation of concerns, and easy scalability.

### 💉 Dependency Injection
- **Koin**: Lightweight DI framework for managing dependencies across ViewModels, UseCases, and Repositories.


### 📍 Geolocation Support
- Integrated geolocation using Google's **FusedLocationProviderClient** via a custom utility class (`LocationUtils`).
- Location updates are requested in high-accuracy mode.
- Automatically retrieves the user's current location (latitude and longitude).

### CI/CD 
- This project is configured with GitHub Actions to automate linting and unit testing, disabled the distribute script.
- https://github.com/farsuller/AndroidTechnicalExercise/actions

## 🔑 API Key Configuration
- The key is automatically read in build.gradle and exposed via BuildConfig.API_KEY.

### Step-by-step To run the app,
1. In your project root directory, paste a file named key.properties.
2. The key.properties file will part of the emailed with the repository.
