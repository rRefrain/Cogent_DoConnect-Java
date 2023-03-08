package com.cogent.entity;


import java.util.List;

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
 * Bean Class to hold Chat Information
 * 
 * @author michaelmiranda
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="chats")
public class Chat {
	/** Unique ID for table lookup */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/** the first User to share messages */
	private String userA;
	
	/** the second User to share messages */
	private String userB;
	
	/** List of UserNames pertaining who has voted on this Question */
	@OneToMany(mappedBy = "chat", fetch=FetchType.EAGER)
	@EqualsAndHashCode.Exclude
	@JsonIgnore
	private List<Message> messages;
	
}
