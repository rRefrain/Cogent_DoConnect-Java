package com.cogent.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO Class used to update Answer
 * 
 * @author michaelmiranda
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDTO {
	/** 1 = upvote, 0 = delete if there -1 = downvote for the Answer */
	private int vote;
	
	/** User who voted for the Answer */
	private String userVoter;
	
	/** Text for the Answer */
	private String descriptionAnswer;
	
	/** Source for a Answer Image */
	private String imgSrc;
	
	/** Date Time for Answer */
	private String datetime;
	
	/** Answer Status */
	private boolean approved;
	
	/** Answer topic  */
	private String topic;
	
	/** Answer Title header */
	private String title;
	
	/** Admin's name string connected to question */
	private String approvedBy;
}
