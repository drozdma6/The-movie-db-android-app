# TMDB android application

Simple application which enables users to browse popular or top rated movies from https://www.themoviedb.org/.
Users can view detail information, add movies to watchlist or search through movies by title. Detail information contains
movie's original language, short overview and a possibility to open www.themoviedb.org in browser and show even more
information.

## How it works
- [API](#api)
- [Database](#database)
- [Images](#images)
    - [Dynamic background color](#dynamic-background-color)
- [Dependency injection](#dependency-injection)
- [Firebase app distribution](#firebase-app-distribution)
- [Firebase Analytics](#firebase-analytics)

### API
The communication with [api](https://www.themoviedb.org/documentation/api) was handled by [retrofit](https://www.themoviedb.org/documentation/api).

### Database
[Room](https://developer.android.com/jetpack/androidx/releases/room), which works with SQLite database.

### Images
[Coil](https://coil-kt.github.io/coil/) was used for loading images to application.

#### Dynamic background color
[Palette](https://developer.android.com/reference/kotlin/androidx/palette/graphics/Palette) was used to calculate average colour from movie's poster and set background of detail fragment accordingly.

### Dependency injection
For dependency injection [Koin](https://insert-koin.io/) was used.

### Firebase app distribution
Firebase app distribution was used to register testers and to distribute app releases to those testers.

### Firebase Analytics
Log Event feature was used to study the most frequently used features of our application.






