package server.api;

import org.junit.jupiter.api.Test;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import static org.mockito.Mockito.*;

class WebSocketConfigTest {

    @Test
    void testRegisterStompEndpoints() {
        StompEndpointRegistry registry = mock(StompEndpointRegistry.class);
        WebSocketConfig webSocketConfig = new WebSocketConfig();

        webSocketConfig.registerStompEndpoints(registry);

        verify(registry).addEndpoint("/websocket");
    }

    @Test
    void testConfigureMessageBroker() {
        MessageBrokerRegistry config = mock(MessageBrokerRegistry.class);
        WebSocketConfig webSocketConfig = new WebSocketConfig();

        webSocketConfig.configureMessageBroker(config);

        verify(config).enableSimpleBroker("/topic");
        verify(config).setApplicationDestinationPrefixes("/app");
    }
}
