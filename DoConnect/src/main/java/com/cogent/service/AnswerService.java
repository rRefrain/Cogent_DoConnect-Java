package com.cogent.service;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cogent.entity.Answer;
import com.cogent.entity.AnswerDTO;
import com.cogent.entity.AnswerVote;
import com.cogent.entity.Question;
import com.cogent.exception.InputChecker;
import com.cogent.mapper.AnswerMapper;
import com.cogent.repository.AnswerRepository;
import com.cogent.util.Time;

/**
 * Service to connect API Answer-related 
 * end points to AnswerRepository
 * @author michaelmiranda
 * @since 1.0
 */
@Service
public class AnswerService{
	/** The JPA Answer Entity Repository to modify SQL */
	@Autowired
	private AnswerRepository answerRepository;
	
	/** Mapper to transcribe DTO to Answer entity */
	@Autowired
	private AnswerMapper mapper;
	
	/** Service to maintain voting */
	@Autowired
	private AnswerVoteService answerVoteService;
	
	/** Service to get questions */
	@Autowired
	private QuestionService questionService;

	/**
	 * Service Method to request saving a 
	 * Answer Entity given
	 * through the JPARepository 
	 * 
	 * @param answer the answer to save in the database
	 * @return ResponseEntity with HTTP 201 response with URI location encoded
	 * 
	 * @see org.springframework.data.jpa.repository.CrudRepository#count()
	 * @see org.springframework.data.jpa.repository.CrudRepository#save(S)
	 * @see org.springframework.http.ResponseEntity#created(URI)
	 * @see org.springframework.http.ResponseEntity.HeadersBuilder#build()
	 * @since 1.0
	 */
	public ResponseEntity<String> saveAnswer(Answer answer) {
		answer.setDatetime(Time.getTimeNow());
		answer.setQuestion(questionService.get(answer.getQuestion().getId()).get());
		Answer answerSaved = answerRepository.save(answer);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(answerSaved.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	/**
	 * Service Method to return a single
	 * Answer Entity via id given 
	 * through the JPARepository 
	 * 
	 * @param id the unique id to search for
	 * @return Optional Object with a Answer Entity if found or Null
	 * 
	 * @see org.springframework.data.jpa.repository.CrudRepository#findById(Id)
	 * @since 1.0
	 */
	public Optional<Answer> get(Long id){
		return answerRepository.findById(id);
	}

	/**
	 * Service Method to request
	 * all Answer Entities 
	 * through the JPARepository 
	 * 
	 * @return list of Answer entities
	 * 
	 * @see org.springframework.data.jpa.repository.JpaRepository#findAll()
	 * @since 1.0
	 */
	public List<Answer> getAll(){
		return (List<Answer>) answerRepository.findAll();
	}
	
	/**
	 * Service Method to request
	 * all Answer Entities filtered by answerTopic
	 * through the JPARepository 
	 * 
	 * @param answerTopic the string to filter by
	 * @return list of Answer entities
	 * 
	 * @see com.cogent.repository.AnswerRepository#findByQuestion(Question)
	 * @since 1.0
	 */
	public List<Answer> getAllByQuestion(Question question){
		InputChecker.checkObjectIsNull(question);
		return (List<Answer>) answerRepository.findByQuestion(question);
	}
	
	/**
	 * Service Method to request
	 * all Answer Entities filtered by answered Answers
	 * through the JPARepository 
	 * 
	 * @param isAnswered flag to filter answered answers
	 * @return list of Answer entities
	 * 
	 * @see com.cogent.repository.AnswerRepository#findByApprovedTrue()
	 * @see com.cogent.repository.AnswerRepository#findByApprovedFalse()
	 * @since 1.0
	 */
	public List<Answer> getAllByApproved(Boolean approved, Question question){
		if (approved) {
			return (List<Answer>) answerRepository.findByQuestionAndApprovedTrue(question);
		}
		return (List<Answer>) answerRepository.findByQuestionAndApprovedFalse(question);
	}
	
	/**
	 * Service Method to request update a 
	 * Answer Entity, given an existing ID
	 * through the JPARepository 
	 * 
	 * @param answer the answer to save in the database
	 * @param id the unique id to search for
	 * @return ResponseEntity with HTTP 204 response on success 
	 * 			HTTP 404 if id not found,
	 * 			HTTP 406 if AnswerDTO is null
	 * 
	 * @see org.springframework.data.repository.CrudRepository.findById(Integer id)
	 * @see com.cogent.exception.InputChecker#checkOptionalIsEmpty(Optional)
	 * @see com.cogent.exception.InputChecker#checkObjectIsNull(Object)
	 * @see com.cogent.entity.AnswerDTO
	 * @see com.cogent.mapper.AnswerMapper#updateAnswerFromDto(AnswerDTO, Answer)
	 * @see org.springframework.http.ResponseEntity.HeadersBuilder#build()
	 * @see org.springframework.http.ResponseEntity.noContent()
	 * @see org.springframework.data.jpa.repository.CrudRepository#save(S)
	 * @since 1.0
	 */
	public ResponseEntity<String> update(AnswerDTO obj, Long id) {
		obj.setDatetime(Time.getTimeNow());
		Optional<Answer> answerOptional = answerRepository.findById(id);

		InputChecker.checkOptionalIsEmpty(answerOptional);
		InputChecker.checkObjectIsNull(obj);
	
		
		Answer answerToUpdate = answerOptional.get();
		mapper.updateAnswerFromDto(obj, answerToUpdate);
		answerRepository.save(answerToUpdate);
		

		return ResponseEntity.noContent().build();		
	}
	
	/**
	 * Service Method to request update a 
	 * Answer Entity votes, given an existing ID
	 * through the JPARepository 
	 * 
	 * @param answer the answer to save in the database
	 * @param id the unique id to search for
	 * @return ResponseEntity with HTTP 204 response on success 
	 * 			HTTP 404 if id not found,
	 * 			HTTP 406 if AnswerDTO is null,
	 * 			HTTP 400 if user has already voted
	 * 
	 * @see org.springframework.data.repository.CrudRepository.findById(Integer id)
	 * @see com.cogent.exception.InputChecker#checkOptionalIsEmpty(Optional)
	 * @see com.cogent.exception.InputChecker#checkObjectIsNull(Object)
	 * @see com.cogent.entity.AnswerDTO
	 * @see org.springframework.http.ResponseEntity.HeadersBuilder#build()
	 * @see org.springframework.http.ResponseEntity.noContent()
	 * @see org.springframework.data.jpa.repository.CrudRepository#save(S)
	 * @since 1.0
	 */
	public ResponseEntity<String> vote(AnswerDTO obj, Long id) {
		Optional<Answer> answerOptional = answerRepository.findById(id);

		InputChecker.checkOptionalIsEmpty(answerOptional);
		InputChecker.checkObjectIsNull(obj);
		
		Answer answerToVote = answerOptional.get();
		InputChecker.checkObjectIsNull(answerToVote.getVotes());
		InputChecker.checkObjectIsNull(obj.getUserVoter());
		
		Optional<AnswerVote> answerVoteOptional = 
				answerVoteService.getByAnswerUserName(answerToVote, obj.getUserVoter());
		/* Add if vote if does not exist, change tally */
		if (!answerVoteOptional.isPresent() && obj.getVote() != 0) {
			System.out.println("answerVoteOptional == null");
			
			AnswerVote newAnswerVote = 
					new AnswerVote(null, obj.getUserVoter(), obj.getVote(), answerToVote);
			answerVoteService.saveAnswerVote(newAnswerVote);
			answerToVote.setVoteTally(answerToVote.getVoteTally() + obj.getVote());
		} else {
			AnswerVote answerVoteLog = answerVoteOptional.get();
			/* Delete and change tally if the new vote is 0 */
			if (answerVoteOptional.isPresent() && obj.getVote() == 0) {
				answerToVote.setVoteTally(answerToVote.getVoteTally() - answerVoteLog.getVote());
				answerVoteLog.setVote(0);
				answerToVote.getVotes().remove(answerVoteLog);
			} 
			/* 
			 * If the Voter already voted for this entity 
			 * and their results are changed, 
			 * change the log and the tally
			 */
			else if (answerVoteLog.getVote() != obj.getVote()) {
				answerToVote.setVoteTally(answerToVote.getVoteTally() + (obj.getVote() * 2));
				answerVoteService.update(new AnswerVote(answerVoteLog.getId(), null, obj.getVote(), answerToVote)
						,answerVoteLog.getId());
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
	
		answerRepository.save(answerToVote);
		return ResponseEntity.noContent().build();		
	}
	
	/**
	 * Service Method to request deletion of a 
	 * Answer Entity
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
		Optional<Answer> answerOptional = answerRepository.findById(id);
		
		InputChecker.checkOptionalIsEmpty(answerOptional);
		
		answerVoteService.deleteByEntity(answerOptional.get());	
		answerRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	

}
