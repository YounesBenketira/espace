# espace ([ɛs.pɑːs]) app
View objects in space from the size of an asteroid all the way to the Sun. See asteroids, comets, planets, and their orbits. Scroll around on a map of the solar system, watching the objects move along their orbits in real time or fast forwarding through time watching the objects move. Filter different types of astronomical objects so you can focus on the types of objects that you want. 
## Features
1. Interactive Map of the Solar System
   1. Scroll around the solar system
   2. Zoom the view of the solar system in and out
   3. See space objects like asteroids, comets, and planets
   4. See the orbit trails of the objects
   5. Tap on space objects to get details about that object
   6. Filter (& Search) which objects can be seen
   7. Objects can be favourited so the user can quickly view them or get details
   8. Quick 'travel' to various interesting objects around the solar system from a menu
   9. Sound effects in the map mode while scrolling around the map, whizzing passed objects, etc.
   10. Background music
2. Simulate where the objects are in space over time
   1. Planets are in their real time positions by default
   2. Planets move over time
   3. Can pause/play simulation, fast forward, rewind
3. Space objects details screen
   1. Can favourite the object
   2. View a picture and details about the object, pulled from Wikipedia
4. Create fictional objects in space, with their own mass and orbit
   1. Set the mass, orbit, velocity, look, etc.
   2. View the object that was created settle into the orbit, interact with different objects
5. Space/astronomy related news articles
   1. Browse/Search for news articles
   2. Notifications for news articles
   3. Can favourite news articles
6. Astronomy pictures of the day
   1. Browse/Search through the pictures
   2. Notifications when new pictures are updated
   3. Can favourite a POTD
## APIs
1. [Asterank REST API](https://www.programmableweb.com/api/nasas-asterank-rest-api) makes use of NASA's JPL Small-Body Database.
2. [News API](https://newsapi.org/)
3. [NASA Astronomy Picture of the Day](https://apod.nasa.gov/apod/astropix.html)
4. [Wikipedia API](https://en.wikipedia.org/api/rest_v1/)
## Libraries
1. [jPCT-AE](http://www.jpct.net/jpct-ae/index.html)
2. [Gson](https://github.com/google/gson)
3. [Volley](https://developer.android.com/training/volley)
## Resources
Planetary data will be stored in the .apk or in a sqlite database, but considering the small data set a database would be unnecessary. A database will be used to cache pulled data from the JPL Small-Body Database as well as persist favourites and saved fictional space objects.

### Data Embedded in the APK
Data present on the site [Lumen Learning](https://courses.lumenlearning.com/astronomy/chapter/physical-and-orbital-data-for-the-planets/) is an example of the kind of data that will be embedded into the APK, data such as planet diameter, mass, density, rotation, inclination, and velocity.