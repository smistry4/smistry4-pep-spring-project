package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{

    /**
     * This property expression exceutes SQL query to get a record using username.
     * @param username
     * @return an Account with username
     */
    Optional<Account> findByUsername(String username);

    /**
     * This property expression executes SQL query to get a record with matching username and password.
     * @param username
     * @param password
     * @return an Account with username and password
     */
    Optional<Account> findByUsernameAndPassword(String username, String password);
}
