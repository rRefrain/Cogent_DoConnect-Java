package com.cogent.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cogent.entity.Answer;
import com.cogent.entity.AnswerVote;

/**
 * JPA Repository linked to AnswerVotes Entities
 * 
 * @author michaelmiranda
 * @since 1.0
 */
public interface AnswerVoteRepository extends JpaRepository<AnswerVote, Long> {
		/** Query a AnswerVote by entity and voter, in practice should be unique */
		Optional<AnswerVote> findByEntityAndVoter(Answer entity, String voter);
		
		/** Query all AnswerVote by a Answer */
		List<AnswerVote> findAllByEntity(Answer entity);
		
		/** Function to delete all QuesitonVote related to a Answer */
		long deleteByEntity(Answer entity);
}