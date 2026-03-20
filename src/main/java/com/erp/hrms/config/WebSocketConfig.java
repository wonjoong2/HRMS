package com.erp.hrms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
//WebSocket + STOMP 활성화
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    //pub : 클라이언트 -> 서버(클라이언트가 메시지 보낼 때 /pub로 시작)
    //sub : 서버 -> 클라이언트
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/sub");
        registry.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //웹소켓 접속 주소
        registry.addEndpoint("/ws")
                //CORS 허용
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}