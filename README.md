# Moodle Android App

A clean, modern Android application built with Jetpack Compose that interacts with the Moodle LMS API. This app allows students to view their enrolled courses, course contents, and academic grades.

## 🚀 Setup Instructions

1.  **Clone the Repository**:
    ```bash
    git clone https://github.com/your-repo/mooble-android.git
    ```
2.  **Open in Android Studio**:
    Open the project using Android Studio Ladybug (2024.2.1) or newer.
3.  **Configure API Base URL**:
    The base URL is currently set in `AppModule.kt`. Update it if needed:
    ```kotlin
    .baseUrl("https://your-moodle-site.com/")
    ```
4.  **Sync Gradle**:
    Let the project sync dependencies (Koin, Retrofit, Room, Compose).
5.  **Run**:
    Select an emulator or physical device (API 24+) and click **Run**.

---

## 🏗 Architecture Overview

The project follows **Clean Architecture** principles combined with the **MVI (Model-View-Intent)** design pattern for the presentation layer.

### Layers:
*   **Presentation Layer**: 
    *   **Jetpack Compose**: For a declarative UI.
    *   **MVI Pattern**: Uses a `BaseViewModel` to manage `ViewState` (single source of truth for the UI) and `ViewEffect` (for one-time events like navigation).
*   **Data Layer**:
    *   **Retrofit**: Handles network requests to the Moodle REST API.
    *   **Room Database**: Securely stores user profile and authentication tokens locally.
    *   **Repositories**: Act as the mediator between the API and the UI, handling data transformation.

### Dependency Injection:
*   **Koin**: Used for lightweight, pragmatic dependency injection across all layers.

---

## 🛠 Key Implementation Decisions

### 1. MVI with BaseViewModel
We implemented a custom `BaseViewModel` to ensure all screens handle state consistently. This prevents "state explosion" and makes debugging UI issues significantly easier as every change is a discrete state update.

### 2. Live API Data (No Course Caching)
While the user profile is cached in Room for authentication, **Course and Grade data are fetched live** from the API. This decision ensures that students always see the most up-to-date information regarding their assignments and progress without potential sync conflicts.

### 3. SVG and Protected Image Handling
Moodle often provides course icons in SVG format and protects them behind authentication. We:
*   Integrated `coil-svg` for rendering vector graphics.
*   Implemented a custom URL builder in `CourseItem` to append the user's `wstoken` to image requests, allowing Coil to load protected media.

### 4. Custom Retrofit Utility (`AppRetrofitRequest`)
To maintain consistency with existing project styles, network calls use a callback-based utility that centralizes logging and error mapping, which is then safely consumed by the ViewModels.

### 5. Responsive UI with SDP
The app uses the `sdp-compose` library to ensure that margins, padding, and text sizes scale proportionally across different screen sizes and densities.

---

## 📚 Libraries Used

*   **UI**: Jetpack Compose, Material 3, Coil (Images), SDP/SSP (Responsiveness).
*   **Navigation**: Jetpack Navigation Compose (Type-safe routes).
*   **Networking**: Retrofit, OkHttp, Gson.
*   **DI**: Koin.
*   **Persistence**: Room.
*   **Serialization**: KotlinX Serialization.
