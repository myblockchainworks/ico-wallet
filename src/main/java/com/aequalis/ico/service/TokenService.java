/**
 * 
 */
package com.aequalis.ico.service;

import java.util.List;

import com.aequalis.ico.model.Token;
import com.aequalis.ico.model.User;

/**
 * @author leoanbarasanm
 *
 */
public interface TokenService {
	
	public void addToken(Token token);
	
	public Token findByTokenid(Long tokenid);
	
	public List<Token> findByName(String name);
	public List<Token> findBySymbol(String symbol);
	public List<Token> findByUser(User user);
	
	public Token findByAddress(String address);
	
	public Token findByNameAndSymbol(String name, String symbol);
	
	public List<Token> findAll();
}
