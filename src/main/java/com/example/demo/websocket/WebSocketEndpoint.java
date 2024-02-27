package com.example.demo.websocket;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@ServerEndpoint(value = "/websocket/{userId}")
@Component
public class WebSocketEndpoint {
    @OnOpen
    public void onOpen(Session session , @PathParam("userId") String userId) {
        SessionPool.put(userId, session);
    }
    @OnClose
    public void onClose(Session session) throws IOException {
        SessionPool.close(session.getId());
    }
    @OnMessage
    public void onMessage(String message) {
        SessionPool.sendMessage(message);
    }
}
