package com.cogent.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Bean Class to hold Answer Information
 * 
 * @author michaelmiranda
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="answers")
public class Answer {
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
	private List<AnswerVote> votes;
	
	/** Text for the answer */
	private String descriptionAnswer;
	
	/** Source for Answer */
	private String imgSrc;
	
	/** Answer status whether it is correct or not */
	private boolean approved;
	
	/** Date time for Answer */
	private String datetime;
	
	/** Question to linked to this answer */
	@ManyToOne
	private Question question;
	
	/** username string connected to Question */
	private String createdBy;
	
	/** Admin's username string connected to Question */
	private String approvedBy;
}
