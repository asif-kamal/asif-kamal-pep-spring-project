package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(int messageId) {
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        return optionalMessage.orElse(null);
    }

    public int deleteMessageById(Integer messageId) {
        return messageRepository.deleteMessageById(messageId);
    } 

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    public Message updateMessageById(Integer messageId, Message updatedMessage) {
        if (messageRepository.existsById(messageId)) {
            return messageRepository.save(updatedMessage);
        }
        return null;
    }

    public List<Message> getAllMessagesByUser(Integer accountId) {
        return messageRepository.findAllByPostedBy(accountId);
    }
}
