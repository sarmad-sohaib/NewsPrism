# NewsPrism
NewsPrim is an android application written in Kotlin using ```MVVM``` architecture to enable users to read breaking news (currently US) and search news. This app has ability to cache loaded breaking news using [Room Persistence Library](https://developer.android.com/jetpack/androidx/releases/room) until network source [newsapi.org](www.newsapi.org) updates breaking news to reduce api calls and network usage on device.

In this repository, you will find:

- User interface built using XML
- Navigation built using [Navigation components](https://developer.android.com/guide/navigation/navigation-getting-started)
- A presentation layer which contains one View (fragments and activities) and Corresponding ```ViewModel``` per screen.
- Reactive UIs built using [Flow](https://developer.android.com/kotlin/flow) and [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) for async operatinos.
- A data layer consist of two data sources, remote ([Retrofit](https://square.github.io/retrofit)) and local (Room) which are working collaboratively due to [paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-migration#benefits) ```RemoteMediatar``` to implement cache for loaded news, and paging to reduce api calls and increase app's performance. Read more about [Remote Mediator](https://developer.android.com/topic/libraries/architecture/paging/v3-network-db).
- Dependency injection using [Hilt](https://developer.android.com/training/dependency-injection/hilt-android).

## Why this app?

The development of this app is purely for education purpose. To learn about network calls, [architecture components](https://developer.android.com/topic/architecture?gclid=CjwKCAiAyfybBhBKEiwAgtB7foTrp3FJuBOxdV9k6HCigW8Jp0nvdj4HjmyVsGd4WNS2CbJyZ9rjvRoCkdMQAvD_BwE&gclsrc=aw.ds), room, image loading and caching, flows and coroutines, and pagination. Any beginner developer who wants to take a look to make these concepts work for them can use this app to learn. This app can help devs to learn about MVVM architecture, how to implement MVVM with multipule data sources, and how to write maintainable and testable apps using Google's recommended approaches.

## Opening NewsPrism in Android Studio
1. Clone the repository:

```git clone git@github.com/sarmad-sohaib/NewsPrism.git```

Note: This app is consist of only one branch so no need to switch branches to go through app.

Contributions are welcomed :)
