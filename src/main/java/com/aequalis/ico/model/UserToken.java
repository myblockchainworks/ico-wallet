/**
 * 
 */
package com.aequalis.ico.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author leoanbarasanm
 *
 */
@Entity
@Table(name="usertokens")
public class UserToken implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long usertokenid;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userid")
	private User user;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tokenid")
	private Token token;
	
	@Transient
	private String balance;
	
	@Transient
	private String fundRaised;
	
	@Transient
	private boolean showBuy;
	
	@Transient
	private boolean active;
	
	@Transient
	private boolean crowdsaleActive;

	/**
	 * @return the usertokenid
	 */
	public Long getUsertokenid() {
		return usertokenid;
	}

	/**
	 * @param usertokenid the usertokenid to set
	 */
	public void setUsertokenid(Long usertokenid) {
		this.usertokenid = usertokenid;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the token
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * @return the balance
	 */
	public String getBalance() {
		return balance;
	}

	/**
	 * @param balance the balance to set
	 */
	public void setBalance(String balance) {
		this.balance = balance;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(Token token) {
		this.token = token;
	}

	/**
	 * @return the fundRaised
	 */
	public String getFundRaised() {
		return fundRaised;
	}

	/**
	 * @param fundRaised the fundRaised to set
	 */
	public void setFundRaised(String fundRaised) {
		this.fundRaised = fundRaised;
	}

	/**
	 * @return the showBuy
	 */
	public boolean isShowBuy() {
		return showBuy;
	}

	/**
	 * @param showBuy the showBuy to set
	 */
	public void setShowBuy(boolean showBuy) {
		this.showBuy = showBuy;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}


	/**
	 * @return the crowdsaleActive
	 */
	public boolean isCrowdsaleActive() {
		return crowdsaleActive;
	}

	/**
	 * @param crowdsaleActive the crowdsaleActive to set
	 */
	public void setCrowdsaleActive(boolean crowdsaleActive) {
		this.crowdsaleActive = crowdsaleActive;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserToken [usertokenid=" + usertokenid + ", user=" + user + ", token=" + token + ", balance=" + balance
				+ ", fundRaised=" + fundRaised + ", showBuy=" + showBuy + ", active=" + active + ", crowdsaleActive="
				+ crowdsaleActive + "]";
	}
	
}
