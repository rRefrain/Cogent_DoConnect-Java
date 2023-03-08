package com.cogent.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cogent.entity.User;


/**
 * JPA Repository linked to User Entities
 * 
 * @author michaelmiranda
 * @since 1.0
 */
public interface UserRepository extends JpaRepository<User, Long> {
	/** Query the first found User by UserName */
	 Optional<User> findByUserName(String username);
    
    /** Query all Users by UserType*/
  //  List<User> findByUserTypeEquals(String UserType);
    
    Boolean existsByUserName(String username);

    Boolean existsByEmail(String email);
    
}