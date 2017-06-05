**Game of Life**

Implementation of Conway's Game of Life game based on Java and Angular js

https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life

**Technology stack**

Spring boot
Spring Rest
AngularJS
AssertJ for tests

**Rest Endpoints**

* POST /gol/?height=10&widht=20 - returns boolean[][] board
* PUT /gol/next BODY board:boolean[][] - does 'game of life move' on the proivded board

**Install and run**

1. `./gradlew build`
2. locate jar file in `build/libs/game-of-life-1.0.1-SNAPSHOT.jar`
3. `java -jar game-of-life-1.0.1-SNAPSHOT.jar`

To build and run Docker

`./gradlew buildDocker`

`docker run -p 8080:8080 outfittery/game-of-life`

By default app uses 8080 port, so to access the web page type
http://localhost:8080
