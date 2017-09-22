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
 * @author leoanbarasanm
 *
 */
@Entity
@Table(name="tokens")
public class Token  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long tokenid;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "symbol")
	private String symbol;
	
	@Column(name = "crowdsalepercentage")
	private Integer crowdsalepercentage;
	
	@Column(name = "initialsupply") 
	private Long initialsupply;
	
	@Column(name = "tokenprice") 
	private Integer tokenprice;
	
	@Column(name="starttime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date starttime;
	
	@Column(name="endtime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endtime;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userid")
	private User user;
	
	@Column(name = "address")
	private String address;
	
	@Column(name="createdon")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdon;

	/**
	 * @return the tokenid
	 */
	public Long getTokenid() {
		return tokenid;
	}

	/**
	 * @param tokenid the tokenid to set
	 */
	public void setTokenid(Long tokenid) {
		this.tokenid = tokenid;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * @param symbol the symbol to set
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * @return the crowdsalepercentage
	 */
	public Integer getCrowdsalepercentage() {
		return crowdsalepercentage;
	}

	/**
	 * @param crowdsalepercentage the crowdsalepercentage to set
	 */
	public void setCrowdsalepercentage(Integer crowdsalepercentage) {
		this.crowdsalepercentage = crowdsalepercentage;
	}

	/**
	 * @return the initialsupply
	 */
	public Long getInitialsupply() {
		return initialsupply;
	}

	/**
	 * @param initialsupply the initialsupply to set
	 */
	public void setInitialsupply(Long initialsupply) {
		this.initialsupply = initialsupply;
	}

	/**
	 * @return the tokenprice
	 */
	public Integer getTokenprice() {
		return tokenprice;
	}

	/**
	 * @param tokenprice the tokenprice to set
	 */
	public void setTokenprice(Integer tokenprice) {
		this.tokenprice = tokenprice;
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
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the createdon
	 */
	public Date getCreatedon() {
		return createdon;
	}

	/**
	 * @return the starttime
	 */
	public Date getStarttime() {
		return starttime;
	}

	/**
	 * @param starttime the starttime to set
	 */
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	/**
	 * @return the endtime
	 */
	public Date getEndtime() {
		return endtime;
	}

	/**
	 * @param endtime the endtime to set
	 */
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	/**
	 * @param createdon the createdon to set
	 */
	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Token [tokenid=" + tokenid + ", name=" + name + ", symbol=" + symbol + ", crowdsalepercentage="
				+ crowdsalepercentage + ", initialsupply=" + initialsupply + ", tokenprice=" + tokenprice
				+ ", starttime=" + starttime + ", endtime=" + endtime + ", user=" + user + ", address=" + address
				+ ", createdon=" + createdon + "]";
	}
	
}
