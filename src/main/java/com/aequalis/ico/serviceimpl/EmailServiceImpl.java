package com.aequalis.ico.serviceimpl;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.aequalis.ico.service.EmailService;

@Component
public class EmailServiceImpl implements EmailService {
  
    @Autowired
    public JavaMailSender emailSender;
    
    @Autowired
    public VelocityEngine velocityEngine;
 
    public boolean sendRegistrationMessage(String email, String userName, String password, String fullname, String activeLink) {
    	boolean result = false;
    	MimeMessage message = emailSender.createMimeMessage();
        
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(email);
	        helper.setSubject("User Registration Verification");
	        Map<String, Object> prams = new HashMap<String, Object>();
	        prams.put("user", fullname);
	        prams.put("username", userName);
	        prams.put("password", password);
	        prams.put("link", activeLink);
	        
	        String templateLocation = "email/userRegistration.vm";
	        
	        String body = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateLocation, StandardCharsets.UTF_8.name(), prams);
	        
	        helper.setText(body, true);
	             
	        emailSender.send(message);
	        result = true;
		} catch (MessagingException e) {
			e.printStackTrace();
		}
        return result;
    }
}