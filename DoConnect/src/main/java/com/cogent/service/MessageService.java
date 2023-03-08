package com.cogent.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cogent.entity.Chat;
import com.cogent.entity.Message;
import com.cogent.exception.InputChecker;
import com.cogent.repository.MessageRepository;

/**
 * Service to connect API Message-related 
 * end points to MessageRepository
 * @author michaelmiranda
 * @since 1.0
 */
@Service
public class MessageService{
	/** The JPA Message Entity Repository to modify SQL */
	@Autowired
	private MessageRepository messageRepository;
	
	/** Service to get questions */
	@Autowired
	private ChatService chatService;

	/**
	 * Service Method to request saving a 
	 * Message Entity given
	 * through the JPARepository 
	 * 
	 * @param message the message to save in the database
	 * @return ResponseEntity with HTTP 201 response with URI location encoded
	 * 
	 * @see org.springframework.data.jpa.repository.CrudRepository#count()
	 * @see org.springframework.data.jpa.repository.CrudRepository#save(S)
	 * @see org.springframework.http.ResponseEntity#created(URI)
	 * @see org.springframework.http.ResponseEntity.HeadersBuilder#build()
	 * @since 1.0
	 */
	public ResponseEntity<String> saveMessage(Message message) {
		/* Find if the these two Users already have a chat object together */
		Optional<Chat> chatOptional = chatService.getAllByIDOrUsers(
				message.getChat().getId(), message.getChat().getUserA(), message.getChat().getUserB());
		Chat chat;
		/* If nothing is found, create a new chat object between two users and register it */
		if (chatOptional.isEmpty()) {
			chat = new Chat(null, message.getChat().getUserA(),
					message.getChat().getUserB(), new ArrayList<Message>());
			chatService.saveChat(chat);
		} else {
			chat = chatOptional.get();
		}
		
		message.setChat(chat);
		Message messageSaved = messageRepository.save(message);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(messageSaved.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	/**
	 * Service Method to return a single
	 * Message Entity via id given 
	 * through the JPARepository 
	 * 
	 * @param id the unique id to search for
	 * @return Optional Object with a Message Entity if found or Null
	 * 
	 * @see org.springframework.data.jpa.repository.CrudRepository#findById(Id)
	 * @since 1.0
	 */
	public Optional<Message> get(Long id){
		return messageRepository.findById(id);
	}

	/**
	 * Service Method to request
	 * all Message Entities 
	 * through the JPARepository 
	 * 
	 * @return list of Message entities
	 * 
	 * @see org.springframework.data.jpa.repository.JpaRepository#findAll()
	 * @since 1.0
	 */
	public List<Message> getAll(){
		return (List<Message>) messageRepository.findAll();
	}
	
	/**
	 * Service Method to request
	 * all Message Entities filtered by messageTopic
	 * through the JPARepository 
	 * 
	 * @param messageTopic the string to filter by
	 * @return list of Message entities
	 * 
	 * @see com.cogent.repository.MessageRepository#findByChat(Chat)
	 * @since 1.0
	 */
	public List<Message> getAllByChat(Chat chat){
		InputChecker.checkObjectIsNull(chat);
		return (List<Message>) messageRepository.findByChat(chat);
	}
	
	/**
	 * Service Method to request deletion of a 
	 * Message Entity
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
		Optional<Message> messageOptional = messageRepository.findById(id);
		
		InputChecker.checkOptionalIsEmpty(messageOptional);
		
		messageRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	

}
