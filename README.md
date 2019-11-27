# Swiss Android (alpha)
=========================
App currently under development.

Libraries Used
--------------
* [Foundation][0] - Components for core system capabilities, Kotlin extensions and support for
  multidex and automated testing.
  * [AppCompat][1] - Degrade gracefully on older versions of Android.
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
* [UI][12] - Details on why and how to use UI Components in your apps - together or separate
  * [Fragment][13] - A basic unit of composable UI.
  * [Layout][14] - Lay out widgets using different algorithms.
* Third party
  * [Glide][15] for image loading
  * [Kotlin Coroutines][16] for managing background threads with simplified code and reducing needs for callbacks
  * [Dagger][17] for managing background threads with simplified code and reducing needs for callbacks


[0]: https://developer.android.com/jetpack/components
[1]: https://developer.android.com/topic/libraries/support-library/packages#v7-appcompat
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
[12]: https://developer.android.com/guide/topics/ui
[13]: https://developer.android.com/guide/components/fragments
[14]: https://developer.android.com/guide/topics/ui/declaring-layout
[15]: https://bumptech.github.io/glide/
[16]: https://kotlinlang.org/docs/reference/coroutines-overview.html
[17]: https://developer.android.com/training/dependency-injection/dagger-android

