package com.cogent.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Bean Class to hold individual messages 
 * 
 * @author michaelmiranda
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="messages")
public class Message {
	/** Unique ID for table lookup */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/** When the message was sent */
	private String datetime;
	
	/** The text on the message */
	private String messageText;
	
	/** Who sent the message */
	private String fromUser;
	
	/** chat object for whom these messages are related to */
	@ManyToOne
	private Chat chat;
}
