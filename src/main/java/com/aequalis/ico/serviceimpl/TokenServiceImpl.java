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
import com.aequalis.ico.repository.TokenRepository;
import com.aequalis.ico.service.TokenService;

/**
 * @author leoanbarasanm
 *
 */
@Service
@Qualifier("tokenService")
@Transactional
public class TokenServiceImpl implements TokenService {
	
	@Autowired
	TokenRepository tokenRepository;

	@Override
	public void addToken(Token token) {
		tokenRepository.saveAndFlush(token);
	}

	@Override
	public List<Token> findByName(String name) {
		return tokenRepository.findByName(name);
	}

	@Override
	public List<Token> findBySymbol(String symbol) {
		return tokenRepository.findBySymbol(symbol);
	}

	@Override
	public List<Token> findByUser(User user) {
		return tokenRepository.findByUser(user);
	}

	@Override
	public Token findByNameAndSymbol(String name, String symbol) {
		return tokenRepository.findByNameAndSymbol(name, symbol);
	}
	
	@Override
	public List<Token> findAll() {
		return tokenRepository.findAll();
	}

	@Override
	public Token findByAddress(String address) {
		return tokenRepository.findByAddress(address);
	}

	@Override
	public Token findByTokenid(Long tokenid) {
		return tokenRepository.findByTokenid(tokenid);
	}

}
