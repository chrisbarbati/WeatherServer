package com.chrisbarbati.weatherserver.Streaming;

import org.apache.logging.log4j.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{

    //Logging
    private static final Logger logger = LogManager.getLogger(WebSocketConfig.class);

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        logger.info("Registering WebSocket Handlers");
        registry.addHandler(new WeatherWebSocketHandler(), "/ws").setAllowedOrigins("*");
    }

}
