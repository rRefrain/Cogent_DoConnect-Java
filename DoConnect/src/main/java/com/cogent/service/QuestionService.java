package com.cogent.service;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cogent.entity.Question;
import com.cogent.entity.QuestionDTO;
import com.cogent.entity.QuestionVote;
import com.cogent.exception.InputChecker;
import com.cogent.mapper.QuestionMapper;
import com.cogent.repository.QuestionRepository;
import com.cogent.util.Time;

/**
 * Service to connect API Question-related 
 * end points to QuestionRepository
 * @author michaelmiranda
 * @since 1.0
 */
@Service
public class QuestionService{
	/** The JPA Question Entity Repository to modify SQL */
	@Autowired
	private QuestionRepository questionRepository;
	
	/** Mapper to transcribe DTO to Question entity */
	@Autowired
	private QuestionMapper mapper;
	
	/** Service to maintain voting */
	@Autowired
	private QuestionVoteService questionVoteService;

	/**
	 * Service Method to request saving a 
	 * Question Entity given
	 * through the JPARepository 
	 * 
	 * @param question the question to save in the database
	 * @return ResponseEntity with HTTP 201 response with URI location encoded
	 * 
	 * @see org.springframework.data.jpa.repository.CrudRepository#count()
	 * @see org.springframework.data.jpa.repository.CrudRepository#save(S)
	 * @see org.springframework.http.ResponseEntity#created(URI)
	 * @see org.springframework.http.ResponseEntity.HeadersBuilder#build()
	 * @since 1.0
	 */
	public ResponseEntity<String> saveQuestion(Question question) {
		question.setDatetime(Time.getTimeNow());
		Question questionSaved = questionRepository.save(question);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(questionSaved.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	/**
	 * Service Method to return a single
	 * Question Entity via id given 
	 * through the JPARepository 
	 * 
	 * @param id the unique id to search for
	 * @return Optional Object with a Question Entity if found or Null
	 * 
	 * @see org.springframework.data.jpa.repository.CrudRepository#findById(Id)
	 * @since 1.0
	 */
	public Optional<Question> get(Long id){
		return questionRepository.findById(id);
	}

	/**
	 * Service Method to request
	 * all Question Entities 
	 * through the JPARepository 
	 * 
	 * @return list of Question entities
	 * 
	 * @see org.springframework.data.jpa.repository.JpaRepository#findAll()
	 * @since 1.0
	 */
	public List<Question> getAll(){
		return (List<Question>) questionRepository.findAll();
	}
	
	/**
	 * Service Method to request
	 * all Question Entities filtered by questionTopic
	 * through the JPARepository 
	 * 
	 * @param questionTopic the string to filter by
	 * @return list of Question entities
	 * 
	 * @see com.cogent.repository.QuestionRepository#findByTopicEquals(String)
	 * @since 1.0
	 */
	public List<Question> getAllByTopic(String questionTopic){
		return (List<Question>) questionRepository.findByTopicEquals(questionTopic);
	}
	
	/**
	 * Service Method to request
	 * all Question Entities filtered by answered Questions
	 * through the JPARepository 
	 * 
	 * @param isAnswered flag to filter answered questions
	 * @return list of Question entities
	 * 
	 * @see com.cogent.repository.QuestionRepository#findByStatusEquals()
	 * @since 1.0
	 */
	public List<Question> getAllByStatus(String status){
		return (List<Question>) questionRepository.findByStatusEquals(status);
	}
	
	/**
	 * Service Method to request update a 
	 * Question Entity, given an existing ID
	 * through the JPARepository 
	 * 
	 * @param question the question to save in the database
	 * @param id the unique id to search for
	 * @return ResponseEntity with HTTP 204 response on success 
	 * 			HTTP 404 if id not found,
	 * 			HTTP 406 if QuestionDTO is null
	 * 
	 * @see org.springframework.data.repository.CrudRepository.findById(Integer id)
	 * @see com.cogent.exception.InputChecker#checkOptionalIsEmpty(Optional)
	 * @see com.cogent.exception.InputChecker#checkObjectIsNull(Object)
	 * @see com.cogent.entity.QuestionDTO
	 * @see com.cogent.mapper.QuestionMapper#updateQuestionFromDto(QuestionDTO, Question)
	 * @see org.springframework.http.ResponseEntity.HeadersBuilder#build()
	 * @see org.springframework.http.ResponseEntity.noContent()
	 * @see org.springframework.data.jpa.repository.CrudRepository#save(S)
	 * @since 1.0
	 */
	public ResponseEntity<String> update(QuestionDTO obj, Long id) {
		Optional<Question> questionOptional = questionRepository.findById(id);
		obj.setDatetime(Time.getTimeNow());
		InputChecker.checkOptionalIsEmpty(questionOptional);
		InputChecker.checkObjectIsNull(obj);
	
		
		Question questionToUpdate = questionOptional.get();
		mapper.updateQuestion(obj, questionToUpdate);
		questionRepository.save(questionToUpdate);
		

		return ResponseEntity.noContent().build();		
	}
	
	/**
	 * Service Method to request update a 
	 * Question Entity votes, given an existing ID
	 * through the JPARepository 
	 * 
	 * @param question the question to save in the database
	 * @param id the unique id to search for
	 * @return ResponseEntity with HTTP 204 response on success 
	 * 			HTTP 404 if id not found,
	 * 			HTTP 406 if QuestionDTO is null,
	 * 			HTTP 400 if user has already voted
	 * 
	 * @see org.springframework.data.repository.CrudRepository.findById(Integer id)
	 * @see com.cogent.exception.InputChecker#checkOptionalIsEmpty(Optional)
	 * @see com.cogent.exception.InputChecker#checkObjectIsNull(Object)
	 * @see com.cogent.entity.QuestionDTO
	 * @see org.springframework.http.ResponseEntity.HeadersBuilder#build()
	 * @see org.springframework.http.ResponseEntity.noContent()
	 * @see org.springframework.data.jpa.repository.CrudRepository#save(S)
	 * @since 1.0
	 */
	public ResponseEntity<String> vote(QuestionDTO obj, Long id) {
		Optional<Question> questionOptional = questionRepository.findById(id);

		InputChecker.checkOptionalIsEmpty(questionOptional);
		InputChecker.checkObjectIsNull(obj);
		
		Question questionToVote = questionOptional.get();
		InputChecker.checkObjectIsNull(questionToVote.getVotes());
		InputChecker.checkObjectIsNull(obj.getUserVoter());
		
		Optional<QuestionVote> questionVoteOptional = 
				questionVoteService.getByQuestionUserName(questionToVote, obj.getUserVoter());
		/* Add if vote if does not exist, change tally */
		if (!questionVoteOptional.isPresent() && obj.getVote() != 0) {
			System.out.println("questionVoteOptional == null");
			
			QuestionVote newQuestionVote = 
					new QuestionVote(null, obj.getUserVoter(), obj.getVote(), questionToVote);
			questionVoteService.saveQuestionVote(newQuestionVote);
			questionToVote.setVoteTally(questionToVote.getVoteTally() + obj.getVote());
		} else {
			QuestionVote questionVoteLog = questionVoteOptional.get();
			/* Delete and change tally if the new vote is 0 */
			if (questionVoteOptional.isPresent() && obj.getVote() == 0) {
				questionToVote.setVoteTally(questionToVote.getVoteTally() - questionVoteLog.getVote());
				questionVoteLog.setVote(0);
				questionToVote.getVotes().remove(questionVoteLog);
			} 
			/* 
			 * If the Voter already voted for this entity 
			 * and their results are changed, 
			 * change the log and the tally
			 */
			else if (questionVoteLog.getVote() != obj.getVote()) {
				questionToVote.setVoteTally(questionToVote.getVoteTally() + (obj.getVote() * 2));
				questionVoteService.update(new QuestionVote(questionVoteLog.getId(), null, obj.getVote(), questionToVote)
						,questionVoteLog.getId());
			}
			/*
			 * If the Voter already voted for this entity 
			 * and their results are the same
			 * do not do anything and return HTTP 400
			 */
			else {
				return ResponseEntity.status(400).build();
				//TODO: Logic Working as intended but sends 204
			}	
			
		}
	
		questionRepository.save(questionToVote);
		return ResponseEntity.noContent().build();		
	}
	
	/**
	 * Service Method to request deletion of a 
	 * Question Entity
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
		//TODO: Deletes corresponding Votes and answers
		Optional<Question> questionOptional = questionRepository.findById(id);
		
		InputChecker.checkOptionalIsEmpty(questionOptional);
		
		questionVoteService.deleteByEntity(questionOptional.get());	
		questionRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	

}
