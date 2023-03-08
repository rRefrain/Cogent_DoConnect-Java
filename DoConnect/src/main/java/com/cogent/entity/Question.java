package com.cogent.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * Bean Class to hold Question Information
 * 
 * @author michaelmiranda
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper=false)
@Table(name="questions")
public class Question{
	/** Unique ID for table lookup */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/** User Voting system */
	private int voteTally;
	
	/** List of UserNames pertaining who has voted on this Question */
	@OneToMany(mappedBy = "entity", fetch=FetchType.EAGER)
	@EqualsAndHashCode.Exclude
	@JsonIgnore
	private Set<QuestionVote> votes;
	
	/** Text for the Question */
	private String descriptionQuestion;
	
	/** Source for a Question Image */
	private String imageSrc;
	
	/** Date Time for Question */
	private String datetime;
	
	/** Question Status Whether it is Closed, Opened or Denied */
	private String status;
	
	/** Question topic  */
	private String topic;
	
	/** Question Title header */
	private String title;
	
	/** List of Answers pertaining to this Question */
	@OneToMany(mappedBy = "question", fetch=FetchType.EAGER)
	@JsonIgnore
	private List<Answer> answers;
	
	/** username string connected to Question */
	private String createdBy;
	
	/** Admin's username string connected to Question */
	private String approvedBy;

}
