package com.cogent.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.cogent.entity.Chat;


public interface ChatRepository extends CrudRepository<Chat, Long> {

	 Optional<Chat> findByUserAAndUserB(String userA, String userB);
	
	 List<Chat> findByUserAOrUserB(String userA, String userB);
}