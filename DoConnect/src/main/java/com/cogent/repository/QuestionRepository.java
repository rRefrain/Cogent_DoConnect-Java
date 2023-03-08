package com.cogent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cogent.entity.Question;

/**
 * JPA Repository linked to Question Entities
 * 
 * @author michaelmiranda
 * @since 1.0
 */
public interface QuestionRepository extends JpaRepository<Question, Long> {
	 /** Query all Questions by Topic */
	 List<Question> findByTopicEquals(String Topic);
	 
	 /** Query all Questions by Status */
	 List<Question> findByStatusEquals(String Status);
}