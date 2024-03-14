package com.example.demo.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class StompEventListener {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @EventListener
    public void handleSessionConnect(SessionConnectEvent event) {
        System.out.println(event.getMessage());
    }
    @EventListener
    public void handleSessionConnected(SessionConnectedEvent event) {
        System.out.println(event.getMessage());
    }
    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        System.out.println(event.getSessionId());
    }
}
