package com.cogent.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cogent.entity.Question;
import com.cogent.entity.QuestionVote;

/**
 * JPA Repository linked to QuestionVotes Entities
 * 
 * @author michaelmiranda
 * @since 1.0
 */
public interface QuestionVoteRepository extends JpaRepository<QuestionVote, Long> {
		/** Query a QuestionVote by entity and voter, in practice should be unique */
		Optional<QuestionVote> findByEntityAndVoter(Question entity, String voter);
		
		/** Query all QuestionVote by a Question */
		List<QuestionVote> findAllByEntity(Question entity);
		
		/** Function to delete all QuesitonVote related to a Question */
		long deleteByEntity(Question entity);
}