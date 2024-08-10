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

    /**
     * This method creates a new message in the database if the user exists, and message is valid(length between 1 and 255).
     * @param message the message to create
     * @return the newly created message.
     * @throws ClientErrorException
     */
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

    /**
     * This method retrieves all the messages in the database.
     * @return a List of messages
     */
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    /**
     * This method retrieves a message using id.
     * @param id the message id.
     * @return the retrieved message.
     */
    public Message getMessageById(int id) {
        return messageRepository.findById(id).orElse(null);
    }

    /**
     * The method deletes a message identified by the id.
     * @param id the message id.
     * @return the response of delete.
     */
    public String deleteMessageById(int id) {
        Message messageToDelete = getMessageById(id);
        if (messageToDelete != null) {
            messageRepository.deleteById(id);
            return "1 Row updated";
        }
        return null;
    }

    /**
     * This method updates a message identified by id, and new message identified by text.
     * @param id
     * @param text
     * @return the status of update
     * @throws ClientErrorException
     */
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

    /**
     * This method returns a list of messages identified by user id.
     * @param id the user id.
     * @return a list of messages.
     */
    public List<Message> getAllMessageByUser(int id) {
        return messageRepository.findByPostedBy(id).orElse(null);
    }
}
