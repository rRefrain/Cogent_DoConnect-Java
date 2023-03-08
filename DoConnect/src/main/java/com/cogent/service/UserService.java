package com.cogent.service;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cogent.entity.User;
import com.cogent.entity.UserDTO;
import com.cogent.exception.InputChecker;
import com.cogent.mapper.UserMapper;
import com.cogent.repository.UserRepository;

/**
 * Service to connect API User-related 
 * end points to UserRepository
 * @author michaelmiranda
 * @since 1.0
 */
@Service
public class UserService{
	/** The JPA User Entity Repository to modify SQL */
	@Autowired
	private UserRepository userRepository;
	
	/** Mapper to transcribe DTO to Question entity */
	@Autowired
	private UserMapper mapper;

	/**
	 * Service Method to request saving a 
	 * User Entity given
	 * through the JPARepository 
	 * 
	 * @param user the user to save in the database
	 * @return ResponseEntity with HTTP 201 response with URI location encoded
	 * 
	 * @see org.springframework.data.jpa.repository.CrudRepository#count()
	 * @see org.springframework.data.jpa.repository.CrudRepository#save(S)
	 * @see org.springframework.http.ResponseEntity#created(URI)
	 * @see org.springframework.http.ResponseEntity.HeadersBuilder#build()
	 * @since 1.0
	 */
	public ResponseEntity<String> saveUser(User user) {
		User userSaved = userRepository.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(userSaved.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	/**
	 * Service Method to return a single
	 * User Entity via id given 
	 * through the JPARepository 
	 * 
	 * @param id the unique id to search for
	 * @return Optional Object with a User Entity if found or Null
	 * 
	 * @see org.springframework.data.jpa.repository.CrudRepository#findById(Id)
	 * @since 1.0
	 */
	public Optional<User> get(Long id){
		return userRepository.findById(id);
	}
	
	/**
	 * Service Method to return a single
	 * User Entity via username given 
	 * through the JPARepository 
	 * 
	 * @param userName the unique username to search for
	 * @return Optional Object with a User Entity if found or Null
	 * 
	 * @see com.cogent.repository.UserRepository#findByUserName(String)
	 * @since 1.0
	 */
	public User get(String userName){
		return userRepository.findByUserName(userName).get();
	}

	/**
	 * Service Method to request
	 * all User Entities 
	 * through the JPARepository 
	 * 
	 * @return list of User entities
	 * 
	 * @see org.springframework.data.jpa.repository.JpaRepository#findAll()
	 * @since 1.0
	 */
	public List<User> getAll(){
		return (List<User>) userRepository.findAll();
	}
	
	/**
	 * Service Method to request
	 * all User Entities filtered by userType
	 * through the JPARepository 
	 * 
	 * @param userType the string to filter by
	 * @return list of User entities
	 * 
	 * @see com.cogent.repository.UserRepository#findByUserTypeEquals(String)
	 * @since 1.0
	 */
	/*public List<User> getAllByType(String userType){
		return (List<User>) userRepository.findByUserTypeEquals(userType);
	}*/
	
	/**
	 * Service Method to request update a 
	 * User Entity, given an existing ID
	 * through the JPARepository 
	 * 
	 * @param user the user to save in the database
	 * @param id the unique id to search for
	 * @return ResponseEntity with HTTP 204 response on success 
	 * 			HTTP 404 if id not found,
	 * 			HTTP 409 if UserDTO is null
	 * 
	 * @see org.springframework.data.repository.CrudRepository.findById(Integer id)
	 * @see com.cogent.exception.InputChecker#checkOptionalIsEmpty(Optional)
	 * @see com.cogent.exception.InputChecker#checkObjectIsNull(Object)
	 * @see com.cogent.entity.UserDTO
	 * @see com.cogent.mapper.UserMapper#updateUserFromDto(UserDTO, User)
	 * @see org.springframework.http.ResponseEntity.HeadersBuilder#build()
	 * @see org.springframework.http.ResponseEntity.noContent()
	 * @see org.springframework.data.jpa.repository.CrudRepository#save(S)
	 * @since 1.0
	 */
	public ResponseEntity<String> update(UserDTO obj, Long id) {
		Optional<User> userOptional = userRepository.findById(id);

		InputChecker.checkOptionalIsEmpty(userOptional);
		InputChecker.checkObjectIsNull(obj);
		User userToUpdate = userOptional.get();

		mapper.updateUserFromDto(obj, userToUpdate);
		userRepository.save(userToUpdate);
		
		return ResponseEntity.noContent().build();		
	}
	
	/**
	 * Service Method to request deletion of a 
	 * User Entity
	 * through the JPARepository 
	 * 
	 * @param id the unique id to search for
	 * @return ResponseEntity with HTTP 204 response on success 
	 * 			or HTTP 404 if id not found
	 * 
	 * @see org.springframework.data.repository.CrudRepository.findById(Integer id)
	 * @see com.cogent.exception.InputChecker#checkOptionalIsEmpty(Optional)
	 * @see org.springframework.http.ResponseEntity.noContent()
	 * @see org.springframework.http.ResponseEntity.HeadersBuilder#build()
	 * @see org.springframework.data.repository.CrudRepository.deleteById(Integer id)
	 * @since 1.0
	 */
	public ResponseEntity<String> delete(Long id) {
		Optional<User> userOptional = userRepository.findById(id);

		InputChecker.checkOptionalIsEmpty(userOptional);
		
		userRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
