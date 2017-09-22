/**
 * 
 */
package com.aequalis.ico.blockchainapi;

/**
 * @author anand
 *
 */
public interface WEBAPI {
	
	static String REGISTER_DATA = "{ \"id\":\"1\", \"jsonrpc\":\"2.0\", \"method\": \"personal_newAccount\", \"params\": [\"PARAM1\"] }";
	
	static String UNLOCK_DATA = "{\"id\":\"1\", \"jsonrpc\":\"2.0\", \"method\": \"personal_unlockAccount\", \"params\":  [ \"PARAM1\", \"PARAM2\", 0 ] }";
	
	static String SEND_TRANSACTION = "{\"id\":\"1\", \"jsonrpc\":\"2.0\", \"method\": \"eth_sendTransaction\", \"params\": [{ \"from\": \"PARAM1\", \"to\": \"PARAM2\", \"gas\": \"0x76c0\", \"gasPrice\": \"0x9184e72a000\", \"value\": \"PARAM3\" }] }";
		
	static String GET_BALANCE = "{\"id\":\"1\", \"jsonrpc\":\"2.0\", \"method\": \"eth_getBalance\", \"params\": [\"PARAM1\", \"latest\"] }";
	
	static String CREATEACCOUNT = "createAccount";
	
	static String CREATEACCOUNT_DATA = "{  \"_password\": \"PARAM1\" }";
	
	static String ACCESSACCOUNTUSINGKEYSTORE = "accessAccountUsingKeystore";
	
	static String ACCESSACCOUNTUSINGKEYSTORE_DATA = "{  \"Keystore\": PARAM1, \"_password\": \"PARAM2\" }";
	
	static String ACCESSACCOUNTUSINGPRIVATEKEY = "accessAccountUsingPrivateKey";
	
	static String ACCESSACCOUNTUSINGPRIVATEKEY_DATA = "{  \"_privateKey\": \"PARAM1\" }";
	
	static String MYTOKENBALANCE = "myTokenBalance";
	
	static String MYTOKENBALANCE_DATA = "{  \"_address\": \"PARAM1\", \"_tokenAddress\" : \"PARAM2\" }";
	
	static String TRANSFERTOKEN = "transferToken";
	
	static String TRANSFERTOKEN_DATA = "{  \"_fromaddress\": \"PARAM1\", \"_privatekey\": \"PARAM2\", \"_toaddress\": \"PARAM3\", \"_amount\": PARAM4, \"_tokenAddress\" : \"PARAM5\"}";
	
	static String CREATETOKEN = "createToken";
	
	static String CREATETOKEN_DATA = "{  \"_name\": \"PARAM1\", \"_symbol\": \"PARAM2\", \"_crowdsalePercentage\": PARAM3, \"_initialSupply\": PARAM4, \"_tokenPrice\" : PARAM5, \"_tokenOwner\" : \"PARAM6\", \"_startTime\" : PARAM7, \"_endTime\" : PARAM8}";
	
	static String BUYTOKEN = "buyToken";
	
	static String BUYTOKEN_DATA = "{  \"_fromaddress\": \"PARAM1\", \"_privatekey\": \"PARAM2\", \"_amount\": PARAM3, \"_tokenAddress\" : \"PARAM4\" }";
	
	static String SENDETHER = "sendEther";
	
	static String SENDETHER_DATA = "{  \"_fromaddress\": \"PARAM1\", \"_privatekey\": \"PARAM2\", \"_amount\": PARAM3, \"_toAddress\" : \"PARAM4\" }";
	
	static String FUNDRAISED = "fundRaised";
	
	static String FUNDRAISED_DATA = "{  \"_tokenAddress\": \"PARAM1\" }";
	
	static String ISTOKENACTIVE = "isTokenActive";
	
	static String ISCROWDSALEACTIVE = "isCrowdsaleActive";
	
	static String INCREASETOKENSUPPLY = "increaseTokenSupply";
	
	static String INCREASETOKENSUPPLY_DATA = "{  \"_tokenAddress\": \"PARAM1\", \"_additionalSupply\" : PARAM2 }";
	
	static String FREEZEORUNFREEZETOKEN = "freezeOrUnfreezeToken";
	
	static String FREEZEORUNFREEZETOKEN_DATA = "{  \"_tokenAddress\": \"PARAM1\", \"_active\" : PARAM2 }";
	
	static String PAUSEORRESUMECROWDSALE = "pauseOrResumeCrowdsale";
	
	static String PAUSEORRESUMECROWDSALE_DATA = "{  \"_tokenAddress\": \"PARAM1\", \"_status\" : PARAM2 }";
	
	static String CHANGESTARTTIME = "changeStartTime";
	
	static String CHANGESTARTTIME_DATA = "{  \"_tokenAddress\": \"PARAM1\", \"_startTime\" : PARAM2 }";
	
	static String CHANGEENDTIME = "changeEndTime";
	
	static String CHANGEENDTIME_DATA = "{  \"_tokenAddress\": \"PARAM1\", \"_endTime\" : PARAM2 }";
	
}