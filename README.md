
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

Possible later work: more endpoints. Date range (`from`/`to`) and time-interval sampling on `/API/weather/past` are implemented.


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
GET /API/weather/past?days=90
GET /API/weather/past?from=2024-01-01&to=2024-06-01
GET /API/weather/past?bucket-hours=0&days=7
```

By default this returns one sample every three hours, chosen in SQL so the Pi does not load the entire table into memory. Pass `bucket-hours=0` to get every stored row. Optional `days=90` means the last 90 days. Or pass calendar dates as `from` and `to` (`yyyy-MM-dd`). Samples are persisted about once a minute.

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

## Changelog
v1.0.87, 13 September 2025 - Perform wide-scale cleanup, upgrade project to JDK21, improve documentation throughout application. Convert versioning system to the standard semantic version (Major.Minor.Patch) rather than the simple incrementing number I used when I started developing this application two years ago.

## Acknowledgment

This project utilizes the [SenseHAT](https://github.com/chrisbarbati/SenseHat/tree/main) library, authored by myself, to interface with SenseHAT sensors.

---
For any questions or feedback, please contact [Christian Barbati](mailto:chris.barbati@gmail.com)
