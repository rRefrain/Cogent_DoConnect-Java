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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cogent.entity.Question;
import com.cogent.entity.QuestionDTO;
import com.cogent.service.QuestionService;

/**
 * Controller to deal with question-related entities
 * 
 * @author michaelmiranda
 * @since 1.0
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
public class QuestionController {
	@Autowired
	private QuestionService questionService;
	
	/**
	 * Function to GET all questions or filtered questions based 
	 * off JSON object values
	 * 
	 * @param obj HashTable Object to filter out results
	 * @return HTTP Code 200 and a List of all (filtered) Question Objects on success
	 * 
	 * @see com.cogent.service.QuestionService#getAll()
	 * @see com.cogent.service.QuestionService#getAllByStatus(String)
	 * @see com.cogent.service.QuestionService#getAllByTopic(String)
	 * @since 1.0
	 */
	@GetMapping(value= {"/questions"})
	public List<Question> getAll(@RequestBody(required=false) Hashtable<String, String> obj) {
		
		if(obj != null) {
			if(obj.containsKey("status")) {
				return questionService.getAllByStatus(obj.get("status").toString());
			} else if(obj.containsKey("topic")) {
				return questionService.getAllByTopic(obj.get("topic").toString());
			}
		}
				
		return questionService.getAll();
	}
		
	/**
	 * Function to GET a question given their id
	 * 
	 * @param id the question id to look up
	 * @return HTTP Code 200 and the Question object on success
	 * @throws NoSuchElementException when questionName cannot be found
	 * 
	 * @see com.cogent.service.QuestionService#get(Integer)
	 * @since 1.0
	 */
	@GetMapping(value = {"/questions/{id}"})
	public Question get(@PathVariable("id") Long id) 
			throws NoSuchElementException {
		Optional<Question> i = questionService.get(id);
		return i.get();
	}
	
	/**
	 * Function to ADD a Question to the date base
	 * 
	 * @param Question to add
	 * @return HTTP Code 201 on successful Creation
	 * 
	 * @see com.cogent.service.QuestionService#saveQuestion(Question)
	 * @since 1.0
	 */
	@PostMapping(value = {"/questions"})
	public ResponseEntity<String> add(@RequestBody Question question) {
		return questionService.saveQuestion(question);
	}
	
	/**
	 * Function to UPDATE a Question given that Question
	 * 
	 * @param HashTable of Question data to update
	 * @param id the id to lookup if Question exists
	 * @return HTTP Code 204 if successfully found and updated or
	 * 			HTTP Code 404 if id is not found
	 * 
	 * @see 
	 * @see com.cogent.service.QuestionService#update(Question)
	 * @since 1.0
	 */
	@PutMapping(value = {"/questions/{id}"})
	public ResponseEntity<String> edit(@RequestBody QuestionDTO obj, @PathVariable("id") Long id) {
		
		if (obj.getUserVoter() != null) {
			questionService.vote(obj, id);
		} 
		System.out.println(obj + "Edit Test");
		return questionService.update(obj, id);
	}
	
	/**
	 * Function to DELETE a single question given their ID
	 * 
	 * @param id the Question ID to look up and delete 
	 * @return HTTP Code 204 if successfully found and deleted or
	 * 			HTTP Code 404 if id is not found
	 * 
	 * @see com.cogent.service.questionService#delete(Integer)
	 * @since 1.0
	 */
	@DeleteMapping(value = {"/questions/{id}"})
	public ResponseEntity<String> delete(@PathVariable("id") Long id) {
		return questionService.delete(id);
	}
}
