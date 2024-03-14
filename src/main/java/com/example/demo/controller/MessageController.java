package com.example.demo.controller;

import com.example.demo.dto.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String sendMessage(String message) throws Exception {
        return message;
    }
    @MessageMapping("/specific")
    public void sendToUser(@Payload ChatMessage chatMessage) {
        String username = chatMessage.getUsername();
        String message = chatMessage.getMessage();
        simpMessagingTemplate.convertAndSendToUser(username,"/queue", message);
    }
}
