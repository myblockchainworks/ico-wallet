/**
 * 
 */
package com.aequalis.ico.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.aequalis.ico.model.Token;
import com.aequalis.ico.model.User;
import com.aequalis.ico.model.UserToken;
import com.aequalis.ico.repository.UserTokenRepository;
import com.aequalis.ico.service.UserTokenService;

/**
 * @author leoanbarasanm
 *
 */
@Service
@Qualifier("userTokenService")
@Transactional
public class UserTokenServiceImpl implements UserTokenService {

	@Autowired
	UserTokenRepository userTokenRepository;
	
	@Override
	public void addUserToken(UserToken userToken) {
		userTokenRepository.saveAndFlush(userToken);
	}

	@Override
	public UserToken findByUserAndToken(User user, Token token) {
		return userTokenRepository.findByUserAndToken(user, token);
	}

	@Override
	public List<UserToken> findByUser(User user) {
		return userTokenRepository.findByUser(user);
	}

	@Override
	public void removeUserToken(UserToken userToken) {
		userTokenRepository.delete(userToken);
	}

}
