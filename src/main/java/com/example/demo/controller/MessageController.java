package com.example.demo.controller;

import com.example.demo.dto.ChatMessage;
import com.example.demo.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MessageController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private RedisUtil redisUtil;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String sendMessage(String message) throws Exception {
        return message;
    }
    @MessageMapping("/specific")
    public void sendToUser(@Payload ChatMessage chatMessage) {
        String to = chatMessage.getTo();
        simpMessagingTemplate.convertAndSendToUser(to,"/queue", chatMessage);
    }
}
