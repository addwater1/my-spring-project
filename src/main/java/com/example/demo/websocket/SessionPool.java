package com.example.demo.websocket;

import jakarta.websocket.Session;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionPool {
    private static Map<String, Session> sessions = new ConcurrentHashMap<>();

    public static void close(String sessionId) throws IOException {
        Session session = sessions.get(sessionId);
        if(session != null) {
            sessions.get(sessionId).close();
        }
    }
    public static void put(String userId, Session session){
        sessions.put(userId, session);
    }

    public static void sendMessage(String sessionId, String message) {
        sessions.get(sessionId).getAsyncRemote().sendText(message);
    }

    public static void sendMessage(String message) {
        for(String sessionId : sessions.keySet()) {
            sessions.get(sessionId).getAsyncRemote().sendText(message);
        }
    }
}
