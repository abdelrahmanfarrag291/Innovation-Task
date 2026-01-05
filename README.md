# Innovation Task
A modern Android application built using **Jetpack Compose** and following **Clean Architecture** and **modularization** principles.

## Features
The application consists of **two main features**:

- **Movies List**
  - Displays a list of movies
  - Supports caching and offline-first behavior

- **Movie Details**
  - Displays detailed information about a selected movie

##  Modular Architecture

The project is designed with **feature-based modularization** to ensure scalability, reusability, and maintainability.

###  Core Modules

#### `core`
- Holds the **base network configuration**
- Centralized setup for:
  - Retrofit
  - OkHttp
  - HTTP Logging Interceptor
- Used across all feature modules

#### `common-movies`
- Contains **shared configuration and logic** between:
  - Movies List
  - Movie Details
- Includes shared models, mappers, and constants
- Prevents duplication and enforces consistency
  
##  Tech Stack

- **Jetpack Compose** – Declarative UI toolkit
- **Kotlin Coroutines & Flow** – Asynchronous and reactive programming
- **Retrofit** – REST API communication
- **OkHttp** – HTTP client
- **HTTP Logging Interceptor** – Network request logging
- **Hilt** – Dependency Injection
- **Room Database** – Local data persistence


## MVI (Model–View–Intent)

The Presentation layer follows the **MVI architecture pattern**:

- **Intent** – User actions and UI events
- **ViewState** – Immutable UI state
- **ViewModel** – Processes intents and exposes state
- **Side Effects** – One-time events (navigation, errors, etc.)


##  Testing

The project includes **unit tests** to ensure reliability and correctness across layers:

- **Local data source tests** (Room)
- **Remote data source tests** (API)
- **Repository tests**
- **ViewModel tests** (state & intent handling)

Testing focuses on:
- Business logic validation
- Correct state emission in MVI
- Error and edge case handling
