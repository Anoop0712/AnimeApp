📱 AnimeApp – Android Application

AnimeApp is an Android application that allows users to explore anime content using a clean UI and modern Android development practices.
The project demonstrates scalable architecture, API integration, and best practices in Android development.

🔗 Repository: https://github.com/Anoop0712/AnimeApp

```Project structure
│
├── data        # API, models, repositories, local data source
├── domain      # Business logic
├── ui          # Activities/ViewModels/Compose
├── di          # Dagger modules
└── utils       # Helpers and extensions
```

🛠 Tech Stack

- Language: Kotlin

- Architecture: MVVM (Model–View–ViewModel)

- UI: Material Components and Compose

- Networking: Retrofit + OkHttp

- Dependency Injection: Dagger2

- Asynchronous Programming: Coroutines + Flow

- Image Loading: Coil

📌 Assumptions Made

- Users may have a stable internet connection to fetch anime data from the API if not show a toast and emoji.

- The backend API returns valid and consistent responses.

- Error states (network/API failures) are handled gracefully with fallback UI messages.

- The project focuses on functionality and architecture rather than exhaustive UI animations.

✨ Features Implemented

- ✅ Browse anime list fetched from a remote API

- ✅ View anime details (title, image, description, ratings, etc.)

- ✅ Clean MVVM architecture with separation of concerns

- ✅ Dependency Injection using Dagger2

- ✅ Efficient API handling using Retrofit & Coroutines

- ✅ Error handling for network failures

- ✅ Modular and scalable codebase

Known Limitations

- ❌ Pagination may be limited depending on API support

- ❌ UI animations are minimal


👨👨‍💻 Author 
Anoop Kumar Mittapelli 
Android Developer 
GitHub: https://github.com/Anoop0712
