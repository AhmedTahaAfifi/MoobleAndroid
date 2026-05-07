# CourseHub - Android Moodle App Development Plan

## App Overview
- **App Name**: CourseHub
- **Package Name**: com.yourname.coursehub
- **Target**: Android 8.0+ (API 26+)
- **Architecture**: MVVM with Clean Architecture
- **Base URL**: https://sandbox.licensure.org.iq
- **API Endpoint**: /webserviceserverphp

---

## Technical Stack

### Core Libraries
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM + Repository Pattern
- **Dependency Injection**: Hilt
- **Local Database**: Room Database
- **Networking**: Retrofit + OkHttp + Moshi
- **State Management**: ViewModel + LiveData/StateFlow
- **Image Loading**: Coil
- **Async**: Coroutines

### Build Tools
- Gradle (Kotlin DSL)
- Android Gradle Plugin 8.0+
- Kotlin 1.9+

---

## Project Structure

```
coursehub/
├── app/
│   ├── src/main/
│   │   ├── kotlin/com/yourname/coursehub/
│   │   │   ├── MainActivity.kt
│   │   │   ├── di/                          # Hilt modules
│   │   │   │   ├── NetworkModule.kt
│   │   │   │   ├── DatabaseModule.kt
│   │   │   │   └── RepositoryModule.kt
│   │   │   ├── data/
│   │   │   │   ├── api/                     # API interfaces
│   │   │   │   │   ├── MoodleApiService.kt
│   │   │   │   │   └── ApiModels.kt
│   │   │   │   ├── database/                # Room Database
│   │   │   │   │   ├── CourseDatabase.kt
│   │   │   │   │   ├── dao/
│   │   │   │   │   │   ├── CourseDao.kt
│   │   │   │   │   │   ├── GradeDao.kt
│   │   │   │   │   │   └── UserDao.kt
│   │   │   │   │   └── entities/
│   │   │   │   │       ├── CourseEntity.kt
│   │   │   │   │       ├── GradeEntity.kt
│   │   │   │   │       └── UserEntity.kt
│   │   │   │   └── repository/              # Repository pattern
│   │   │   │       ├── AuthRepository.kt
│   │   │   │       ├── CourseRepository.kt
│   │   │   │       └── GradeRepository.kt
│   │   │   ├── presentation/
│   │   │   │   ├── viewmodel/
│   │   │   │   │   ├── LoginViewModel.kt
│   │   │   │   │   ├── CoursesViewModel.kt
│   │   │   │   │   ├── CourseDetailsViewModel.kt
│   │   │   │   │   └── GradesViewModel.kt
│   │   │   │   └── ui/
│   │   │   │       ├── screens/
│   │   │   │       │   ├── LoginScreen.kt
│   │   │   │       │   ├── CoursesScreen.kt
│   │   │   │       │   ├── CourseDetailsScreen.kt
│   │   │   │       │   └── GradesScreen.kt
│   │   │   │       ├── components/
│   │   │   │       │   ├── CourseCard.kt
│   │   │   │       │   ├── GradeCard.kt
│   │   │   │       │   └── ErrorDialog.kt
│   │   │   │       └── theme/
│   │   │   │           ├── Color.kt
│   │   │   │           ├── Typography.kt
│   │   │   │           └── Theme.kt
│   │   │   └── utils/
│   │   │       ├── Constants.kt
│   │   │       ├── Extensions.kt
│   │   │       └── TokenManager.kt
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
└── build.gradle.kts (root)
```

---

## Feature Implementation Plan

### Phase 1: Setup & Authentication (Days 1-2)

#### 1.1 Project Setup
- [ ] Create new Android Studio project
- [ ] Add Gradle dependencies (Hilt, Room, Retrofit, Compose)
- [ ] Setup Hilt DI
- [ ] Create basic project structure

#### 1.2 API Integration
- [ ] Create `MoodleApiService` interface
- [ ] Define API request/response models
- [ ] Setup Retrofit with OkHttp interceptor
- [ ] Implement token management

**Key Models**:
```kotlin
data class LoginRequest(
    val username: String,
    val password: String
)

data class LoginResponse(
    val token: String,
    val userid: Int,
    val firstname: String,
    val lastname: String
)
```

#### 1.3 Database Setup
- [ ] Create Room database with UserEntity
- [ ] Create UserDao for session management
- [ ] Create TokenManager utility class

#### 1.4 Authentication
- [ ] Create `AuthRepository`
- [ ] Create `LoginViewModel`
- [ ] Build `LoginScreen` UI (email, password, login button)
- [ ] Handle login errors and validation
- [ ] Store token securely using Android Keystore

---

### Phase 2: Courses Feature (Days 2-3)

#### 2.1 Database & API Models
- [ ] Create `CourseEntity` for Room
- [ ] Create `CourseDao`
- [ ] Create API models for courses
- [ ] Setup Course endpoints in `MoodleApiService`

**Key Models**:
```kotlin
data class CourseEntity(
    @PrimaryKey val id: Int,
    val courseName: String,
    val courseCode: String,
    val displayName: String,
    val summary: String? = null,
    val enrollmentStatus: String,
    val startDate: Long? = null
)
```

#### 2.2 Repository Pattern
- [ ] Create `CourseRepository`
- [ ] Implement caching logic (fetch from API, save to DB)
- [ ] Handle network errors gracefully

#### 2.3 ViewModel & UI
- [ ] Create `CoursesViewModel`
- [ ] Implement state management (loading, success, error)
- [ ] Build `CoursesScreen` with list of courses
- [ ] Create `CourseCard` composable component

**Requirements from Email**:
- ✅ Display enrolled courses
- ✅ Show course progress/enrollment status
- ✅ Pull data from API and cache locally

---

### Phase 3: Course Details (Days 3-4)

#### 3.1 Navigation Setup
- [ ] Setup Jetpack Navigation Compose
- [ ] Create navigation graph
- [ ] Implement deep linking if needed

#### 3.2 Course Content
- [ ] Define CourseContent API model
- [ ] Update `CourseRepository` for course details
- [ ] Create `CourseDetailsViewModel`

**Key Models**:
```kotlin
data class CourseContent(
    val id: Int,
    val name: String,
    val modules: List<Module>,
    val activities: List<Activity>
)

data class Module(
    val id: Int,
    val name: String,
    val sections: List<Section>
)

data class Section(
    val id: Int,
    val title: String,
    val content: String
)
```

#### 3.3 UI Implementation
- [ ] Build `CourseDetailsScreen`
- [ ] Display course info, modules, activities
- [ ] Handle loading and error states
- [ ] Show course progress/completion

**Requirements from Email**:
- ✅ Show course content
- ✅ Display modules and activities
- ✅ Show enrollment progress

---

### Phase 4: Grades Feature (Days 4-5)

#### 4.1 Database & API
- [ ] Create `GradeEntity` for Room
- [ ] Create `GradeDao`
- [ ] Define Grade API models
- [ ] Add grade endpoints to `MoodleApiService`

**Key Models**:
```kotlin
data class GradeEntity(
    @PrimaryKey val id: Int,
    val courseId: Int,
    val itemName: String,
    val gradeValue: Double,
    val maxGrade: Double,
    val dateGraded: Long,
    val feedback: String? = null
)

data class CourseGrades(
    val courseId: Int,
    val courseName: String,
    val grades: List<GradeEntity>,
    val courseGrade: Double
)
```

#### 4.2 Repository & ViewModel
- [ ] Create `GradeRepository`
- [ ] Create `GradesViewModel`
- [ ] Implement filtering by course
- [ ] Sort grades by date

#### 4.3 UI
- [ ] Build `GradesScreen`
- [ ] Create `GradeCard` component
- [ ] Display grades with visual indicators (progress bars)
- [ ] Show feedback if available

**Requirements from Email**:
- ✅ Display user grades
- ✅ Show grades using course details
- ✅ Display grade feedback

---

## Technical Expectations (from Email)

### ✅ Native Implementation
- Pure Kotlin/Android (no cross-platform frameworks)
- Jetpack Compose for modern UI

### ✅ REST API Integration
- Retrofit for HTTP requests
- Base URL: https://sandbox.licensure.org.iq/webserviceserverphp
- Support for token authentication
- Error handling with proper status codes

### ✅ Consistent Architecture
- MVVM pattern across all screens
- Repository pattern for data access
- Single responsibility principle
- Dependency injection with Hilt

### ✅ Data Persistence
- Room Database for local caching
- Token storage using Android Keystore
- Automatic refresh on app restart

### ✅ Error Handling
- Network error recovery
- User-friendly error messages
- Loading states for async operations
- Offline support (cached data)

### ✅ Data Consistency
- Synchronization between local and remote data
- Handle token expiration
- Retry logic for failed requests

---

## Implementation Checklist

### Daily Breakdown

**Day 1:**
- [ ] Project setup & dependencies
- [ ] Hilt DI configuration
- [ ] Retrofit setup
- [ ] Room Database setup
- [ ] LoginScreen basic UI

**Day 2:**
- [ ] LoginViewModel & AuthRepository
- [ ] Token management
- [ ] CoursesScreen & UI
- [ ] Course API integration

**Day 3:**
- [ ] Course caching (Room)
- [ ] CourseDetailsScreen
- [ ] Navigation setup
- [ ] Error handling

**Day 4:**
- [ ] Grades API integration
- [ ] GradesScreen UI
- [ ] Grade caching
- [ ] Testing screens together

**Day 5:**
- [ ] Polish UI/UX
- [ ] Test offline functionality
- [ ] Add edge case handling
- [ ] Final testing & debugging

---

## API Authentication Details

### Login Flow
```
POST /webserviceserverphp
Parameters:
- username: student1
- password: (provided)
- moodlewsrestformat: json

Response:
{
  "token": "xxxxxxxxxxxx",
  "userid": 2,
  "firstname": "Student",
  "lastname": "User"
}
```

### Authenticated Requests
All subsequent requests include token:
```
?wstoken=xxxxxxxxxxxx&moodlewsrestformat=json
```

### Required Endpoints
1. `core_auth_validate_credentials` - Validate login
2. `core_enrol_get_users_courses` - Get enrolled courses
3. `core_course_get_contents` - Get course content
4. `gradereport_user_get_grades_table` - Get grades

---

## Dependencies Setup (build.gradle.kts)

```kotlin
dependencies {
    // Core
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    
    // Jetpack Compose
    implementation("androidx.compose.ui:ui:1.5.4")
    implementation("androidx.compose.material3:material3:1.1.2")
    implementation("androidx.activity:activity-compose:1.8.0")
    
    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.5")
    
    // Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-compiler:2.48")
    
    // Room
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    
    // Retrofit & OkHttp
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.15.0")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.15.0")
    
    // Coil (Image loading)
    implementation("io.coil-kt:coil-compose:2.5.0")
    
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    
    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    
    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
```

---

## Testing Strategy

### Unit Tests
- Repository layer tests (mock API)
- ViewModel tests (test state changes)
- Utility function tests

### Integration Tests
- Database tests (Room DAOs)
- API integration tests (mock server)

### UI Tests
- Compose screen tests
- Navigation tests

---

## Deliverables

### Code Submission
1. Complete Android Studio project
2. All source files (.kt)
3. build.gradle.kts with dependencies
4. AndroidManifest.xml

### Documentation
1. **README.md**: Setup instructions, how to build/run
2. **Architecture.md**: Detailed architecture explanation
3. **API_INTEGRATION.md**: How API calls are made
4. **DATABASE_SCHEMA.md**: Room database design

### Submission Structure
```
coursehub/
├── README.md
├── ARCHITECTURE.md
├── API_INTEGRATION.md
├── DATABASE_SCHEMA.md
├── .gitignore
├── build.gradle.kts
├── settings.gradle.kts
└── app/
    ├── build.gradle.kts
    └── src/main/
        ├── kotlin/...
        └── AndroidManifest.xml
```

---

## Success Criteria

- ✅ All 3 screens fully functional (Courses, Details, Grades)
- ✅ API integration working with error handling
- ✅ Data persists locally using Room
- ✅ Proper token management and authentication
- ✅ Clean MVVM architecture
- ✅ All data displays correctly
- ✅ Responsive UI with loading states
- ✅ No crashes or ANRs
- ✅ Well-documented code
- ✅ README with setup instructions

---

## Notes

- Build time estimate: **24 hours**
- Use Kotlin coroutines for all async operations
- Always use Hilt for dependency injection
- Follow Android Architecture Components best practices
- Test on Android 8.0+ devices/emulators
- Handle both success and error states in every screen
