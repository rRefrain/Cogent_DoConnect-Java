package com.cogent.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO Class used to interact with User
 * 
 * @author michaelmiranda
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	/** User's name */
	private String name;
	
	/** Password for login */
	private String password;
	
	/** User email credentials */
	private String email;
}
