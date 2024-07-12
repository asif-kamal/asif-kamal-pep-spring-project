package com.example.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.service.MessageService;

@RestController
public class MessageController {
    
    @Autowired
    private MessageService messageService;
    @Autowired
    private AccountRepository accountRepository;


    @GetMapping("/messages")
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId) {
        Message foundMessage = messageService.getMessageById(messageId);
        return ResponseEntity.ok(foundMessage);
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByAccountId(@PathVariable Integer accountId) {
        List<Message> messages = messageService.getAllMessagesByUser(accountId);
        return ResponseEntity.ok(messages);
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        if (message.getMessageText().isBlank() || message.getMessageText().length() > 255) {
            return ResponseEntity.badRequest().build(); 
        }
        if (!accountRepository.existsById(message.getPostedBy())) {
            return ResponseEntity.badRequest().build(); 
        }
        Message createdMessage = messageService.saveMessage(message);
        return ResponseEntity.ok(createdMessage);
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable Integer messageId) {
        int rowsDeleted = messageService.deleteMessageById(messageId);
        if (rowsDeleted > 0) {
            return ResponseEntity.ok(rowsDeleted);
        } else {
            return ResponseEntity.ok().build();
        }
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<?> updateMessage(@PathVariable Integer messageId, @RequestBody Message updatedMessage) {
        try {
            if (updatedMessage.getMessageText().length() > 255 || updatedMessage.getMessageText().isBlank()) {
                return ResponseEntity.badRequest().body("Message is blank or exceeds 255 characters");
            }
            Message patchedMessage = messageService.updateMessageById(messageId, updatedMessage);
    
            if (patchedMessage == null) {
                return ResponseEntity.badRequest().body("No message found with ID " + messageId);
            }
            return ResponseEntity.ok(1);
    
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
