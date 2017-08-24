/**
 * 
 */
package com.aequalis.ico.main;

import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;

import com.aequalis.ico.blockchainapi.WebAPICall;

/**
 * @author anand
 *
 */
public class SchedulerController {
	
	@Scheduled(fixedDelay = 3600000)
    public void demoServiceMethod()
    {
		System.out.println("Triggered Alert at :: "+ new Date());
		WebAPICall.setProperties();
    }
}
