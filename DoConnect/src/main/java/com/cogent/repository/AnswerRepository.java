package com.cogent.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cogent.entity.Answer;
import com.cogent.entity.Question;
public interface AnswerRepository extends JpaRepository<Answer, Long> {
	 /** Query all Answers by Question */
	 List<Answer> findByQuestion(Question question);
	 
	 /** Query all approved Answers*/
	 List<Answer> findByQuestionAndApprovedTrue(Question question);
	 
	 /** Query all not approved Answers */
	 List<Answer> findByQuestionAndApprovedFalse(Question question);
}