package com.cogent.service;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cogent.entity.Answer;
import com.cogent.entity.AnswerVote;
import com.cogent.exception.InputChecker;
import com.cogent.mapper.AnswerMapper;
import com.cogent.repository.AnswerVoteRepository;

/**
 * Service to connect API AnswerVote-related 
 * end points to AnswerVoteRepository
 * @author michaelmiranda
 * @since 1.0
 */
@Service
public class AnswerVoteService {
	/** Mapper to transcribe DTO to QuesitonVote entity */
	@Autowired
	private AnswerMapper mapper;
	
	@Autowired
	private AnswerVoteRepository answerVoteRepository;

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
	public ResponseEntity<String> saveAnswerVote(AnswerVote quesitonVote) {
		AnswerVote quesitonVoteSaved = answerVoteRepository.save(quesitonVote);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(quesitonVoteSaved.getId()).toUri();
		
		return ResponseEntity.created(location).build();
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
	public Optional<AnswerVote> get(Long id){
		return answerVoteRepository.findById(id);
	}
	
	/**
	 * Service Method to return a single
	 * QuesitonVote Entity via id given 
	 * through the JPARepository 
	 * 
	 * @param id the unique id to search for
	 * @return Optional Object with a QuesitonVote Entity if found or Null
	 * 
	 * @see com.cogent.repository.QuesitonVoteRepository#findByEntityAndVoter(Answer, String)
	 * @since 1.0
	 */
	public Optional<AnswerVote> getByAnswerUserName(Answer entity, String voter){
		return answerVoteRepository.findByEntityAndVoter(entity, voter);
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
	public List<AnswerVote> getAll(){
		return (List<AnswerVote>) answerVoteRepository.findAll();
	}
	
	/**
	 * Service Method to request
	 * all QuesitonVote Entities filtered by Answer
	 * through the JPARepository 
	 * 
	 * @param Answer the Answer to filter by
	 * @return list of QuesitonVote entities
	 * 
	 * @see com.cogent.repository.QuesitonVoteRepository#findByTopicEquals(String)
	 * @since 1.0
	 */
	public List<AnswerVote> getAllByEntity(Answer entity){
		return answerVoteRepository.findAllByEntity(entity);
				
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
	public ResponseEntity<String> update(AnswerVote obj, Long id) {
		Optional<AnswerVote> quesitonVoteOptional = answerVoteRepository.findById(id);

		InputChecker.checkOptionalIsEmpty(quesitonVoteOptional);
		InputChecker.checkObjectIsNull(obj);
	
		AnswerVote quesitonVoteToUpdate = quesitonVoteOptional.get();
		mapper.updateAnswerVote(obj, quesitonVoteToUpdate);
		answerVoteRepository.save(quesitonVoteToUpdate);
		

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
		Optional<AnswerVote> quesitonVoteOptional = answerVoteRepository.findById(id);
		
		InputChecker.checkOptionalIsEmpty(quesitonVoteOptional);
		
		answerVoteRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	/**
	 * Service Method to request deletion of a 
	 * QuesitonVote of all QuesitonVote related to a Answer
	 * through the JPARepository 
	 * 
	 * @param id the unique id to search for
	 * @return ResponseEntity with HTTP 204 response on success 
	 * 
	 * @see org.springframework.data.repository.CrudRepository.deleteByEntity(Answer)
	 * @see org.springframework.http.ResponseEntity.HeadersBuilder#build()
	 * @see org.springframework.http.ResponseEntity.noContent()
	 * @see org.springframework.data.repository.CrudRepository.deleteById(Integer)
	 * @since 1.0
	 */
	public ResponseEntity<String> deleteByEntity(Answer entity) {
		answerVoteRepository.deleteByEntity(entity);
		return ResponseEntity.noContent().build();
	}
}
