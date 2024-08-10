package com.example.controller;

import java.util.List;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    
    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    /**
     * This method registers a new user in the database.
     * @param account contains the Account Entity
     * @return Response Entity with the newly created account
     */
    @PostMapping("register")
    public ResponseEntity<Account> registerUser(@RequestBody Account account) {
        Account returnedAccount = accountService.addUserAccount(account);
        return ResponseEntity.status(200).body(returnedAccount);
    }

    /**
     * This method logins a user based on the provided credentials.
     * @param account contains the Account Entity
     * @return  Response Entity with the Account if successful.
     * @throws AuthenticationException when username and password don't match.
     */
    @PostMapping("login")
    public ResponseEntity<Account> loginUser(@RequestBody Account account) throws AuthenticationException {
        Account returnedAccount = accountService.processUserLogin(account);
        return ResponseEntity.status(200).body(returnedAccount);
    }

    /**
     * This method creates a new message in the database.
     * @param message contains the Message Entity with the user supplied information
     * @return Response Entity with newly created message.
     */
    @PostMapping("messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message returnedMessage = messageService.addMessage(message);
        return ResponseEntity.status(200).body(returnedMessage);
    }

    /**
     * This method returns all the messages that exist in the database.
     * @return a List of messages
     */
    @GetMapping("messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }

    /**
     * This method retrieves a message using the id supplied by the user.
     * @param messageId the message id.
     * @return The message entity with the messsage id.
     */
    @GetMapping("messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId) {
        return ResponseEntity.status(200).body(messageService.getMessageById(messageId));
    }

    /**
     * This method deletes a message identified by the id.
     * @param messageId the message id
     * @return a Response Entity with number of rows updated if successful
     */
    @DeleteMapping("messages/{messageId}")
    public ResponseEntity<String> deleteMessageById(@PathVariable Integer messageId) {
        return ResponseEntity.status(200).body(messageService.deleteMessageById(messageId));
    }

    /**
     * This method updates a message identified by the id with the information in request body.
     * @param message the new message values
     * @param messageId the message id
     * @return a Response Entity with the status of the update.
     */
    @PatchMapping("messages/{messageId}")
    public ResponseEntity<String> updateMessageById(@RequestBody Message message, @PathVariable Integer messageId) {
        String rv = messageService.updateMessageById(messageId, message.getMessageText());
        return ResponseEntity.status(200).body(rv);
    }

    /**
     * This method returns all the messages created by the user.
     * @param accountId the user identifier
     * @return a List of all the messages by a user.
     */
    @GetMapping("accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByUser(@PathVariable Integer accountId) {
        return ResponseEntity.status(200).body(messageService.getAllMessageByUser(accountId));
    }

    /**
     * Handles the authentication exception
     * @param ex the exception
     * @return the message generated
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleUnauthorized(AuthenticationException ex) {
        return ex.getMessage();
    }
}
