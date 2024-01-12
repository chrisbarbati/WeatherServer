
# Weather Server with Spring Boot

This repository contains a simple Weather Server implemented using Spring Boot. The server exposes a RESTful API endpoint that retrieves temperature, humidity, and pressure information from the SenseHAT sensors on a Raspberry Pi using the [SenseHATI2C](https://github.com/chrisbarbati/SenseHatI2C/tree/main) library, which I wrote myself.

## Functionality

The Weather Server's primary functionality is to respond to GET requests by retrieving real-time environmental data from the SenseHAT sensors. Upon receiving a request to `/API/weather`, the server calls functions in the SenseHATI2C class to gather current temperature, humidity, and pressure readings. This data is then formatted as JSON and returned as the API response.

## Making API Requests

Send a GET request to retrieve the weather information:

```
GET /API/weather
```

The server will respond with a JSON object containing the current temperature, humidity, and pressure readings. To test it, I am currently hosting it [on a Raspberry Pi at my home](http://chrisbarbati.ddns.net:2048/API/weather). HTTP only, until I get the SSL certificates installed.

## Purpose

This repository serves as a demonstration of implementing a basic RESTful API using Spring Boot to retrieve sensor data from the SenseHAT on a Raspberry Pi. It showcases familiarity with Spring Boot's capabilities for building simple yet effective server applications.

## Acknowledgment

This project utilizes the [SenseHATI2C](https://github.com/chrisbarbati/SenseHatI2C/tree/main) library, authored by myself, to interface with SenseHAT sensors.

---

This project is created to showcase Spring Boot skills and is intentionally kept straightforward. For any questions or feedback, please contact [Christian Barbati](mailto:chris.barbati@gmail.com)