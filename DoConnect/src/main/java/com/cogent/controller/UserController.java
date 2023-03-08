package com.cogent.controller;

import java.util.Hashtable;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cogent.entity.User;
import com.cogent.entity.UserDTO;
import com.cogent.login.SignupRequest;
import com.cogent.service.UserService;

/**
 * Controller to deal with user-related entities
 * 
 * @author michaelmiranda
 * @since 1.0
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthController authController;
	
	/**
	 * Function to display a home message
	 * 
	 * @return a message to display
	 * @since 1.0
	 */
    @GetMapping(value = {"/"})
    public String welcome() {
        return "Welcome to DoConnect!!";
    }
    /*
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	boolean isAdmin = authentication.getAuthorities().stream()
	          .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
	//if(userToUpdate.getRoles().)*/
	
    //TODO: filter
	/**
	 * Function to GET all users or filtered users based 
	 * off JSON object values
	 * 
	 * @param obj HashTable Object to filter out results
	 * @return HTTP Code 200 and a List of all (filtered) User Objects on success
	 * 
	 * @see com.cogent.service.UserService#getAll()
	 * @see com.cogent.service.UserService#getAllByType(String)
	 * @since 1.0
	 */
	@GetMapping(value = {"/users"})
	public List<User> getAll(@RequestBody(required=false) Hashtable<String, String> obj) {
		
		if(obj != null && obj.containsKey("type")) {
			//return userService.getAllByType(obj.get("type").toString());
		}
		
		return userService.getAll();
	}
		
	/**
	 * Function to GET a user given their id
	 * 
	 * @param id the user id to look up
	 * @return HTTP Code 200 and the User object on success
	 * @throws NoSuchElementException when userName cannot be found
	 * 
	 * @see com.cogent.service.UserService#get(Integer)
	 * @since 1.0
	 */
	@GetMapping(value = {"/users/{id}"})
	public User get(@PathVariable("id") Long id) 
			throws NoSuchElementException {
		Optional<User> i = userService.get(id);
		return i.get();
	}
	
	/**
	 * Function to GET a user given their username
	 * 
	 * @param username the user username to look up
	 * @return HTTP Code 200 and the User object on success 
	 * @throws NoSuchElementException when userName cannot be found
	 * 
	 * @see com.cogent.service.UserService#get(String)
	 * @since 1.0
	 */
	@GetMapping(value = {"/username/{username}"})
	public User get(@PathVariable("username") String userName) 
			throws NoSuchElementException {
		User user = userService.get(userName);
		if (user == null) {
			throw new NoSuchElementException();
		}
		return user;
	}
	
	//TODO: Admin can create mods, admins and users
	/**
	 * Function to ADD a User to the date base
	 * 
	 * @param User to add
	 * @return HTTP Code 201 on successful Creation
	 * 
	 * @see com.cogent.service.UserService#saveUser(User)
	 * @since 1.0
	 */
    @PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = {"/users"})
	public ResponseEntity<?> add(@Valid @RequestBody SignupRequest signUpRequest) {
		return authController.registerUser(signUpRequest);
	}
    
    //TODO: add @PatchMapping(value = {"/users"}) for roles, admin-only
    //ToUpdate a User
    
	/**
	 * Function to UPDATE a User given that User
	 * 
	 * @param HashTable of User data to update
	 * @param id the id to lookup if User exists
	 * @return HTTP Code 204 if successfully found and updated or
	 * 			HTTP Code 404 if id is not found
	 * 
	 * @see com.cogent.service.UserService#update(User)
	 * @since 1.0
	 */
    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
	@PutMapping(value = {"/users/{id}"})
	public ResponseEntity<String> edit(@RequestBody UserDTO obj, @PathVariable("id") Long id) {
		return userService.update(obj, id);
	}
	
	/**
	 * Function to DELETE a single user given their ID
	 * 
	 * @param id the User ID to look up and delete 
	 * @return HTTP Code 204 if successfully found and deleted or
	 * 			HTTP Code 404 if id is not found
	 * 
	 * @see com.cogent.service.userService#delete(Integer)
	 * @since 1.0
	 */
    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
	@DeleteMapping(value = {"/users/{id}"})
	public ResponseEntity<String> delete(@PathVariable("id") Long id) {
		return userService.delete(id);
	}
}
