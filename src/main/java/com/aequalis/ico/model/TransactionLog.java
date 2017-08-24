/**
 * 
 */
package com.aequalis.ico.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author anand
 *
 */
@Entity
@Table(name="transactions")
public class TransactionLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long transactionid;

	@Column(name = "referencenumber")
	private String referencenumber;
	
	@Column(name = "fromaddress")
	private String fromaddress;
	
	@Column(name = "toaddress")
	private String toaddress;
	
	@Column(name = "amount")
	private String amount;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tokenid")
	private Token token;
	
	@Column(name="transactiondate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactiondate;
	
	@Column(name = "status")
	private Boolean status;
	
	@Column(name = "type")
	private String type;

	/**
	 * @return the transactionid
	 */
	public Long getTransactionid() {
		return transactionid;
	}

	/**
	 * @param transactionid the transactionid to set
	 */
	public void setTransactionid(Long transactionid) {
		this.transactionid = transactionid;
	}

	/**
	 * @return the referencenumber
	 */
	public String getReferencenumber() {
		return referencenumber;
	}

	/**
	 * @param referencenumber the referencenumber to set
	 */
	public void setReferencenumber(String referencenumber) {
		this.referencenumber = referencenumber;
	}

	/**
	 * @return the fromaddress
	 */
	public String getFromaddress() {
		return fromaddress;
	}

	/**
	 * @param fromaddress the fromaddress to set
	 */
	public void setFromaddress(String fromaddress) {
		this.fromaddress = fromaddress;
	}

	/**
	 * @return the toaddress
	 */
	public String getToaddress() {
		return toaddress;
	}

	/**
	 * @param toaddress the toaddress to set
	 */
	public void setToaddress(String toaddress) {
		this.toaddress = toaddress;
	}

	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * @return the transactiondate
	 */
	public Date getTransactiondate() {
		return transactiondate;
	}

	/**
	 * @param transactiondate the transactiondate to set
	 */
	public void setTransactiondate(Date transactiondate) {
		this.transactiondate = transactiondate;
	}

	/**
	 * @return the status
	 */
	public Boolean getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Boolean status) {
		this.status = status;
	}

	/**
	 * @return the token
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(Token token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "TransactionLog [transactionid=" + transactionid + ", referencenumber=" + referencenumber
				+ ", fromaddress=" + fromaddress + ", toaddress=" + toaddress + ", amount=" + amount + ", token="
				+ token + ", transactiondate=" + transactiondate + ", status=" + status + ", type=" + type + "]";
	}
	
}
