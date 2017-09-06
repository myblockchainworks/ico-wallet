/**
 * 
 */
package com.aequalis.ico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aequalis.ico.model.User;

/**
 * @author anand
 *
 */
public interface UserRepository extends JpaRepository<User, Long>  {
	
	User findByUsernameAndPassword(String username, String password);
	
	User findByUserid(Long userid);
	
	User findByUsername(String username);
	
	User findByBcaddress(String bcaddress);
	
	User findByVerificationcode(String verificationcode);
}
