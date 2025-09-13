package com.chrisbarbati.weatherserver.websocket;

import org.apache.logging.log4j.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Simple configuration class for WebSocket
 *
 * @since 1.0.0
 * @author Christian Barbati
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{

    private static final Logger logger = LogManager.getLogger(WebSocketConfig.class);

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        logger.info("Registering WebSocket Handlers");
        registry.addHandler(new WeatherWebSocketHandler(), "/ws").setAllowedOrigins("*");
    }

}
