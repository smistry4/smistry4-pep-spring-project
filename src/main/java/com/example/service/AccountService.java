package com.example.service;

import java.util.Optional;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.ClientErrorException;
import com.example.exception.ResourceAlreadyExistsException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private AccountRepository accountRepository;
    
    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * This method adds a user to the database if the user doesn't already exist, other fields are valid.
     * @param account the user supplied information
     * @return The newly created Account
     * @throws ResourceAlreadyExistsException
     * @throws ClientErrorException
     */
    public Account addUserAccount(Account account) throws ResourceAlreadyExistsException, ClientErrorException {
        Optional<Account> optionalAccount = accountRepository.findByUsername(account.getUsername());
        
        if (optionalAccount.isPresent()) {
            throw new ResourceAlreadyExistsException("Account with username " + account.getUsername() + " already exists");
        }
        if (!account.getUsername().isEmpty() &&
            account.getPassword().length() >= 4) {
                accountRepository.save(account);
        }

        return accountRepository.findByUsername(account.getUsername()).orElseThrow(() -> new ClientErrorException("Account creation not successful."));
    }

    /**
     * This method logins a user based on the username and password.
     * @param account the user supplied credentials.
     * @return the Account if a user exist.
     * @throws AuthenticationException
     */
    public Account processUserLogin(Account account) throws AuthenticationException {
        return accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword()).orElseThrow(() -> new AuthenticationException("Login request unsuccessful."));
    }

    /**
     * This method retrieves a user using id.
     * @param id the user supplied id.
     * @return An account with id.
     * @throws ClientErrorException
     */
    public Account getAccountById(int id) throws ClientErrorException {
        return accountRepository.findById(id).orElseThrow(() -> new ClientErrorException("Account with id " + id + " does not exist"));
    }


}
