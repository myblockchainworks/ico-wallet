/**
 * 
 */
package com.aequalis.ico.service;

import java.util.List;

import com.aequalis.ico.model.TransactionLog;


/**
 * @author anand
 *
 */
public interface TransactionLogService {
	
	public void addTransactionLog(TransactionLog transactionLog);
	
	public List<TransactionLog> findMyTransactions(String useraddress);
}
