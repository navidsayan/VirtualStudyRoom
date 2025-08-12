package com.example.virtualstudyroom.service;

import com.example.virtualstudyroom.model.Message;
import com.example.virtualstudyroom.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> getMessages() {
        return messageRepository.findAll();
    }

    public void addNewMessage(Message message) {
        // Add validation if needed
        messageRepository.save(message);
    }
}
