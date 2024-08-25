# HarryPotterCharactersApp


This is designed to display a list of characters from the Harry Potter series. Users can click on any character to view more details

This sample project is a demo application that is based on modern Android architectures.
It will fetch the data from the network and cache it using the Room DB via MVVM pattern. We are using the API's for this purpose.

## App Architecture
The app uses clean architecture and MVVM pattern.


##  Core Features
* Implement the MVVM Architecture Pattern
* Mediate data operations with the Repository Pattern
* Observe and respond to changing data using LiveData.
* Used Kotlin Coroutines
* Uses Room for Data persistence
* ViewModel
* Dagger Hilt for dependency injection.

* Handle app navigation with Navigation Component
* Minimum SDK level 28


## Core Libraries
*  
*   [Retrofit 2](https://github.com/square/retrofit) and
* [Coroutines]
*   [Gson](https://github.com/google/gson) for parsing JSON
*   [Navigation](https://developer.android.com/jetpack/compose/navigation) to navigate between screens
*   Kotlin Coroutines


* Jetpack Compose for building UI.

## Testing
* I added local unit tests to test the usecases

* MockK for unit testing