
# Weather Server with Spring Boot

This repository contains a simple Weather Server implemented using Spring Boot. The server exposes a RESTful API endpoint that retrieves temperature, humidity, and pressure information from the SenseHAT sensors on a Raspberry Pi using the [SenseHAT](https://github.com/chrisbarbati/SenseHat/tree/main) library, which I wrote myself.

## Functionality

The Weather Server's primary functionality is to respond to GET requests by retrieving real-time environmental data from the SenseHAT sensors. Upon receiving a request to `/API/weather`, the server calls functions in the SenseHATI2C class to gather current temperature, humidity, and pressure readings. This data is then formatted as JSON and returned as the API response.

## Making API Requests

Send a GET request to retrieve the weather information:

```
GET /API/weather
```

The default units are degrees Celsius and pressure in Millibar, but adding request parameters will allow you to select other units if desired:

```
GET /API/weather?temp-unit=fahrenheit&pressure-unit=psi
```

At present the supported temperature units are Celsius, Fahrenheit, Kelvin. Supported pressure units are Millibar and PSI (lbs/in^2).

The server will respond with a JSON object containing the current temperature, humidity, and pressure readings. To test it, I am currently hosting it [on a Raspberry Pi at my home](https://chrisbarbati.ddns.net:2048/API/weather).
## Purpose

This repository serves as a demonstration of implementing a basic RESTful API using Spring Boot to retrieve sensor data from the SenseHAT on a Raspberry Pi. It showcases familiarity with Spring Boot as well as general object-oriented programming and software development skills.

## Acknowledgment

This project utilizes the [SenseHATI2C](https://github.com/chrisbarbati/SenseHatI2C/tree/main) library, authored by myself, to interface with SenseHAT sensors.

---

This project is created to showcase Spring Boot skills and is intentionally kept straightforward. For any questions or feedback, please contact [Christian Barbati](mailto:chris.barbati@gmail.com)
