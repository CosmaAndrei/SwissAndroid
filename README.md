# Swiss Android (alpha)

This application is currently under development.

Libraries Used
--------------
* [Foundation][0] - Components for core system capabilities, Kotlin extensions and support for
  multidex and automated testing.
  * [Android KTX][2] - Write more concise, idiomatic Kotlin code.
  * [Test][3] - An Android testing framework for unit and runtime UI tests.
* [Architecture][4] - A collection of libraries that help you design robust, testable, and
  maintainable apps. Start with classes for managing your UI component lifecycle and handling data
  persistence.
  * [Data Binding][5] - Declaratively bind observable data to UI elements.
  * [Lifecycles][6] - Create a UI that automatically responds to lifecycle events.
  * [LiveData][7] - Build data objects that notify views when the underlying database changes.
  * [Navigation][8] - Handle everything needed for in-app navigation.
  * [Room][9] - Access your app's SQLite database with in-app objects and compile-time checks.
  * [ViewModel][10] - Store UI-related data that isn't destroyed on app rotations. Easily schedule
     asynchronous tasks for optimal execution.
  * [WorkManager][11] - Manage your Android background jobs.
* [MVVM][12] - MVVM pattern was used for the presentation layer
* Third party
  * [Retrofit][15] for network requests
  * [Kotlin Coroutines][16] for managing background threads with simplified code and reducing needs for callbacks
  * [Dagger][17] for dependency injection
  * [Timber][18] for logging



[0]: https://developer.android.com/jetpack/components
[2]: https://developer.android.com/kotlin/ktx
[3]: https://developer.android.com/training/testing/
[4]: https://developer.android.com/jetpack/arch/
[5]: https://developer.android.com/topic/libraries/data-binding/
[6]: https://developer.android.com/topic/libraries/architecture/lifecycle
[7]: https://developer.android.com/topic/libraries/architecture/livedata
[8]: https://developer.android.com/topic/libraries/architecture/navigation/
[9]: https://developer.android.com/topic/libraries/architecture/room
[10]: https://developer.android.com/topic/libraries/architecture/viewmodel
[11]: https://developer.android.com/topic/libraries/architecture/workmanager
[12]: https://developer.android.com/jetpack/docs/guide
[15]: https://square.github.io/retrofit/
[16]: https://kotlinlang.org/docs/reference/coroutines-overview.html
[17]: https://developer.android.com/training/dependency-injection/dagger-android
[18]: https://github.com/JakeWharton/timber


