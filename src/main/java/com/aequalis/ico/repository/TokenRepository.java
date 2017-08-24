/**
 * 
 */
package com.aequalis.ico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aequalis.ico.model.Token;
import com.aequalis.ico.model.User;
import java.util.List;
import java.lang.String;
import java.lang.Long;

/**
 * @author anand
 *
 */
public interface TokenRepository extends JpaRepository<Token, Long>  {
	Token findByTokenid(Long tokenid);
	List<Token> findByName(String name);
	List<Token> findBySymbol(String symbol);
	List<Token> findByUser(User user);
	Token findByAddress(String address);
	Token findByNameAndSymbol(String name, String symbol);
}
