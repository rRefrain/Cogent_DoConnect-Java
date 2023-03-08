package com.cogent.service;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cogent.entity.Chat;
import com.cogent.exception.InputChecker;
import com.cogent.repository.ChatRepository;

/**
 * Service to connect API Chat-related 
 * end points to ChatRepository
 * @author michaelmiranda
 * @since 1.0
 */
@Service
public class ChatService{
	/** The JPA Chat Entity Repository to modify SQL */
	@Autowired
	private ChatRepository chatRepository;
	
	/**
	 * Service Method to request saving a 
	 * Chat Entity given
	 * through the JPARepository 
	 * 
	 * @param chat the chat to save in the database
	 * @return ResponseEntity with HTTP 201 response with URI location encoded
	 * 
	 * @see org.springframework.data.jpa.repository.CrudRepository#count()
	 * @see org.springframework.data.jpa.repository.CrudRepository#save(S)
	 * @see org.springframework.http.ResponseEntity#created(URI)
	 * @see org.springframework.http.ResponseEntity.HeadersBuilder#build()
	 * @since 1.0
	 */
	public ResponseEntity<String> saveChat(Chat chat) {
		Chat chatSaved = chatRepository.save(chat);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(chatSaved.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	/**
	 * Service Method to return a single
	 * Chat Entity via id given 
	 * through the JPARepository 
	 * 
	 * @param id the unique id to search for
	 * @return Optional Object with a Chat Entity if found or Null
	 * 
	 * @see org.springframework.data.jpa.repository.CrudRepository#findById(Id)
	 * @since 1.0
	 */
	public Optional<Chat> get(Long id){
		return chatRepository.findById(id);
	}

	/**
	 * Service Method to request
	 * all Chat Entities 
	 * through the JPARepository 
	 * 
	 * @return list of Chat entities
	 * 
	 * @see org.springframework.data.jpa.repository.JpaRepository#findAll()
	 * @since 1.0
	 */
	public List<Chat> getAll(){
		return (List<Chat>) chatRepository.findAll();
	}
	
	/**
	 * Service Method to request
	 * all Chat Entities 
	 * through the JPARepository 
	 * 
	 * @return list of Chat entities
	 * 
	 * @see org.springframework.data.jpa.repository.JpaRepository#findAll()
	 * @since 1.0
	 */
	public List<Chat> getAllBySingleUser(String user){
		return (List<Chat>) chatRepository.findByUserAOrUserB(user, user);
	}
	
	/**
	 * Service Method to request
	 * all Message Entities filtered by users
	 * through the JPARepository 
	 * 
	 * @param messageTopic the string to filter by
	 * @return list of Message entities
	 * 
	 * @see com.cogent.repository.MessageRepository#findByChat(Chat)
	 * @since 1.0
	 */
	public Optional<Chat> getByUsers(String userA, String userB){
		InputChecker.checkObjectIsNull(userA);
		InputChecker.checkObjectIsNull(userB);
		Optional<Chat> chatOptional = chatRepository.findByUserAAndUserB(userA, userB);
		if (chatOptional.isEmpty()) {
			return chatRepository.findByUserAAndUserB(userB, userA);
		}
		return chatOptional;
	}
	
	/**
	 * Service Method to request
	 * all Message Entities filtered by id or users
	 * through the JPARepository 
	 * 
	 * @param messageTopic the string to filter by
	 * @return list of Message entities
	 * 
	 * @see com.cogent.repository.MessageRepository#findByChat(Chat)
	 * @since 1.0
	 */
	public Optional<Chat> getAllByIDOrUsers(Long id, String userA, String userB) {
		Optional<Chat> chatOptional;
		
		if (id != null && (chatOptional = get(id)).isPresent()) {
			return chatOptional;
		}
		chatOptional = getByUsers(userA, userB);
		return chatOptional;
	}
	
	/**
	 * Service Method to request deletion of a 
	 * Chat Entity
	 * through the JPARepository 
	 * 
	 * @param id the unique id to search for
	 * @return ResponseEntity with HTTP 204 response on success 
	 * 			or HTTP 404 if id not found
	 * 
	 * @see org.springframework.data.repository.CrudRepository.findById(Integer id)
	 * @see org.springframework.http.ResponseEntity.notFound()
	 * @see com.cogent.exception.InputChecker#checkOptionalIsEmpty(Optional)
	 * @see org.springframework.http.ResponseEntity.HeadersBuilder#build()
	 * @see org.springframework.http.ResponseEntity.noContent()
	 * @see org.springframework.data.repository.CrudRepository.deleteById(Integer id)
	 * @since 1.0
	 */
	public ResponseEntity<String> delete(Long id) {
		Optional<Chat> chatOptional = chatRepository.findById(id);
		
		InputChecker.checkOptionalIsEmpty(chatOptional);
		
		chatRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	

}
