/**
 * 
 */
package com.aequalis.ico.service;

/**
 * @author leoanbarasanm
 *
 */
public interface EmailService {
	public boolean sendRegistrationMessage(String email, String userName, String password, String fullname, String activeLink);
}
