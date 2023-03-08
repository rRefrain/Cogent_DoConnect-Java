package com.cogent.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO Class used to update Question
 * 
 * @author michaelmiranda
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {
	
	/** 1 = upvote, 0 = delete if there -1 = downvote for the Question */
	private int vote;
	
	/** User who voted for the Question */
	private String userVoter;
	
	/** Text for the Question */
	private String descriptionQuestion;
	
	/** Source for a Question Image */
	private String imageSrc;
	
	/** Date Time for Question */
	private String datetime;
	
	/** Question Status */
	private String status;
	
	/** Question topic  */
	private String topic;
	
	/** Question Title header */
	private String title;
	
	/** Admin's name string connected to question */
	private String approvedBy;
}
