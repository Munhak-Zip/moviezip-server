package com.example.moviezip.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

//Client는 React를 사용하기 때문에 CORS를 허용해야만 한다.
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
/*
flow
ws://localhost:8080/chat를 호출하면 websocket 연결이 될 것이다.
이후 /sub를 통해 채팅방에 구독 신청을 하고,
채팅 데이터를 전송할 때 마다 /pub관련 메서드를 호출해 채팅방 구독하는 모두에게 메시지 브로커가 메시지를 전달할 것이다.
* */

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        //ws://localhost:8080/chat를 호출하면 websocket 연결이 될 것
        registry.addEndpoint("/chat") //socket 연결 url
                .setAllowedOriginPatterns("http://localhost:3000")//CORS 허용 범위
                .withSockJS(); //낮은 버전 브라우저도 사용할 수 있도록


    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){
        registry.enableSimpleBroker("/topic", "/queue"); //메시지 구독 요청 : 메시지 송신
        registry.setApplicationDestinationPrefixes("/app"); //메시지 발행 요청: 메시지 수신
    }

//    @Override
//    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
//        registration.setMessageSizeLimit(1024 * 1024); // Set max message size
//    }

}
