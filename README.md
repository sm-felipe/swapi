###about

This app is a CRUD for planets inspired at Star Wars API.
It can also list the planets available there.

It has 2 controllers: a reactive and a blocking one. 
They are teared apart because JDBC with MySQL is not reactive,
and the reactive driver for MySQL is still too early created, so with the controllers separated 
they can be later separated in 2 apps and scaled up independently. 

###starting the database

    docker-compose up swapi-db
    
 ### building the app
 
    ./gradlew clean build
    
### running the app

    ./gradlew run
    
### Further improvements for the future
1. Use flyway to version the database
2. Separate the blocking controller from the reactive controller in two apps
3. Use the R2DBC driver for MySQL to eliminate the blocking controller
  