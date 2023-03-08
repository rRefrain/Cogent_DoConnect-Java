package com.cogent.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cogent.entity.Answer;
import com.cogent.entity.AnswerDTO;
import com.cogent.entity.Question;
import com.cogent.service.AnswerService;
import com.cogent.service.QuestionService;

/**
 * Controller to deal with answer-related entities
 * 
 * @author michaelmiranda
 * @since 1.0
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
public class AnswerController {
	@Autowired
	private AnswerService answerService;
	
	@Autowired
	private QuestionService questionService;
	
	/**
	 * Function to GET all answers or filtered answers based 
	 * off JSON object values
	 * 
	 * @param obj HashTable Object to filter out results
	 * @return HTTP Code 200 and a List of all (filtered) Answer Objects on success
	 * 
	 * @see com.cogent.service.AnswerService#getAll()
	 * @see com.cogent.service.AnswerService#getAllByQuestion(Question)
	 * @see com.cogent.service.AnswerService#getAllByApproved(String)
	 * @since 1.0
	 */
	@GetMapping(value= {"/answers"})
	public List<Answer> getAll(@RequestParam(required=false) String id) {
		if(id != null) {	
			Question question = questionService.get(Long.parseLong(id)).get();
			System.out.println("ID != null");
			return answerService.getAllByQuestion(question);	
		}
			
		System.out.println("ID == null");
		return answerService.getAll();
	}
		
	/**
	 * Function to GET a answer given their id
	 * 
	 * @param id the answer id to look up
	 * @return HTTP Code 200 and the Answer object on success
	 * @throws NoSuchElementException when answerName cannot be found
	 * 
	 * @see com.cogent.service.AnswerService#get(Integer)
	 * @since 1.0
	 */
	@GetMapping(value = {"/answers/{id}"})
	public Answer get(@PathVariable("id") Long id) 
			throws NoSuchElementException {
		Optional<Answer> i = answerService.get(id);
		return i.get();
	}
	
	/**
	 * Function to ADD a Answer to the date base
	 * 
	 * @param Answer to add
	 * @return HTTP Code 201 on successful Creation
	 * 
	 * @see com.cogent.service.AnswerService#saveAnswer(Answer)
	 * @since 1.0
	 */
	//@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@PostMapping(value = {"/answers"})
	public ResponseEntity<String> add(@RequestBody Answer answer) {
		return answerService.saveAnswer(answer);
	}
	
	/**
	 * Function to UPDATE a Answer given that Answer
	 * 
	 * @param HashTable of Answer data to update
	 * @param id the id to lookup if Answer exists
	 * @return HTTP Code 204 if successfully found and updated or
	 * 			HTTP Code 404 if id is not found
	 * 
	 * @see 
	 * @see com.cogent.service.AnswerService#update(Answer)
	 * @since 1.0
	 */
	//@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@PutMapping(value = {"/answers/{id}"})
	public ResponseEntity<String> edit(@RequestBody AnswerDTO obj, @PathVariable("id") Long id) {
		if (obj.getUserVoter() != null) {
			answerService.vote(obj, id);
		} 
		return answerService.update(obj, id);
	}
	
	/**
	 * Function to DELETE a single answer given their ID
	 * 
	 * @param id the Answer ID to look up and delete 
	 * @return HTTP Code 204 if successfully found and deleted or
	 * 			HTTP Code 404 if id is not found
	 * 
	 * @see com.cogent.service.answerService#delete(Integer)
	 * @since 1.0
	 */
	//@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@DeleteMapping(value = {"/answers/{id}"})
	public ResponseEntity<String> delete(@PathVariable("id") Long id) {
		return answerService.delete(id);
	}
}
