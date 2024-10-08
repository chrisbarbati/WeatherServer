
# Weather Server with Spring Boot

This repository contains a simple Weather Server implemented using Spring Boot. The server exposes a RESTful API endpoint that retrieves temperature, humidity, and pressure information from the SenseHAT sensors on a Raspberry Pi using the [SenseHAT](https://github.com/chrisbarbati/SenseHat/tree/main) library, which I wrote myself.

## Functionality

The Weather Server's primary functionality is to respond to GET requests by retrieving real-time environmental data from the SenseHAT sensors. Upon receiving a request to `/API/weather`, the server calls functions in the SenseHATI2C class to gather current temperature, humidity, and pressure readings. This data is then formatted as JSON and returned as the API response.

I have recently added some additional functionality:

 - RESTful API endpoint returning the current weather conditions
 - WebSocket endpoint providing streaming data at 1s intervals
 - Hibernate ORM Framework
 - Regular (10 minute interval) insertion of weather records into local MariaDB database
 - Unit tests with JUnit, logging
 - Caching to improve performance and reduce database load for subsequent calls
 - New API endpoints at /API/weather/past and /API/weather/pasthour that return past weather data as JSON

## Planned Features

Additional work to be done includes making the code more efficient and adding more API endpoint functionality (accepting a date range, or time interval) and possibly adding more endpoints


## Making API Requests

Send a GET request to retrieve the weather information:

```
GET /API/weather
```

Or to the following endpoint to receive the last hour of information as JSON:

```
GET /API/weather/pasthour
```

The default units are degrees Celsius and pressure in Millibar, but adding request parameters will allow you to select other units if desired:

```
GET /API/weather?temp-unit=fahrenheit&pressure-unit=psi
```

At present the supported temperature units are Celsius, Fahrenheit, Kelvin. Supported pressure units are Millibar and PSI (lbs/in^2).

The server will respond with a JSON object containing the current temperature, humidity, and pressure readings. To test it, I am currently hosting it [on a Raspberry Pi at my home](https://chrisbarbati.ddns.net:2048/API/weather).

To get past data, there is an additional endpoint:

```
GET /API/weather/past
```

This endpoint returns a JSON object representing all past historical data, stored in ten minute intervals. This feature is currently in development and there may be changes in the near future.

## **Experimental - Weather Forecast **

There is an endpoint that will return a WeatherForecast object as JSON:

```
GET /API/weather/forecast
```
At present this feature is still in development. It currently returns the rate of change in barometric pressure (simple linear calculation over the past hour of data, and the slope of a quadratic equation fit to the past hour of data at the time of the last sample). Decreasing barometric pressure typically indicates precipitation, and increasing barometric pressure typically means clearer conditions.

## Purpose

This repository serves as a demonstration of implementing a basic RESTful API using Spring Boot to retrieve sensor data from the SenseHAT on a Raspberry Pi. It showcases familiarity with Spring Boot as well as general object-oriented programming and software development skills.

## Testing

Unit tests are included wherever possible. The only methods not tested are those that would fail due to my compiling on Windows hardware (IntelliJ remote development does not work on my Raspberry Pi for some reason, so I develop locally). For example, methods that cause an I2C read to occur will always fail as my Thinkpad does not have an I2C bus.

## Acknowledgment

This project utilizes the [SenseHATI2C](https://github.com/chrisbarbati/SenseHatI2C/tree/main) library, authored by myself, to interface with SenseHAT sensors.

---
For any questions or feedback, please contact [Christian Barbati](mailto:chris.barbati@gmail.com)
