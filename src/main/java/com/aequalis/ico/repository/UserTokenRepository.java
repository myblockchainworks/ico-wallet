/**
 * 
 */
package com.aequalis.ico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aequalis.ico.model.Token;
import com.aequalis.ico.model.User;
import com.aequalis.ico.model.UserToken;

/**
 * @author leoanbarasanm
 *
 */
public interface UserTokenRepository extends JpaRepository<UserToken, Long>{
	UserToken findByUserAndToken(User user, Token token);
	List<UserToken> findByUser(User user);
}
