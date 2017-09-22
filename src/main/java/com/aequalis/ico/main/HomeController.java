/**
 * 
 */
package com.aequalis.ico.main;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.aequalis.ico.blockchainapi.WebAPICall;
import com.aequalis.ico.dto.UserDTO;
import com.aequalis.ico.model.Token;
import com.aequalis.ico.model.TransactionLog;
import com.aequalis.ico.model.User;
import com.aequalis.ico.model.UserToken;
import com.aequalis.ico.service.EmailService;
import com.aequalis.ico.service.TokenService;
import com.aequalis.ico.service.TransactionLogService;
import com.aequalis.ico.service.UserService;
import com.aequalis.ico.service.UserTokenService;

/**
 * @author anand
 *
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	TokenService tokenService;
	
	@Autowired
	TransactionLogService transactionLogService;
	
	@Autowired
	UserTokenService userTokenService;
	
	@Autowired(required=true)
	EmailService emailService;
	
	@Autowired(required = true)
	public HttpServletRequest request;
	
	@Autowired
	public Environment environment;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "index";
	}
	
	public HttpSession getSession() {
		if (request != null) {
			return request.getSession();
		}
		return null;
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String loginScreen(Model model) {
	    return "index";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registerScreen(Model model) {
	    return "register";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutScreen(Model model) {
		HttpSession session = getSession();
		session.removeAttribute("loginUser");
	    return "index";
	}
	
	
	
	private BigDecimal getFunRaised(String tokenAddress) {
		
		BigDecimal wei = new BigDecimal("1000000000000000000");
		BigDecimal fundRaised = new BigDecimal("0");
		String currentBalance = WebAPICall.getFunRaised(tokenAddress);
		
		if (currentBalance != null && !currentBalance.equalsIgnoreCase("0")) {
			fundRaised = new BigDecimal(currentBalance);
			fundRaised = fundRaised.divide(wei);
		}
		
		return fundRaised;
	}
	
	private BigDecimal getMyBalance(String myAddress) {
		
		BigDecimal wei = new BigDecimal("1000000000000000000");
		BigDecimal myBlanace = new BigDecimal("0");
		String currentBalance = WebAPICall.getBalance(myAddress);
		
		if (currentBalance != null) {
			myBlanace = new BigDecimal(new BigInteger(currentBalance.substring(2, currentBalance.length()), 16));
			myBlanace = myBlanace.divide(wei);
		}
		
		return myBlanace;
	}
	
	@RequestMapping(value = "/registerUser", method = RequestMethod.POST)
	public ModelAndView registerUser(Model model, HttpServletRequest httpServletRequest) {
		
		String fullname = httpServletRequest.getParameter("fullname");
		String username = httpServletRequest.getParameter("username");
		String password = httpServletRequest.getParameter("password");
		String confirmPassword = httpServletRequest.getParameter("confirmPassword");
		String contactNumber = httpServletRequest.getParameter("contactNumber");
		String email = httpServletRequest.getParameter("email");
		String privatekey = httpServletRequest.getParameter("privatekey");
		String address = httpServletRequest.getParameter("address");
		if (password.equals(confirmPassword)) {
			User availableUser = userService.findByUsername(username);
			if (availableUser == null) {
				UserDTO userDTO = null;
				if (privatekey != null && address != null && !privatekey.isEmpty() && !address.isEmpty()) {
					userDTO = new UserDTO();
					userDTO.setAddress(address);
					userDTO.setPrivateKey(privatekey);
				} else {
					userDTO = WebAPICall.createAccount(password);
				}
				if (userDTO != null) {
					String verificationCode = UUID.randomUUID().toString();
					String userActivationLink = "";
					Properties properties = new Properties();
					try {
						properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("ico.properties"));
						userActivationLink = properties.getProperty("userActivationLink") + verificationCode;
					} catch (IOException e) {
						e.printStackTrace();
					}
					boolean isMailSent = emailService.sendRegistrationMessage(email, username, password, fullname, userActivationLink);
					if (isMailSent) {
						User user = new User();
						user.setUsername(username);
						user.setFullname(fullname);
						user.setPassword(password);
						user.setContactnumber(contactNumber);
						user.setEmail(email);
						user.setBcaddress(userDTO.getAddress());
						user.setKeystore(userDTO.getKeystore());
						user.setPrivatekey(userDTO.getPrivateKey());
						user.setVerificationcode(verificationCode);
						user.setEnabled(false);
						userService.addUser(user);
						
						model.addAttribute("successmsg", "User registration is successful!");
						return new ModelAndView("redirect:/register");
					} else {
						model.addAttribute("errormsg", "Could not send mail to registered email, Please try again!");
						return new ModelAndView("redirect:/register");
					}
					
				} else {
					model.addAttribute("errormsg", "Failed to create user account in blockchain, Please try again!");
					return new ModelAndView("redirect:/register");
				}
				
			} else {
				model.addAttribute("errormsg", "Username is not available, Please try again!");
				model.addAttribute("privatekey", privatekey);
				model.addAttribute("address", address);
				return new ModelAndView("redirect:/register");
			}
			
		} else {
			model.addAttribute("errormsg", "Password does not match, Please try again!");
			model.addAttribute("privatekey", privatekey);
			model.addAttribute("address", address);
			return new ModelAndView("redirect:/register");
		}
			
	}
	
	@RequestMapping(value = "/activeUser", method = RequestMethod.GET)
	public String activeUser(Model model, HttpServletRequest httpServletRequest) {
		String token = httpServletRequest.getParameter("token");
		if (token != null) {
			User user = userService.findByVerificationcode(token);
			if (user != null) {
				user.setEnabled(true);
				user.setVerificationcode("");
				userService.addUser(user);
				model.addAttribute("result", "<div class='alert alert-success col-sm-12'>Activation is Successful! <a href='index'> Login Here </a></div>");
			} else {
				model.addAttribute("result", "<div class='alert alert-danger col-sm-12'>Activiation is failed. Invalid Token!</div>");
			}
		} else {
			model.addAttribute("result", "<div class='alert alert-danger col-sm-12'>Activiation is failed. Invalid Token!</div>");
		}
	    return "activeUser";
	}
	
	
	@RequestMapping(value = "/loginUser", method = RequestMethod.POST)
	public ModelAndView loginUser(Model model, HttpServletRequest httpServletRequest) {
		Date loginTime = new Date();
		String userName = httpServletRequest.getParameter("userName");
		String password = httpServletRequest.getParameter("password");
		
		User user = userService.loginUser(userName, password);
		if (user != null) {
			if (user.isEnabled()) {
				HttpSession session = getSession();
				session.setAttribute("loginUser", user.getUserid());
				user.setLastLogin(user.getCurrentLogin());
				user.setCurrentLogin(loginTime);
				userService.addUser(user);
				
				return new ModelAndView("redirect:/mywallet");
			} else {
				model.addAttribute("errormsg", "User account is not enabled. Please enable from your mail and try again.");
				return new ModelAndView("redirect:/");	
			}
			
		}
		
		model.addAttribute("errormsg", "Invalid user name or password. Please try again.");
		return new ModelAndView("redirect:/");	
		
	}
	
	@RequestMapping(value = "/mywallet", method = RequestMethod.GET)
	public String mywalletScreen(Model model) {
	    
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		
		if(userId != null) {
			User user = userService.findByUserid(Long.parseLong(userId.toString()));
			user.setTokenBalance(WebAPICall.myTokenBalance(user.getBcaddress(), ""));
			model.addAttribute("currentUser", user);
			
			BigDecimal myBlanace = getMyBalance(user.getBcaddress());
			model.addAttribute("myBalance", myBlanace.setScale(2, BigDecimal.ROUND_UP));
			
			List<UserToken> userTokens = userTokenService.findByUser(user);
			for (UserToken userToken : userTokens) {
				userToken.setBalance(convertToDecimal(WebAPICall.myTokenBalance(user.getBcaddress(), userToken.getToken().getAddress())));
				userToken.setActive(WebAPICall.isTokenActive(userToken.getToken().getAddress()));
			}
			
			model.addAttribute("userTokens", userTokens);
			
//			List<TransactionLog> transactionLogs = transactionLogService.findMyTransactions(user.getBcaddress());
//			model.addAttribute("transactionLogs", transactionLogs);
			
			return "mywallet";
		}
		
		return "index";
	}
	
	@RequestMapping(value = "/tokens", method = RequestMethod.GET)
	public String tokensScreen(Model model) {
	    
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		
		if(userId != null) {
			User user = userService.findByUserid(Long.parseLong(userId.toString()));
			user.setTokenBalance(WebAPICall.myTokenBalance(user.getBcaddress(), ""));
			model.addAttribute("currentUser", user);
			List<UserToken> myTokens = new ArrayList<UserToken>();
			List<UserToken> otherTokens = new ArrayList<UserToken>();
			
			List<UserToken> userTokens = userTokenService.findByUser(user);
			Date now = new Date();
			for (UserToken userToken : userTokens) {
				userToken.setBalance(convertToDecimal(WebAPICall.myTokenBalance(user.getBcaddress(), userToken.getToken().getAddress())));
				userToken.setActive(WebAPICall.isTokenActive(userToken.getToken().getAddress()));
				userToken.setCrowdsaleActive(WebAPICall.isCrowdsaleActive(userToken.getToken().getAddress()));
				if (userToken.getToken().getStarttime() != null && userToken.getToken().getStarttime().before(now) && userToken.getToken().getEndtime().after(now)) 
					userToken.setShowBuy(true);
				if (userToken.getToken().getUser().getUserid() == user.getUserid()) {
					userToken.setFundRaised(getFunRaised(userToken.getToken().getAddress()).toString()); // .setScale(2, BigDecimal.ROUND_UP)
					myTokens.add(userToken);
				} else {
					otherTokens.add(userToken);
				}
			}
			
			model.addAttribute("myTokens", myTokens);
			model.addAttribute("otherTokens", otherTokens);
			return "tokens";
		}
		
		return "index";
	}
	
	@RequestMapping(value = "/newtoken", method = RequestMethod.GET)
	public String newtokenScreen(Model model) {
	    
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		
		if(userId != null) {
			User user = userService.findByUserid(Long.parseLong(userId.toString()));
			user.setTokenBalance(WebAPICall.myTokenBalance(user.getBcaddress(), ""));
			model.addAttribute("currentUser", user);
			
			return "newtoken";
		}
		
		return "index";
	}
	
	@RequestMapping(value = "/addtoken", method = RequestMethod.GET)
	public String addtokenScreen(Model model) {
	    
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		
		if(userId != null) {
			User user = userService.findByUserid(Long.parseLong(userId.toString()));
			user.setTokenBalance(WebAPICall.myTokenBalance(user.getBcaddress(), ""));
			model.addAttribute("currentUser", user);
			
			return "addtoken";
		}
		
		return "index";
	}
	
	@RequestMapping(value = "/startcrowdsale", method = RequestMethod.GET)
	public String startcrowdsaleScreen(Model model, HttpServletRequest httpServletRequest) {
	    
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		
		String refnumber = httpServletRequest.getParameter("refnumber");
		String active = httpServletRequest.getParameter("activeStatus");
		
		if(userId != null) {
			Token token = tokenService.findByTokenid(Long.parseLong(refnumber));
			if (token != null) {
				String result = WebAPICall.pauseOrResumeCrowdsale(token.getAddress(), active);
				if (result != null)
					if (active.equals("false"))
						return "redirect:tokens?successmsg=Successfully Paused Crowdsale!";
					else
						return "redirect:tokens?successmsg=Successfully Started Crowdsale!";
				else
					return "redirect:tokens?errormsg=Could not process your request, please try again!";
			} else {
				return "index";
			}
		}
		
		return "index";
	}
	
	@RequestMapping(value = "/freezetoken", method = RequestMethod.GET)
	public String freezetokenScreen(Model model, HttpServletRequest httpServletRequest) {
	    
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		
		String refnumber = httpServletRequest.getParameter("refnumber");
		String active = httpServletRequest.getParameter("activeStatus");
		
		if(userId != null) {
			Token token = tokenService.findByTokenid(Long.parseLong(refnumber));
			if (token != null) {
				String result = WebAPICall.freezeOrUnfreezeToken(token.getAddress(), active);
				if (result != null)
					if (active.equals("false"))
						return "redirect:tokens?successmsg=Successfully Freezed Token!";
					else
						return "redirect:tokens?successmsg=Successfully Unfreezed Token!";
				else
					return "redirect:tokens?errormsg=Could not process your request, please try again!";
			} else {
				return "index";
			}
		}
		
		return "index";
	}
	
	@RequestMapping(value = "/verifyandaddtoken", method = RequestMethod.GET)
	public String verifyandaddtokenScreen(Model model, HttpServletRequest httpServletRequest) {
	    
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		
		String refnumber = httpServletRequest.getParameter("refnumber");
		
		if(userId != null) {
			Token token = tokenService.findByTokenid(Long.parseLong(refnumber));
			if (token != null) {
				User user = userService.findByUserid(Long.parseLong(userId.toString()));
				user.setTokenBalance(WebAPICall.myTokenBalance(user.getBcaddress(), ""));
				model.addAttribute("token", token);
				model.addAttribute("currentUser", user);
				return "verifyandaddtoken";
			} else {
				return "index";
			}
		}
		
		return "index";
	}
	
	@RequestMapping(value = "/getTokenDetail", method = RequestMethod.POST)
	public ModelAndView getTokenDetail(Model model, HttpServletRequest httpServletRequest) {
		String tokenAddress = httpServletRequest.getParameter("tokenAddress");
		
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		
		if(userId != null) {
			User user = userService.findByUserid(Long.parseLong(userId.toString()));
			user.setTokenBalance(WebAPICall.myTokenBalance(user.getBcaddress(), ""));
			model.addAttribute("currentUser", user);
			
			Token token = tokenService.findByAddress(tokenAddress);
			if (token == null) {
				model.addAttribute("errormsg", "Token is not found with this address, please try again!");
				return new ModelAndView("redirect:/addtoken");
			} else {
				if (token.getUser().getUserid() != user.getUserid()) {
					UserToken userToken = userTokenService.findByUserAndToken(user, token);
					if (userToken != null) {
						model.addAttribute("errormsg", "You have already added this token!");
						return new ModelAndView("redirect:/addtoken");
					} else {
						model.addAttribute("refnumber", token.getTokenid());
						return new ModelAndView("redirect:/verifyandaddtoken");
					}
				} else {
					model.addAttribute("errormsg", "This is your token, and you have already have it in your token list!");
					return new ModelAndView("redirect:/addtoken");
				}
			}
		}
		
		return new ModelAndView("redirect:/index");
	}
	
	
	@RequestMapping(value = "/addTokenToUser", method = RequestMethod.POST)
	public ModelAndView addTokenToUser(Model model, HttpServletRequest httpServletRequest) {
		String tokenAddress = httpServletRequest.getParameter("tokenAddress");
		
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		
		if(userId != null) {
			User user = userService.findByUserid(Long.parseLong(userId.toString()));
			user.setTokenBalance(WebAPICall.myTokenBalance(user.getBcaddress(), ""));
			model.addAttribute("currentUser", user);
			
			Token token = tokenService.findByAddress(tokenAddress);
			if (token == null) {
				model.addAttribute("errormsg", "Token is not found with this address, please try again!");
				return new ModelAndView("redirect:/addtoken");
			} else {
				if (token.getUser().getUserid() != user.getUserid()) {
					UserToken userToken = userTokenService.findByUserAndToken(user, token);
					if (userToken != null) {
						model.addAttribute("errormsg", "You have already added this token!");
						return new ModelAndView("redirect:/addtoken");
					} else {
						userToken = new UserToken();
						userToken.setToken(token);
						userToken.setUser(user);
						userTokenService.addUserToken(userToken);
						model.addAttribute("successmsg", "Token is added successfully!");
						return new ModelAndView("redirect:/tokens");
					}
				} else {
					model.addAttribute("errormsg", "This is your token, and you have already have it in your token list!");
					return new ModelAndView("redirect:/addtoken");
				}
			}
		}
		
		return new ModelAndView("redirect:/index");
	}
	
	@RequestMapping(value = "/sendtoken", method = RequestMethod.GET)
	public String sendtokenScreen(Model model, HttpServletRequest httpServletRequest) {
	    
		String refnumber = httpServletRequest.getParameter("refnumber");
		
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		if(userId != null) {
			Token token = tokenService.findByTokenid(Long.parseLong(refnumber));
			if (token != null) {
				boolean active = WebAPICall.isTokenActive(token.getAddress());
				if (active) {
					User user = userService.findByUserid(Long.parseLong(userId.toString()));
					user.setTokenBalance(WebAPICall.myTokenBalance(user.getBcaddress(), ""));
					String balance = WebAPICall.myTokenBalance(user.getBcaddress(), token.getAddress());
					model.addAttribute("tokenName", token.getName());
					model.addAttribute("tokenId", token.getTokenid());
					model.addAttribute("tokenAddress", token.getAddress());
					model.addAttribute("balance", convertToDecimal(balance));
					model.addAttribute("currentUser", user);
					return "sendtoken";
				} else {
					return "redirect:tokens?errormsg=Token is freezed, please try again!";
				}
				
			} else {
				return "index";
			}
		}
		
		return "index";
	}
	
	
	@RequestMapping(value = "/updateStartTime", method = RequestMethod.GET)
	public String updateStartTimeScreen(Model model, HttpServletRequest httpServletRequest) {
	    
		String refnumber = httpServletRequest.getParameter("refnumber");
		
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		if(userId != null) {
			Token token = tokenService.findByTokenid(Long.parseLong(refnumber));
			if (token != null) {
				boolean active = WebAPICall.isTokenActive(token.getAddress());
				if (active) {
					User user = userService.findByUserid(Long.parseLong(userId.toString()));
					user.setTokenBalance(WebAPICall.myTokenBalance(user.getBcaddress(), ""));
					String balance = WebAPICall.myTokenBalance(user.getBcaddress(), token.getAddress());
					model.addAttribute("tokenName", token.getName());
					model.addAttribute("tokenId", token.getTokenid());
					model.addAttribute("tokenAddress", token.getAddress());
					String endTime = "";
					if (token.getEndtime() != null) {
						endTime = token.getEndtime().toString();
						endTime = endTime.substring(0, 16).replace(" ", "T");
					}
					model.addAttribute("endTime", endTime);
					model.addAttribute("balance", convertToDecimal(balance));
					model.addAttribute("currentUser", user);
					return "updateStartTime";
				} else {
					return "redirect:tokens?errormsg=Token is freezed, please try again!";
				}
				
			} else {
				return "index";
			}
		}
		
		return "index";
	}
	
	@RequestMapping(value = "/increasetoken", method = RequestMethod.GET)
	public String increasetokenScreen(Model model, HttpServletRequest httpServletRequest) {
	    
		String refnumber = httpServletRequest.getParameter("refnumber");
		
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		if(userId != null) {
			Token token = tokenService.findByTokenid(Long.parseLong(refnumber));
			if (token != null) {
				boolean active = WebAPICall.isTokenActive(token.getAddress());
				if (active) {
					User user = userService.findByUserid(Long.parseLong(userId.toString()));
					user.setTokenBalance(WebAPICall.myTokenBalance(user.getBcaddress(), ""));
					String balance = WebAPICall.myTokenBalance(user.getBcaddress(), token.getAddress());
					model.addAttribute("tokenName", token.getName());
					model.addAttribute("tokenId", token.getTokenid());
					model.addAttribute("tokenAddress", token.getAddress());
					model.addAttribute("balance", convertToDecimal(balance));
					model.addAttribute("currentUser", user);
					return "increasetoken";
				} else {
					return "redirect:tokens?errormsg=Token is freezed, please try again!";
				}
			} else {
				return "index";
			}
		}
		
		return "index";
	}
	
	
	
	@RequestMapping(value = "/sendether", method = RequestMethod.GET)
	public String sendetherScreen(Model model, HttpServletRequest httpServletRequest) {
		
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		if(userId != null) {
			User user = userService.findByUserid(Long.parseLong(userId.toString()));
			model.addAttribute("currentUser", user);
			BigDecimal myBlanace = getMyBalance(user.getBcaddress());
			model.addAttribute("myBalance", myBlanace.setScale(2, BigDecimal.ROUND_UP));
			return "sendether";
		}
		
		return "index";
	}
	
	@RequestMapping(value = "/buytoken", method = RequestMethod.GET)
	public String buytokenScreen(Model model, HttpServletRequest httpServletRequest) {
	    
		String refnumber = httpServletRequest.getParameter("refnumber");
		
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		if(userId != null) {
			Token token = tokenService.findByTokenid(Long.parseLong(refnumber));
			if (token != null) {
				boolean active = WebAPICall.isTokenActive(token.getAddress());
				if (active) {
					User user = userService.findByUserid(Long.parseLong(userId.toString()));
					user.setTokenBalance(WebAPICall.myTokenBalance(user.getBcaddress(), ""));
					model.addAttribute("tokenName", token.getName());
					model.addAttribute("tokenId", token.getTokenid());
					model.addAttribute("tokenAddress", token.getAddress());
					model.addAttribute("currentUser", user);
					return "buytoken";
				} else {
					return "redirect:tokens?errormsg=Token is freezed, please try again!";
				}
			} else {
				return "index";
			}
		}
		
		return "index";
	}
	
	@RequestMapping(value= "/removetoken", method = RequestMethod.GET)
	public ModelAndView removetokenScreen(Model model, HttpServletRequest httpServletRequest) {
		    
		String refnumber = httpServletRequest.getParameter("refnumber");
		
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		if(userId != null) {
			User user = userService.findByUserid(Long.parseLong(userId.toString()));
			Token token = tokenService.findByTokenid(Long.parseLong(refnumber));
			if (token != null) {
				UserToken userToken = userTokenService.findByUserAndToken(user, token);
				if (userToken != null) {
					userTokenService.removeUserToken(userToken);
					model.addAttribute("successmsg", "Token removed successfully!");
					return new ModelAndView("redirect:/tokens");
				} else {
					model.addAttribute("errormsg", "Could not remove the token, please try again!");
					return new ModelAndView("redirect:/tokens");
				}
			} else {
				return new ModelAndView("redirect:/index");
			}
		}
		
		return new ModelAndView("redirect:/index");
	}
	
	
	@RequestMapping(value = "/sendEtherToRecipient", method = RequestMethod.POST)
	public ModelAndView sendEtherToRecipient(Model model, HttpServletRequest httpServletRequest) {
		String etheramount = httpServletRequest.getParameter("etheramount");
		String toaddress = httpServletRequest.getParameter("toaddress");
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		
		if(userId != null) {
			User user = userService.findByUserid(Long.parseLong(userId.toString()));
			if (etheramount != null) {
				BigDecimal etherAmount = new BigDecimal(etheramount);
				BigDecimal myBalance = getMyBalance(user.getBcaddress());
				
				if (myBalance != null && myBalance.compareTo(etherAmount) > 0) {
					String privateKey = user.getPrivatekey();
					if (privateKey != null && !privateKey.isEmpty()) {
						privateKey = privateKey.substring(2, privateKey.length());
					}
					String result = WebAPICall.sendEther(user.getBcaddress(), privateKey, etherAmount.toString(), toaddress);
					if (result != null) {
						if (!result.contains("Error:")) {
							TransactionLog transactionLog = new TransactionLog();
							transactionLog.setTransactiondate(new Date());
							transactionLog.setFromaddress(user.getBcaddress());
							transactionLog.setToaddress(toaddress);
							transactionLog.setAmount(etheramount);
							transactionLog.setStatus(true);
							transactionLog.setToken(tokenService.findByAddress("ethertoken"));
							transactionLog.setReferencenumber(result);
							transactionLog.setType("Ether Transaction");
							transactionLogService.addTransactionLog(transactionLog);
							model.addAttribute("successmsg", "Ether sent Successfully!");
							return new ModelAndView("redirect:/sendether");
						} else {
							model.addAttribute("errormsg", result);
							return new ModelAndView("redirect:/sendether");
						}
						
					} else {
						model.addAttribute("errormsg", "Could not send ether. Please try again.");
						return new ModelAndView("redirect:/sendether");
					}
				} else {
					model.addAttribute("errormsg", "Insufficient ether!");
					return new ModelAndView("redirect:/sendether");
				}
			} else {
				model.addAttribute("errormsg", "Invalid ether amount. Please try again.");
				return new ModelAndView("redirect:/sendether");
			}
		}
		
		return new ModelAndView("redirect:/index");
	}
	
	@RequestMapping(value = "/buyTokenFromICO", method = RequestMethod.POST)
	public ModelAndView buyTokenFromICO(Model model, HttpServletRequest httpServletRequest) {
		String etheramount = httpServletRequest.getParameter("etheramount");
		String tokenaddress = httpServletRequest.getParameter("tokenaddress");
		String tokenid = httpServletRequest.getParameter("tokenid");
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		
		if(userId != null) {
			User user = userService.findByUserid(Long.parseLong(userId.toString()));
			Token token = tokenService.findByAddress(tokenaddress);
			if (token != null) {
				boolean active = WebAPICall.isTokenActive(token.getAddress());
				if (active) {
					if (etheramount != null) {
						BigDecimal etherAmount = new BigDecimal(etheramount);
						BigDecimal myBalance = getMyBalance(user.getBcaddress());
						
						if (myBalance != null && myBalance.compareTo(etherAmount) > 0) {
							String privateKey = user.getPrivatekey();
							if (privateKey != null && !privateKey.isEmpty()) {
								privateKey = privateKey.substring(2, privateKey.length());
							}
							String result = WebAPICall.buyToken(user.getBcaddress(), privateKey, etheramount, tokenaddress);
							if (result != null) {
								if (!result.contains("Error:")) {
									TransactionLog transactionLog = new TransactionLog();
									transactionLog.setTransactiondate(new Date());
									transactionLog.setFromaddress(user.getBcaddress());
									transactionLog.setToaddress(tokenaddress);
									transactionLog.setAmount(etheramount);
									transactionLog.setStatus(true);
									transactionLog.setToken(tokenService.findByAddress("ethertoken"));
									transactionLog.setReferencenumber(result);
									transactionLog.setType("Bought Token");
									transactionLogService.addTransactionLog(transactionLog);
									model.addAttribute("successmsg", "Successfully Bought Token!");
									return new ModelAndView("redirect:/buytoken?refnumber=" + tokenid);
								} else {
									model.addAttribute("errormsg", result);
									return new ModelAndView("redirect:/buytoken?refnumber=" + tokenid);
								}
								
							} else {
								model.addAttribute("errormsg", "Could not buy token. Please try again.");
								return new ModelAndView("redirect:/buytoken?refnumber=" + tokenid);
							}
						} else {
							model.addAttribute("errormsg", "Insufficient ether!");
							return new ModelAndView("redirect:/buytoken?refnumber=" + tokenid);
						}
					} else {
						model.addAttribute("errormsg", "Invalid ether amount. Please try again.");
						return new ModelAndView("redirect:/buytoken?refnumber=" + tokenid);
					}
				} else {
					model.addAttribute("errormsg", "Token is freezed, please try again!.");
					return new ModelAndView("redirect:/tokens");
				}
				
			} else {
				model.addAttribute("errormsg", "Invalid token. Please try again.");
				return new ModelAndView("redirect:/buytoken?refnumber=" + tokenid);
			}
			
		}
		
		return new ModelAndView("redirect:/index");
	}
	
	@RequestMapping(value = "/additionalTokenSupply", method = RequestMethod.POST)
	public ModelAndView additionalTokenSupply(Model model, HttpServletRequest httpServletRequest) {
		String tokensupply = httpServletRequest.getParameter("tokensupply");
		String tokenaddress = httpServletRequest.getParameter("tokenaddress");
		String tokenid = httpServletRequest.getParameter("tokenid");
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		
		if(userId != null) {
			Token token = tokenService.findByAddress(tokenaddress);
			if (token != null) {
				boolean active = WebAPICall.isTokenActive(token.getAddress());
				if (active) {
					if (tokensupply != null) {
						
						String transaction = WebAPICall.increaseTokenSupply(tokenaddress, convertToFullDecimal(tokensupply));
						if (transaction != null) {
							model.addAttribute("successmsg", "Successfully Updated Token Supply!");
							return new ModelAndView("redirect:/increasetoken?refnumber=" + tokenid);
						} else {
							model.addAttribute("errormsg", "Could not increase the token supply. Please try again.");
							return new ModelAndView("redirect:/increasetoken?refnumber=" + tokenid);
						}		
					} else {
						model.addAttribute("errormsg", "Invalid token supply. Please try again.");
						return new ModelAndView("redirect:/increasetoken?refnumber=" + tokenid);
					}
				} else {
					model.addAttribute("errormsg", "Token is freezed, please try again!.");
					return new ModelAndView("redirect:/tokens");
				}
				
			} else {
				model.addAttribute("errormsg", "Invalid token. Please try again.");
				return new ModelAndView("redirect:/increasetoken?refnumber=" + tokenid);
			}
			
		}
		
		return new ModelAndView("redirect:/index");
	}
	
	private String convertToFullDecimal(String value) {
		BigDecimal wei = new BigDecimal("1000000000000000000");
		BigDecimal fullValue = new BigDecimal(value);
		fullValue = fullValue.multiply(wei);
		return fullValue.toPlainString();
	}
	
	private String convertToDecimal(String value) {
		BigDecimal wei = new BigDecimal("1000000000000000000");
		BigDecimal fullValue = new BigDecimal(value);
		fullValue = fullValue.divide(wei);
		return fullValue.toPlainString();
	}
	
	@RequestMapping(value = "/createNewToken", method = RequestMethod.POST)
	public ModelAndView createNewToken(Model model, HttpServletRequest httpServletRequest) {
		String tokenName = httpServletRequest.getParameter("tokenName");
		String tokenSymbol = httpServletRequest.getParameter("tokenSymbol");
		String crowdsale = httpServletRequest.getParameter("crowdsale");
		String initialSupply = httpServletRequest.getParameter("initialSupply");
		String tokenPrice = httpServletRequest.getParameter("tokenPrice");
		String startDate = httpServletRequest.getParameter("startTime");
		String endDate = httpServletRequest.getParameter("endTime");
		
		long startTime = 0;
		long endTime = 0;
		
		Date startdate = null;
		Date enddate = null;
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			enddate = sdf.parse(endDate.replace("T", " "));
			endTime = enddate.getTime();
			
			if (startDate != null && !startDate.isEmpty()) {
				startdate = sdf.parse(startDate.replace("T", " "));
				startTime = startdate.getTime();
				Date now = new Date();
				if (startdate.before(now)) {
					model.addAttribute("errormsg", "Crowdsale start time should be in future time!");
					return new ModelAndView("redirect:/newtoken");
				} else if (enddate.before(startdate)) {
					model.addAttribute("errormsg", "Crowdsale end time should be after start time!");
					return new ModelAndView("redirect:/newtoken");
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
			model.addAttribute("errormsg", "Start and End Crowdsale date issue, please contract admin!");
			return new ModelAndView("redirect:/newtoken");
		}
		
		
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		
		if(userId != null) {
			User user = userService.findByUserid(Long.parseLong(userId.toString()));
			
			Token token = tokenService.findByNameAndSymbol(tokenName, tokenSymbol);
			if (token == null) {
				
				String tokenAddress = WebAPICall.createToken(tokenName, tokenSymbol, crowdsale, initialSupply, tokenPrice, user.getBcaddress(), "" + startTime, "" + endTime);
				if (tokenAddress != null && tokenAddress.startsWith("0x")) {
					token = new Token();
					token.setName(tokenName);
					token.setSymbol(tokenSymbol);
					token.setCrowdsalepercentage(Integer.parseInt(crowdsale));
					token.setInitialsupply(Long.parseLong(initialSupply));
					token.setTokenprice(Integer.parseInt(tokenPrice));
					token.setUser(user);
					token.setAddress(tokenAddress);
					token.setCreatedon(new Date());
					token.setStarttime(startdate);
					token.setEndtime(enddate);
					tokenService.addToken(token);
					
					token = tokenService.findByNameAndSymbol(tokenName, tokenSymbol);
					if (token != null) {
						UserToken userToken = new UserToken();
						userToken.setUser(user);
						userToken.setToken(token);
						userTokenService.addUserToken(userToken);
						model.addAttribute("successmsg", "Token created Successfully!");
						return new ModelAndView("redirect:/newtoken");
					} else {
						model.addAttribute("errormsg", "Could not create token. Please try again.");
						return new ModelAndView("redirect:/newtoken");
					}
				} else {
					System.out.println("Error in Creating Token: " + tokenAddress);
					model.addAttribute("errormsg", "Could not create token. Please try again.");
					return new ModelAndView("redirect:/newtoken");
				}
			} else {
				model.addAttribute("errormsg", "Token with name and symbol available, please try again with new name and symbol!");
				return new ModelAndView("redirect:/newtoken");
			}
		}
		
		return new ModelAndView("redirect:/index");
	}
	
	@RequestMapping(value = "/transactions", method = RequestMethod.GET)
	public String myTransactions(Model model) {
	    
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		
		if(userId != null) {
			User user = userService.findByUserid(Long.parseLong(userId.toString()));
			model.addAttribute("currentUser", user);
			
			List<TransactionLog> transactionLogs = transactionLogService.findMyTransactions(user.getBcaddress());
			model.addAttribute("transactionLogs", transactionLogs);
			
			return "transactions";
		}
		
		return "index";
	}
	
	@RequestMapping(value = "/myprofile", method = RequestMethod.GET)
	public String myprofileScreen(Model model) {
	    
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		
		if(userId != null) {
			User user = userService.findByUserid(Long.parseLong(userId.toString()));
			
			model.addAttribute("currentUser", user);
			
			BigDecimal myBlanace = getMyBalance(user.getBcaddress());
			model.addAttribute("myBalance", myBlanace.setScale(2, BigDecimal.ROUND_UP));
			
			return "myprofile";
		}
		
		return "index";
	}
	
	
	@RequestMapping(value = "/sendTokenToUser", method = RequestMethod.POST)
	public ModelAndView sendTokenToUser(Model model, HttpServletRequest httpServletRequest) {
		String toaddress = httpServletRequest.getParameter("toaddress");
		String tokenamount = httpServletRequest.getParameter("tokenamount");
		String tokenid = httpServletRequest.getParameter("tokenid");
		String tokenaddress = httpServletRequest.getParameter("tokenaddress");
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		
		if(userId != null) {
			User user = userService.findByUserid(Long.parseLong(userId.toString()));
			
			if (toaddress != null && tokenamount != null) {
				if(toaddress.length() == 42 && toaddress.startsWith("0x")) {
					if (!toaddress.equals(user.getBcaddress())) {
						
						Token token = tokenService.findByAddress(tokenaddress);
						if (token != null) {
							
							boolean active = WebAPICall.isTokenActive(token.getAddress());
							if (active) {
								BigDecimal myBalance = new BigDecimal(convertToDecimal(WebAPICall.myTokenBalance(user.getBcaddress(), tokenaddress)));
								BigDecimal sendTokenAmount = new BigDecimal(tokenamount);
								if (myBalance != null && myBalance.compareTo(sendTokenAmount) > 0) {
									String privateKey = user.getPrivatekey();
									if (privateKey != null && !privateKey.isEmpty()) {
										privateKey = privateKey.substring(2, privateKey.length());
									}
									String result = WebAPICall.transferToken(user.getBcaddress(), privateKey, toaddress, convertToFullDecimal(tokenamount), tokenaddress);
									if (result != null) {
										if (!result.contains("Error:")) {
											TransactionLog transactionLog = new TransactionLog();
											transactionLog.setTransactiondate(new Date());
											transactionLog.setFromaddress(user.getBcaddress());
											transactionLog.setToaddress(toaddress);
											transactionLog.setAmount(tokenamount);
											transactionLog.setStatus(true);
											transactionLog.setToken(token);
											transactionLog.setReferencenumber(result);
											transactionLog.setType("Token Transaction");
											transactionLogService.addTransactionLog(transactionLog);
											model.addAttribute("successmsg", "Successfully Transferred Token!");
											return new ModelAndView("redirect:/sendtoken?refnumber=" + tokenid);
										} else {
											model.addAttribute("errormsg", result);
											return new ModelAndView("redirect:/sendtoken?refnumber=" + tokenid);
										}
										
									} else {
										model.addAttribute("errormsg", "Could not transfer token. Please try again.");
										return new ModelAndView("redirect:/sendtoken?refnumber=" + tokenid);
									}
								} else {
									model.addAttribute("errormsg", "Insufficient token!");
									return new ModelAndView("redirect:/sendtoken?refnumber=" + tokenid);
								}
							} else {
								model.addAttribute("errormsg", "Token is freezed, please try again!.");
								return new ModelAndView("redirect:/tokens");
							}
						} else {
							model.addAttribute("errormsg", "Invalid Token, Please try again.");
							return new ModelAndView("redirect:/sendtoken?refnumber=" + tokenid);
						}
						
					} else {
						model.addAttribute("errormsg", "You can't send token to yourself!");
						return new ModelAndView("redirect:/sendtoken?refnumber=" + tokenid);
					}
				} else {
					model.addAttribute("errormsg", "Invalid To Address. Please try again.");
					return new ModelAndView("redirect:/sendtoken?refnumber=" + tokenid);
				}
			}
		}
		
		return new ModelAndView("redirect:/index");
	}
	
	@RequestMapping(value = "/updateTokenStartTime", method = RequestMethod.POST)
	public ModelAndView updateTokenStartTime(Model model, HttpServletRequest httpServletRequest) {
		String launchDate = httpServletRequest.getParameter("launchDate");
		String tokenid = httpServletRequest.getParameter("tokenid");
		String tokenaddress = httpServletRequest.getParameter("tokenaddress");
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		
		if(userId != null) {
			
			if (launchDate != null && !launchDate.isEmpty()) {
				Token token = tokenService.findByAddress(tokenaddress);
				if (token != null) {
					boolean active = WebAPICall.isTokenActive(token.getAddress());
					if (active) {
						long startTime = 0;
						Date startdate = null;
						try {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
							Date enddate = token.getEndtime();
							
							startdate = sdf.parse(launchDate.replace("T", " "));
							startTime = startdate.getTime();
							Date now = new Date();
							
							if (startdate.before(now)) {
								model.addAttribute("errormsg", "Crowdsale start time should be in future time!");
								return new ModelAndView("redirect:/updateStartTime?refnumber=" + tokenid);
							} else if (enddate.before(startdate)) {
								model.addAttribute("errormsg", "Crowdsale start time should be before end time!");
								return new ModelAndView("redirect:/updateStartTime?refnumber=" + tokenid);
							}
						} catch (ParseException e) {
							e.printStackTrace();
							model.addAttribute("errormsg", "Crowdsale start time issue, please contract admin!");
							return new ModelAndView("redirect:/updateStartTime?refnumber=" + tokenid);
						}
						
						String result = WebAPICall.changeStartTime(tokenaddress, "" + startTime);
						if (result != null) {
							WebAPICall.pauseOrResumeCrowdsale(tokenaddress, "" + true);
							token.setStarttime(startdate);
							tokenService.addToken(token);
							model.addAttribute("successmsg", "Successfully updated start time!");
							return new ModelAndView("redirect:/tokens");
						} else {
							model.addAttribute("errormsg", "Could not updated start time. Please try again.");
							return new ModelAndView("redirect:/updateStartTime?refnumber=" + tokenid);
						}
					} else {
						model.addAttribute("errormsg", "Token is freezed, please try again!.");
						return new ModelAndView("redirect:/tokens");
					}
				} else {
					model.addAttribute("errormsg", "Invalid Token, Please try again.");
					return new ModelAndView("redirect:/updateStartTime?refnumber=" + tokenid);
				}
			} else {
				model.addAttribute("errormsg", "Start Time is empty, Please try again.");
				return new ModelAndView("redirect:/updateStartTime?refnumber=" + tokenid);
			}
		}
		
		return new ModelAndView("redirect:/index");
	}
}
