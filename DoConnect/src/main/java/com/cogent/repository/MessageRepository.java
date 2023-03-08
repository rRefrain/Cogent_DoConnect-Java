package com.cogent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cogent.entity.Chat;
import com.cogent.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long>{
	List<Message> findByChat(Chat chat);
}
