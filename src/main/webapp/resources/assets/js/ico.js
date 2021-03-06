
function formatAndDisplayDate(value, className, token) {
	if (value == "") {
		if (token != undefined)
			$('.' + className).html('<a href="updateStartTime?refnumber=' + token + '" class="buttontype" style="background-color: #8a6d3b;">Update</a>');
		else 
			$('.' + className).html('');
		return;
	}
	if (value.endsWith(".0"))
		value = value.substring(0, value.length - 2).replace(' ', 'T');
	var date = new Date(value);
	var monthNames = [
	  "Jan", "Feb", "Mar",
	  "Apr", "May", "Jun", "Jul",
	  "Aug", "Sep", "Oct",
	  "Nov", "Dec"
	];
	var day = date.getDate();
	var monthIndex = date.getMonth();
	var year = date.getFullYear();
	
	var hour = date.getHours()
	var min = date.getMinutes();
	if (hour < 10) {
		hour = '0' + hour;
	} 
	
	if (min < 10) {
		min = '0' + min;
	}
	
	var formatted = day + '-' + monthNames[monthIndex] + '-' + year + ' ' + hour + ':' + min;
	$('.' + className).html(formatted); 
}


//Calculate the difference of two dates in total days
function diffDays(d1, d2)
{
  var ndays;
  var tv1 = d1.valueOf();  // msec since 1970
  var tv2 = d2.valueOf();

  ndays = (tv2 - tv1) / 1000 / 86400;
  ndays = Math.round(ndays - 0.5);
  return ndays;
}

function showErrorMessageWallet(message) {
	if (message != "") {
		$(".errorMessageDiv").html('<div id="login-alert" class="alert alert-danger col-sm-12">' + message + '</div>');
		setTimeout(function() { 
			$(".errorMessageDiv").fadeOut(1000);
			window.location.href = "tokens";
		}, 3000);
	}
}

function showSuccessMessageWallet(message) {
	if (message != "") {
		$(".successMessageDiv").html('<div id="login-alert" class="alert alert-success col-sm-12">' + message + '</div>');
		setTimeout(function() { 
			$(".successMessageDiv").fadeOut(1000);
			window.location.href = "tokens";
		}, 3000);
	}
}

function showErrorMessage(message) {
	if (message != "") {
		$(".errorMessageDiv").html('<div id="login-alert" class="alert alert-danger col-sm-12">' + message + '</div>');
		setTimeout(function() { 
			$(".errorMessageDiv").fadeOut(1000);
		}, 3000);
	}
}

function showSuccessMessage(message) {
	if (message != "") {
		$(".successMessageDiv").html('<div id="login-alert" class="alert alert-success col-sm-12">' + message + '</div>');
		setTimeout(function() { 
			$(".successMessageDiv").fadeOut(1000);
		}, 3000);
	}
}

function formatAddress(address, className, currentAddress) {
	var formatted = address;
	if (address == currentAddress) {
		formatted = '<span style="font-weight: bold;color: #087480;">' + address + '</span>'
	}
	$('.' + className).html(formatted); 
}

function downloadKeyStore() {
	var isSafari = /constructor/i.test(window.HTMLElement) || (function (p) { return p.toString() === "[object SafariRemoteNotification]"; })(!window['safari'] || (typeof safari !== 'undefined' && safari.pushNotification));

	if (isSafari) {
		alert ("Download keyfile has been blocked due to the file protocol, please use other brower! (i.e. Chrome or Firefox)");
	} else {
		var privatekey = $('#privatekey').html();
		var downloadData = $('#keystoreJson').val();
		downloadData = downloadData.replace(/\\/g, '\"');
		
		var hiddenElement = document.createElement('a');
		document.body.appendChild(hiddenElement);
		hiddenElement.setAttribute("type", "hidden"); // make it hidden if needed
		hiddenElement.href = 'data:text/json;charset=utf-8,' + encodeURI(downloadData);
		hiddenElement.target = '_blank';
		hiddenElement.download = 'keystore-' + privatekey + '.json';
		hiddenElement.click();
	}
}

function buyTokenConfirmation() {
	var ether = $('#etheramount').val();
	if (ether != undefined && ether != '') {
		if(confirm ("Are you sure you want to buy token for " + ether + " Ether?")) {
			$('#buyTokenForm').submit();
		}
	} else {
		alert ("Please enter the ether amount to buy token!");
	}
}

function sendTokenConfirmation() {
	var toaddress = $('#toaddress').val();
	var tokenamount = $('#tokenamount').val();
	if (toaddress != undefined && toaddress != '' && tokenamount != undefined && tokenamount != '') {
		if(confirm ("Are you sure you want to send " + tokenamount + " tokens to " + toaddress + "?")) {
			$('#sendTokenForm').submit();
		}
	} else {
		alert ("Please fill the recipient address and token amount!");
	}
}

function addAdditionTokenConfirmation() {
	var token = $('#tokensupply').val();
	if (token != undefined && token != '') {
		if(confirm ("Are you sure you want to increase the token supply by " + token + "?")) {
			$('#addAdditionalTokenForm').submit();
		}
	} else {
		alert ("Please enter the additional token supply you wanted to increase!");
	}
}

function sendEtherConfirmation() {
	var toaddress = $('#toaddress').val();
	var etheramount = $('#etheramount').val();
	if (toaddress != undefined && toaddress != '' && etheramount != undefined && etheramount != '') {
		if(confirm ("Are you sure you want to send " + etheramount + " ether to " + toaddress + "?")) {
			$('#sendEtherForm').submit();
		}
	} else {
		alert ("Please fill the recipient address and ether amount!");
	}
}

function showSendAction(token, className, balance, active, showBuy, isCrowdsaleActive) {
	var pauseCrowdsale = '';
	if (showBuy == "true") {
		if (isCrowdsaleActive == "true") 
			pauseCrowdsale = '<a style="background-color:#ff9222" onclick="confirmStartCrowdsale(' + token + ', false)" class="buttontype">Pause</a> ';
		else
			pauseCrowdsale = '<a style="background-color:#2dbcc4" onclick="confirmStartCrowdsale(' + token + ', true)" class="buttontype">Start</a> ';
	}
	var freezeActive = '<a style="background-color:#be0505" onclick="confrimationFreeze(' + token + ', false)" class="buttontype">Freeze</a>';
	if (active == "false") {
		freezeActive = '<a style="background-color:#2dbcc4" onclick="confrimationFreeze(' + token + ', true)" class="buttontype">Unfreeze</a>';
		$('.' + className).html(freezeActive);
	} else {
		if (balance > 500000)
			$('.' + className).html(pauseCrowdsale + ' <a href="sendtoken?refnumber=' + token + '" class="buttontype">Send</a> ' + freezeActive);
		else 
			$('.' + className).html(pauseCrowdsale + '<a href="sendtoken?refnumber=' + token + '" class="buttontype">Send</a> <a style="background-color: #3c763d;" href="increasetoken?refnumber=' + token + '" class="buttontype"> + Supply</a> ' + freezeActive);
	}
}

function showSendBuyAction(token, className, showBuy, active, isCrowdsaleActive) {
	if (active == "true") {
		if (showBuy == "true" && isCrowdsaleActive == "true")
			$('.' + className).html('<a href="sendtoken?refnumber=' + token + '" class="buttontype">Send</a> <a style="background-color: #2dbcc4;" href="buytoken?refnumber=' + token + '" class="buttontype">Buy</a> <a style="background-color:#be0505" href="removetoken?refnumber=' + token + '" class="buttontype">Remove</a>');
		else 
			$('.' + className).html('<a href="sendtoken?refnumber=' + token + '" class="buttontype">Send</a> <a style="background-color:#be0505" href="removetoken?refnumber=' + token + '" class="buttontype">Remove</a>');
	} else {
		$('.' + className).html('<a style="background-color:#be0505" href="removetoken?refnumber=' + token + '" class="buttontype">Remove</a>');
	}
	
}

function showFreezeIcon(active, className) {
	if (active == "false") {
		$('.' + className).html('<img src="resources/images/freeze-icon.png" style="height: 36px;width: 36px;">');
	}
}

function confirmStartCrowdsale(referNumber, activeStatus) {
	if (activeStatus) {
		if(confirm ("Are you sure you want to start crowdsale?")) {
			window.location.href = 'startcrowdsale?refnumber=' + referNumber + '&activeStatus=true'
		}
	} else {
		if(confirm ("Are you sure you want to pause crowdsale?")) {
			window.location.href = 'startcrowdsale?refnumber=' + referNumber + '&activeStatus=false'
		}
	}
}

function confrimationFreeze(referNumber, activeStatus) {
	if (activeStatus) {
		if(confirm ("Are you sure you want to unfreeze the token?")) {
			window.location.href = 'freezetoken?refnumber=' + referNumber + '&activeStatus=true'
		}
	} else {
		if(confirm ("Are you sure you want to freeze the token?")) {
			window.location.href = 'freezetoken?refnumber=' + referNumber + '&activeStatus=false'
		}
	}
}