package com.cogent.service;

import java.net.URI;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cogent.entity.Question;
import com.cogent.entity.QuestionVote;
import com.cogent.exception.InputChecker;
import com.cogent.mapper.QuestionMapper;
import com.cogent.repository.QuestionVoteRepository;

/**
 * Service to connect API QuestionVote-related 
 * end points to QuestionVoteRepository
 * @author michaelmiranda
 * @since 1.0
 */
@Service
public class QuestionVoteService {
	/** Mapper to transcribe DTO to QuesitonVote entity */
	@Autowired
	private QuestionMapper mapper;
	
	@Autowired
	private QuestionVoteRepository questionVoteRepository;

	/**
	 * Service Method to request saving a 
	 * QuesitonVote Entity given
	 * through the JPARepository 
	 * 
	 * @param quesitonVote the quesitonVote to save in the database
	 * @return ResponseEntity with HTTP 201 response with URI location encoded
	 * 
	 * @see org.springframework.data.jpa.repository.CrudRepository#count()
	 * @see org.springframework.data.jpa.repository.CrudRepository#save(S)
	 * @see org.springframework.http.ResponseEntity#created(URI)
	 * @see org.springframework.http.ResponseEntity.HeadersBuilder#build()
	 * @since 1.0
	 */
	public ResponseEntity<String> saveQuestionVote(QuestionVote quesitonVote) {
		QuestionVote quesitonVoteSaved = questionVoteRepository.save(quesitonVote);

		
		return ResponseEntity.ok().build();
	}
	
	/**
	 * Service Method to return a single
	 * QuesitonVote Entity via id given 
	 * through the JPARepository 
	 * 
	 * @param id the unique id to search for
	 * @return Optional Object with a QuesitonVote Entity if found or Null
	 * 
	 * @see org.springframework.data.jpa.repository.CrudRepository#findById(Id)
	 * @since 1.0
	 */
	public Optional<QuestionVote> get(Long id){
		return questionVoteRepository.findById(id);
	}
	
	/**
	 * Service Method to return a single
	 * QuesitonVote Entity via id given 
	 * through the JPARepository 
	 * 
	 * @param id the unique id to search for
	 * @return Optional Object with a QuesitonVote Entity if found or Null
	 * 
	 * @see com.cogent.repository.QuesitonVoteRepository#findByEntityAndVoter(Question, String)
	 * @since 1.0
	 */
	public Optional<QuestionVote> getByQuestionUserName(Question entity, String voter){
		return questionVoteRepository.findByEntityAndVoter(entity, voter);
	}

	/**
	 * Service Method to request
	 * all QuesitonVoteVotes Entities 
	 * through the JPARepository 
	 * 
	 * @return list of QuesitonVoteVotes entities
	 * 
	 * @see org.springframework.data.jpa.repository.JpaRepository#findAll()
	 * @since 1.0
	 */
	public List<QuestionVote> getAll(){
		return (List<QuestionVote>) questionVoteRepository.findAll();
	}
	
	/**
	 * Service Method to request
	 * all QuesitonVote Entities filtered by Question
	 * through the JPARepository 
	 * 
	 * @param Question the Question to filter by
	 * @return list of QuesitonVote entities
	 * 
	 * @see com.cogent.repository.QuesitonVoteRepository#findByTopicEquals(String)
	 * @since 1.0
	 */
	public List<QuestionVote> getAllByEntity(Question entity){
		return questionVoteRepository.findAllByEntity(entity);
				
	}
	
	/**
	 * Service Method to request update a 
	 * QuesitonVote Entity, given an existing ID
	 * through the JPARepository 
	 * 
	 * @param quesitonVote the quesitonVote to save in the database
	 * @param id the unique id to search for
	 * @return ResponseEntity with HTTP 204 response on success 
	 * 			HTTP 404 if id not found,
	 * 			HTTP 406 if QuesitonVoteDTO is null
	 * 
	 * @see org.springframework.data.repository.CrudRepository.findById(Integer id)
	 * @see com.cogent.exception.InputChecker#checkOptionalIsEmpty(Optional)
	 * @see com.cogent.exception.InputChecker#checkObjectIsNull(Object)
	 * @see com.cogent.mapper.QuesitonVoteMapper#updateQuesitonVote(QuesitonVoteDTO, QuesitonVote)
	 * @see org.springframework.http.ResponseEntity.HeadersBuilder#build()
	 * @see org.springframework.http.ResponseEntity.noContent()
	 * @see org.springframework.data.jpa.repository.CrudRepository#save(S)
	 * @since 1.0
	 */
	public ResponseEntity<String> update(QuestionVote obj, Long id) {
		Optional<QuestionVote> quesitonVoteOptional = questionVoteRepository.findById(id);

		InputChecker.checkOptionalIsEmpty(quesitonVoteOptional);
		InputChecker.checkObjectIsNull(obj);
	
		QuestionVote quesitonVoteToUpdate = quesitonVoteOptional.get();
		mapper.updateQuestionVote(obj, quesitonVoteToUpdate);
		questionVoteRepository.save(quesitonVoteToUpdate);
		

		return ResponseEntity.noContent().build();		
	}
	
	/**
	 * Service Method to request deletion of a 
	 * QuesitonVote Entity
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
		Optional<QuestionVote> quesitonVoteOptional = questionVoteRepository.findById(id);
		
		InputChecker.checkOptionalIsEmpty(quesitonVoteOptional);
		
		questionVoteRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	/**
	 * Service Method to request deletion of a 
	 * QuesitonVote of all QuesitonVote related to a Question
	 * through the JPARepository 
	 * 
	 * @param id the unique id to search for
	 * @return ResponseEntity with HTTP 204 response on success 
	 * 
	 * @see org.springframework.data.repository.CrudRepository.deleteByEntity(Question)
	 * @see org.springframework.http.ResponseEntity.HeadersBuilder#build()
	 * @see org.springframework.http.ResponseEntity.noContent()
	 * @see org.springframework.data.repository.CrudRepository.deleteById(Integer)
	 * @since 1.0
	 */
	public ResponseEntity<String> deleteByEntity(Question entity) {
		questionVoteRepository.deleteByEntity(entity);
		return ResponseEntity.noContent().build();
	}
}
