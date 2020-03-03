# espace app
View objects in space from the size of an asteroid all the way to the Sun. See asteroids, comets, planets, and their orbits. Scroll around on a map of the solar system, watching the objects move along their orbits in real time or fast forwarding through time watching the objects move. Filter different types of astronomical objects so you can focus on the types of objects that you want. 
## Features
1. Scroll around the solar system
2. Zoom the view of the solar system in and out
3. See space objects like asteroids, comets, and planets
4. See the orbit trails of the objects
5. Simulate where the objects are in space over time (Fast forward and Rewind)
6. Tap on space objects to get details about that object
7. Filter (Search) which objects can be seen
8. Quick 'travel' to various interesting objects around the solar system from a menu
9. Objects can be favourited so the user can quickly view them or get details
10. Create fictional objects in space, with their own mass and orbit
11. Browse/Search news articles
12. Notifications for news articles
13. Sound effects in the map mode while scrolling around the map, etc.
14. Background music
15. Browse through astronomy pictures of the day
## APIs
1. [Asterank REST API](https://www.programmableweb.com/api/nasas-asterank-rest-api) makes use of NASA's JPL Small-Body Database.
2. [News API](https://newsapi.org/)
3. [NASA Astronomy Picture of the Day](https://apod.nasa.gov/apod/astropix.html)
## Resources
Planetary data will be stored in the .apk or in a sqlite database, but considering the small data set a database would be unnecessary. A database will be used to cache pulled data from the JPL Small-Body Database as well as persist favourites and saved fictional space objects.

### Data Embedded in the APK
The data that will be embedded into the APK will be planetary orbital information, and physical data. An example of the data to be embedded can be found at [Lumen Learning](https://courses.lumenlearning.com/astronomy/chapter/physical-and-orbital-data-for-the-planets/).
