/**
 * 
 */
package com.aequalis.ico.dto;

/**
 * @author leoanbarasanm
 *
 */
public class UserDTO {
	private String address;
	private String privateKey;
	private String keystore;
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
	 * @return the privateKey
	 */
	public String getPrivateKey() {
		return privateKey;
	}
	/**
	 * @param privateKey the privateKey to set
	 */
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	/**
	 * @return the keystore
	 */
	public String getKeystore() {
		return keystore;
	}
	/**
	 * @param keystore the keystore to set
	 */
	public void setKeystore(String keystore) {
		this.keystore = keystore;
	}
}
