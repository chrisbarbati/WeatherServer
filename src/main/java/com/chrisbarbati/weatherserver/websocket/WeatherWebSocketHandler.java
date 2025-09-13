package com.chrisbarbati.weatherserver.websocket;

import com.chrisbarbati.weatherserver.weather.model.Weather;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.apache.logging.log4j.*;

/**
 * Websocket handler for the {@link Weather} broadcast function
 *
 * @since 1.0.0
 * @author Christian Barbati
 */
@Component
public class WeatherWebSocketHandler extends TextWebSocketHandler {

    private static final Logger logger = LogManager.getLogger(WeatherWebSocketHandler.class);

    // Member variables
    private Weather lastWeather;
    private static final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Default constructor
     *
     * @since 1.0.0
     * @author Christian Barbati
     */
    public WeatherWebSocketHandler() {
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);  // Add the session when connected
        logger.info("Connection established. Total sessions: {}", sessions.size());
        if (lastWeather != null) {
            broadcastWeather(lastWeather);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);  // Remove the session when connection closes
        System.out.println("Connection closed. Total sessions: " + sessions.size());
    }

    /**
     * Broadcast the weather data to all connected clients.
     *
     * @param weather Weather data to broadcast
     * @since 1.0.0
     * @author Christian Barbati
     */
    public void broadcastWeather(Weather weather) {
        // Only broadcast if the weather has changed
        if(weather.equals(lastWeather)){
            return;
        }

        lastWeather = weather;

        String jsonMessage = null;
        try {
            jsonMessage = objectMapper.writeValueAsString(weather);
        } catch (JsonProcessingException e) {
            logger.error("Error converting weather object to JSON", e);
        }

        TextMessage message = new TextMessage(jsonMessage);

        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(message);
                } catch (IOException e) {
                    logger.error("Error sending message to session", e);
                }
            }
        }
    }
}
