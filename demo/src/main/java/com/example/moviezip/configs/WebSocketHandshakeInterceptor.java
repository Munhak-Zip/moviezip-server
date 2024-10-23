package com.example.moviezip.configs;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        // Extract the session from the request
        if (request instanceof org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor) {
            HttpSession session = ((javax.servlet.http.HttpServletRequest) request).getSession(false);
            if (session != null) {
                attributes.put("sessionId", session.getId());
                attributes.put("username", session.getAttribute("username"));
                return true;
            }
        }
        return false; // Reject the handshake if the session is invalid
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        // No post-handshake actions needed
    }
}
