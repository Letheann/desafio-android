# Android Challenge

This project is a **Kotlin Multiplatform (KMP)** application using **Jetpack Compose**, **Ktor**,
and **Koin**, following an **MVVM + MVI** architecture. Gradle configuration and dependency
management are centralized through a modularized `build_logic` setup using the **Gradle Version
Catalog** and custom plugins.

## Technologies Used

- **Kotlin Multiplatform (KMP)**: Shared business logic across Android, iOS, and other platforms.
- **Jetpack Compose**: Modern declarative UI framework for building Android interfaces.
- **Ktor**: HTTP client for networking in Kotlin multiplatform projects.
- **Koin**: Lightweight dependency injection framework.
- **Coroutines & Flow**: For managing asynchronous data streams and background tasks.
- **MVI Architecture**: Clear unidirectional data flow pattern using `Intent`, `State`, and
  `Effect`.
- **MVVM Pattern**: Separation of concerns between UI (View), logic (ViewModel), and business
  rules (UseCases).
- **Gradle build_logic**: Centralized, maintainable Gradle configuration using version catalogs and
  plugin DSL.
- **MockK**: Mocking library for unit testing.
- **JUnit**: Test runner for unit and integration testing.

## Features

The application displays a simple list using **cards**, each showing the properties of the list
items. It is structured for clarity and maintainability, serving as a solid foundation for
multiplatform app development.

## Architecture

This project implements a **Model-View-Intent (MVI)** pattern alongside **MVVM**, structured as
follows:

- **Model**: Business logic and data transformations handled by ViewModels and UseCases.
- **View**: Built with Jetpack Compose, observing state from ViewModels.
- **Intent**: User actions are modeled as Intents, sent to the ViewModel.
- **ViewEffect**: One-time effects like navigation or toasts, emitted by the ViewModel and observed
  by the View.

### MVI Flow

1. The **View** sends an **Intent** to the **ViewModel**.
2. The **ViewModel** processes the intent, updates the **State**, and optionally emits a *
   *ViewEffect**.
3. The **View** observes and reacts to the new **State** or **ViewEffect**.

## Tests

- **Unit Tests**:
    - **MockK** is used to mock dependencies and verify behavior.
    - **JUnit** is used to write and run test cases.
    - **Kotlinx Coroutines Test** is used for coroutine-based testing.

## How to Run the Project

1. Clone the repository:
   ```bash
   git clone https://github.com/letheann/desafio-android.git
   ```

## How to Switch to the ‘KMP’ Branch

To switch to the ‘KMP’ branch, follow these steps:

1. Open a terminal in the project directory.
2. Run the command:
   ```bash
   git checkout KMP

## License

This project is licensed under the [MIT License](LICENSE).
