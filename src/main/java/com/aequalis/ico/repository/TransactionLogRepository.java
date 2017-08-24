/**
 * 
 */
package com.aequalis.ico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aequalis.ico.model.TransactionLog;

/**
 * @author anand
 *
 */
public interface TransactionLogRepository extends JpaRepository<TransactionLog, Long>  {
	
	@Query(value = "SELECT t FROM TransactionLog t WHERE t.fromaddress IN (:useraddress) OR t.toaddress IN (:useraddress)")
	public List<TransactionLog> findMyTransactions(@Param("useraddress") String useraddress);
}
