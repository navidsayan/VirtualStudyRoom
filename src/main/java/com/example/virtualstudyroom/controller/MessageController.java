package com.example.virtualstudyroom.controller;

import com.example.virtualstudyroom.model.Message;
import com.example.virtualstudyroom.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public List<Message> getMessages() {
        return messageService.getMessages();
    }

    @PostMapping
    public void addMessage(@RequestBody Message message) {
        messageService.addNewMessage(message);
    }
}
