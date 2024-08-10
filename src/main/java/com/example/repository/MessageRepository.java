package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{

    /**
     * This property expression is used to query messages by a particular user.
     * @param postedBy
     * @return a List of messages
     */
    Optional<List<Message>> findByPostedBy(int postedBy);
}
