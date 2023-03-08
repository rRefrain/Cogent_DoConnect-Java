package com.cogent.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="question_vote")
public class QuestionVote {
	/** Unique ID for table lookup */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/** String of Username of who voted */
	private String voter;
	
	/** Flag Whether it was an upvote */
	private int vote;
	
	/** The entity to vote for */
	@ManyToOne
	private Question entity;
	
}
