package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.ClientErrorException;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private MessageRepository messageRepository;
    private AccountService accountService;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountService accountService) {
        this.messageRepository = messageRepository;
        this.accountService = accountService;
    }

    public Message addMessage(Message message) throws ClientErrorException{
        Account account = accountService.getAccountById(message.getPostedBy());
        if (message.getMessageText().length() == 0 || message.getMessageText().length() > 255) {
            throw new ClientErrorException("Message length should be between 1 and 255");
        }
        Message rvMessage = null;
        if (account != null) {
            rvMessage = messageRepository.save(message);
        }
        return messageRepository.findById(rvMessage.getMessageId()).orElseThrow(() -> new ClientErrorException("Message creation unsuccessful."));
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(int id) {
        return messageRepository.findById(id).orElse(null);
    }

    public String deleteMessageById(int id) {
        Message messageToDelete = getMessageById(id);
        if (messageToDelete != null) {
            messageRepository.deleteById(id);
            return "1 Row updated";
        }
        return null;
    }

    public String updateMessageById(int id, String text) throws ClientErrorException{
        Message retrievedMessage = getMessageById(id);
        if (retrievedMessage == null || 
            text.isEmpty()||
            text.length() > 255) {
                throw new ClientErrorException("Update not successful");
        }
        retrievedMessage.setMessageText(text);
        messageRepository.save(retrievedMessage);
        return "1 Row updated";
    }

    public List<Message> getAllMessageByUser(int id) {
        return messageRepository.findByPostedBy(id).orElse(null);
    }
}
