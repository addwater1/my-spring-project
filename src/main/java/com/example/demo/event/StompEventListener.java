package com.example.demo.event;

import com.example.demo.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.List;

@Component
public class StompEventListener {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @EventListener
    public void handleSessionConnect(SessionConnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
        String login = accessor.getLogin();
        redisUtil.addUser(sessionId, login);
    }

    @EventListener
    public void handleSessionConnected(SessionConnectedEvent event) {
        List<String> userList = redisUtil.getAllUsers();
        simpMessagingTemplate.convertAndSend("/topic/connect", userList);
        System.out.println(userList);
    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();
        redisUtil.deleteUser(sessionId);
        List<String> userList = redisUtil.getAllUsers();
        System.out.println(userList);
        simpMessagingTemplate.convertAndSend("/topic/connect", userList);
    }
}
