# EcoTrack 🌱



## 📥 Download EcoTrack

Try EcoTrack using the latest release.

| Build | Download |
|---|---|
| Android APK | [Download EcoTrack APK](https://github.com/Shubhampanigrahi6/EcoTrack/releases/download/v1.0.0/app-release.apk) |
| Android App Bundle | [Download EcoTrack AAB](https://github.com/Shubhampanigrahi6/EcoTrack/releases/download/v1.0.0/app-release.aab) |

**Installation:** Download the APK, open it on your Android device, and allow installation from your browser or file manager if prompted.

**Play Store:** Coming soon!


### Demo Login

| Field | Value |
|---|---|
| Email | `demo@ecotrack.com` |
| Password | `123456` |




**Live Sustainably. Make Every Choice Count.**

EcoTrack is an Android sustainability tracking application developed using **Kotlin and Jetpack Compose**. It encourages users to make environmentally responsible everyday choices, track their sustainability impact, and compare their progress with the community.

The application focuses on four key areas: sustainable travel, responsible shopping, planet-friendly food choices, and everyday eco-friendly activities.




## ✨ Core Features

### 1. Authentication
- Login and Signup screens.
- Mock authentication repository for demonstration.
- Local session management using Android SharedPreferences.
- Login state restoration across app launches.

### 2. Sustainability Dashboard
- Personalized sustainability dashboard.
- Displays total CO₂ savings.
- Tracks Eco Points and daily streaks.
- Weekly sustainability savings visualization.
- Daily sustainability challenge.

### 3. Eco-Friendly Missions
- Browse sustainability missions.
- Complete activities to earn Eco Points.
- Track CO₂ savings from completed missions.
- Update dashboard and leaderboard rewards.

### 4. Community Feed
- View sustainability-related community posts.
- Like and unlike posts.
- Comment on posts.
- Create new community posts.

### 5. Nearby Friends Map
- Google Maps integration.
- Displays nearby friend markers.
- Includes a location button to access the device's current location.
- Demonstrates the nearby community experience using sample friend locations.

### 6. Sustainability Chatbot
- Basic text-based chatbot.
- Provides predefined responses to sustainability-related questions.
- Covers topics such as recycling, transportation, plastic reduction, water and energy conservation.

### 7. Sustainability Leaderboard
- Displays community sustainability rankings.
- Shows Eco Points and user positions.
- Includes weekly and monthly leaderboard views.

### 8. Reports and Analytics
- Weekly and monthly report filters.
- CO₂ savings summaries.
- Transport usage breakdown.
- Walking, cycling and public transport metrics.
- Sustainability impact overview.

### 9. Profile and Settings
- User profile overview.
- Profile editing interface.
- Sustainability statistics.
- Notification and reminder preferences.
- Location-sharing and appearance settings.
- Logout functionality.

## 🛠 Technology Stack

| Technology | Purpose |
|---|---|
| Kotlin | Primary programming language |
| Jetpack Compose | Declarative UI development |
| Material 3 | UI components and styling |
| MVVM | Application architecture |
| Repository Pattern | Data management |
| Kotlin Coroutines | Asynchronous operations |
| StateFlow | Reactive UI state management |
| Android ViewModel | UI state and business logic |
| Navigation Compose | Screen navigation |
| Google Maps SDK | Map integration |
| Maps Compose | Google Maps UI integration |
| Fused Location Provider | Device location |
| SharedPreferences | Local session storage |
| JSON | Mock application data |
| JUnit | Unit testing |

## 🏗 Architecture

EcoTrack follows the **MVVM (Model-View-ViewModel) architecture** with a Repository Pattern.

### Architecture Overview

```text
                 Compose UI
                     |
                     v
                  ViewModel
                     |
                     v
                  Repository
                     |
             -----------------
             |               |
             v               v
         JSON Assets     Local Storage
             |               |
             -----------------
                     |
                     v
                 Data Models
```

### Project Structure

```text
EcoTrack/
│
├── app/
│   └── src/
│       ├── main/
│       │   ├── assets/
│       │   │   ├── eco_data.json
│       │   │   └── missions.json
│       │   │
│       │   ├── java/com/superhumans/ecotrack/
│       │   │   ├── core/
│       │   │   │   ├── common/
│       │   │   │   ├── constants/
│       │   │   │   ├── utils/
│       │   │   │   └── components/
│       │   │   │
│       │   │   ├── data/
│       │   │   │   ├── model/
│       │   │   │   ├── remote/
│       │   │   │   └── repository/
│       │   │   │
│       │   │   ├── domain/
│       │   │   │   ├── model/
│       │   │   │   ├── repository/
│       │   │   │   └── usecase/
│       │   │   │
│       │   │   ├── di/
│       │   │   ├── navigation/
│       │   │   │
│       │   │   └── presentation/
│       │   │       ├── auth/
│       │   │       ├── home/
│       │   │       ├── missions/
│       │   │       ├── community/
│       │   │       │   ├── feed/
│       │   │       │   ├── map/
│       │   │       │   └── chat/
│       │   │       ├── leaderboard/
│       │   │       ├── reports/
│       │   │       ├── profile/
│       │   │       └── settings/
│       │   │
│       │   └── res/
│       │
│       └── test/
│           └── java/com/superhumans/ecotrack/
│
├── README.md
└── build.gradle.kts
```

## 📊 Data Sources and Application Flow

The current implementation uses local JSON assets for demonstration data.

- `eco_data.json` provides dashboard, user impact, leaderboard and report information.
- `missions.json` provides sustainability mission information.
- `EcoDataRepository` loads and parses JSON data.
- ViewModels expose application state through Kotlin StateFlow.
- Compose screens observe state and update the UI.

Mock authentication is implemented using `MockAuthRepository`.

The application uses SharedPreferences to maintain the logged-in session locally.

## 🧪 Testing

The project includes JUnit-based unit tests.

Current test coverage includes:
- Successful login with valid demo credentials.
- Failed login with invalid credentials.
- Successful signup with a new email address.
- Signup rejection for an existing email address.

### Demo Login

| Field | Value |
|---|---|
| Email | `demo@ecotrack.com` |
| Password | `123456` |

Run unit tests using:

```bash
./gradlew testDebugUnitTest
```

On Windows PowerShell:

```powershell
.\gradlew testDebugUnitTest
```

Run Android instrumentation tests using:

```bash
.\gradlew connectedDebugAndroidTest
```

## ⚙️ Setup Instructions

### Requirements
- Android Studio.
- JDK 11 or compatible configured Gradle JDK.
- Android SDK 36.
- Google Maps API key.
- Android device or emulator.

### Installation

1. Clone or download the EcoTrack project.
2. Open the project in Android Studio.
3. Allow Gradle to sync.
4. Configure your Google Maps API key in `local.properties`:

```properties
MAPS_API_KEY=YOUR_GOOGLE_MAPS_API_KEY
```

5. Ensure the Maps SDK for Android is enabled in Google Cloud.
6. Build and run the application.

**Security:** Do not commit `local.properties`, API keys, passwords or signing keystores to a public repository.

## 📦 Build and Deployment

The application is configured for Android release builds.

- Application ID: `com.superhumans.ecotrack`
- Minimum SDK: 31
- Compile SDK: 36
- Target SDK: 36
- Version Code: 1
- Version Name: 1.0

### Generate a Release Build

In Android Studio:

1. Open **Build**.
2. Select **Generate Signed Bundle / APK**.
3. Select **Android App Bundle**.
4. Choose the upload keystore.
5. Select the `release` build variant.
6. Generate the signed AAB.

The resulting Android App Bundle can be uploaded to Google Play Console for internal testing.


## 📱 Application Information

| Property | Details |
|---|---|
| Application | EcoTrack |
| Category | Sustainability |
| Platform | Android |
| Language | Kotlin |
| UI Framework | Jetpack Compose |
| Architecture | MVVM |
| Package | `com.superhumans.ecotrack` |

---

**EcoTrack — Small actions. Sustainable future. 🌍**
