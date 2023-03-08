package com.cogent.controller;

import java.util.Hashtable;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cogent.entity.Chat;
import com.cogent.entity.Message;
import com.cogent.service.ChatService;
import com.cogent.service.MessageService;

/**
 * Controller to deal with message-related entities
 * 
 * @author michaelmiranda
 * @since 1.0
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
public class MessageController {
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private ChatService chatService;
    
	/**
	 * Function to GET all messages or filtered messages based 
	 * off JSON object values
	 * 
	 * @param obj HashTable Object to filter out results
	 * @return HTTP Code 200 and a List of all (filtered) Message Objects on success
	 * 
	 * @see com.cogent.service.MessageService#getAll()
	 * @see com.cogent.service.MessageService#getAllByChat(Chat)
	 * @since 1.0
	 */
	@GetMapping(value = {"/messages"})
	public List<Message> getAll(@RequestBody(required=false) Hashtable<String, String> obj) {
		
		if (obj != null && obj.containsKey("userA") && obj.containsKey("userB")) {
			Chat chat = chatService.getByUsers(obj.get("userA"), obj.get("userB")).get();
			return messageService.getAllByChat(chat);
		}
		
		return messageService.getAll();
	}
	
	/**
	 * Function to GET all messages or filtered messages based 
	 * off JSON object values
	 * 
	 * @param obj HashTable Object to filter out results
	 * @return HTTP Code 200 and a List of all (filtered) Message Objects on success
	 * 
	 * @see com.cogent.service.MessageService#getAll()
	 * @see com.cogent.service.MessageService#getAllByChat(Chat)
	 * @since 1.0
	 */
	@GetMapping(value = {"/chatHeaders/{user}"})
	public List<Chat> getAllHeader(@PathVariable("user") String user) {
		return chatService.getAllBySingleUser(user);
	}
		
	/**
	 * Function to GET a message given their id
	 * 
	 * @param id the message id to look up
	 * @return HTTP Code 200 and the Message object on success
	 * @throws NoSuchElementException when messageName cannot be found
	 * 
	 * @see com.cogent.service.MessageService#get(Integer)
	 * @since 1.0
	 */
	@GetMapping(value = {"/messages/{id}"})
	public Message get(@PathVariable("id") Long id) 
			throws NoSuchElementException {
		Optional<Message> i = messageService.get(id);
		return i.get();
	}	
	
	/**
	 * Function to ADD a Message to the date base
	 * 
	 * @param Message to add
	 * @return HTTP Code 201 on successful Creation
	 * 
	 * @see com.cogent.service.MessageService#saveMessage(Message)
	 * @since 1.0
	 */
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@PostMapping(value = {"/messages"})
	public ResponseEntity<String> add(@RequestBody Message message) {
		return messageService.saveMessage(message);
	}

	
	/**
	 * Function to DELETE a single message given their ID
	 * 
	 * @param id the Message ID to look up and delete 
	 * @return HTTP Code 204 if successfully found and deleted or
	 * 			HTTP Code 404 if id is not found
	 * 
	 * @see com.cogent.service.messageService#delete(Integer)
	 * @since 1.0
	 */
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@DeleteMapping(value = {"/messages/{id}"})
	public ResponseEntity<String> delete(@PathVariable("id") Long id) {
		return messageService.delete(id);
	}
}
