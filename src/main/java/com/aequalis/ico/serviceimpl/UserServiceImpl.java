/**
 * 
 */
package com.aequalis.ico.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.aequalis.ico.model.User;
import com.aequalis.ico.repository.UserRepository;
import com.aequalis.ico.service.UserService;

/**
 * @author anand
 *
 */
@Service
@Qualifier("userService")
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;

	@Override
	public void addUser(User user) {
		userRepository.saveAndFlush(user);
	}

	@Override
	public User loginUser(String username, String password) {
		return userRepository.findByUsernameAndPassword(username, password);
	}

	@Override
	public User findByUserid(Long userid) {
		User user = userRepository.findByUserid(userid);
		user.setKeystore(user.getKeystore().replaceAll("\"", "\\\\"));
		return user;
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}


	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User findByBcaddress(String bcaddress) {
		return userRepository.findByBcaddress(bcaddress);
	}

	@Override
	public User findByVerificationcode(String verificationcode) {
		return userRepository.findByVerificationcode(verificationcode);
	}
}
