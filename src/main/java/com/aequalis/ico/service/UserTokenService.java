/**
 * 
 */
package com.aequalis.ico.service;

import java.util.List;

import com.aequalis.ico.model.Token;
import com.aequalis.ico.model.User;
import com.aequalis.ico.model.UserToken;

/**
 * @author leoanbarasanm
 *
 */
public interface UserTokenService {
	
	public void addUserToken(UserToken userToken);
	
	public void removeUserToken(UserToken userToken);
	
	public UserToken findByUserAndToken(User user, Token token);
	public List<UserToken> findByUser(User user);
}
