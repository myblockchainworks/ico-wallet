/**
 * 
 */
package com.aequalis.ico.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.aequalis.ico.model.TransactionLog;
import com.aequalis.ico.repository.TransactionLogRepository;
import com.aequalis.ico.service.TransactionLogService;

/**
 * @author anand
 *
 */
@Service
@Qualifier("transactionLogService")
@Transactional
public class TransactionLogServiceImpl implements TransactionLogService {

	@Autowired
	TransactionLogRepository transactionLogRepository;
	
	@Override
	public void addTransactionLog(TransactionLog transactionLog) {
		transactionLogRepository.saveAndFlush(transactionLog);
	}

	@Override
	public List<TransactionLog> findMyTransactions(String useraddress) {
		return transactionLogRepository.findMyTransactions(useraddress);
	}

}
