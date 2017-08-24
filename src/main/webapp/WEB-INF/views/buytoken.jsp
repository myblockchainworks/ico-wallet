<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>ICO Token</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="<c:url value="/resources/css/bootstrap.min.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/sb-admin.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700" rel="stylesheet">  

<link
	href="<c:url value="/resources/css/plugins/morris.css" />"
	rel="stylesheet">

<link
	href="<c:url value="/resources/font-awesome/css/font-awesome.min.css" />"
	rel="stylesheet" type="text/css">
<link href="<c:url value="/resources/css/date.css" />" rel="stylesheet">

<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
<script src="<c:url value="/resources/js/ico.js" />"></script>

</head>
<body>

	<div id="wrapper">

		<!-- Navigation -->
		<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-ex1-collapse">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">ICO Wallet</a>

		</div>
		<!-- Top Menu Items -->
		<ul class="nav navbar-left top-nav">
			<div class="row">
				<div class="col-sm-6">
					<div class="search">

						<div class="input-group col-md-12">
							<form class="navbar-form" role="search">
								<div class="input-group add-on">
									<div class="input-group-btn">
										<button class="btn btn-default1" type="submit">
											<i class="glyphicon glyphicon-search"></i>
										</button>
									</div>
									<input class="form-control" placeholder="Search"
										name="srch-term" id="srch-term" type="text">

								</div>
							</form>

						</div>



					</div>
				</div>
				<div class="col-sm-6">
					<ul class="top_hed pull-right">
						<li>
							<div class="imge">
								<img src="<c:url value="/resources/images/user.png"/>" style="height: 45px;width: 45px;">
							</div>
						</li>
						<li><div class="tex">
								<h2>${currentUser.fullname}</h2>
							</div>
						</li>
					</ul>
					
				</div>
			</div>
		</ul>
		<!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
		<div class="collapse navbar-collapse navbar-ex1-collapse">
			<ul class="nav navbar-nav side-nav">
				<li><a href="mywallet"><i class="fa fa-google-wallet" aria-hidden="true"></i>
						My Wallet</a></li>
				<li><a href="sendether"><i class="fa fa-paper-plane" aria-hidden="true"></i>
						Send Ether</a></li>
				<li class="active"><a href="tokens"><i class="fa fa-money" aria-hidden="true"></i>
						Tokens</a></li>
					<li><a href="transactions"><i class="fa fa-tasks" aria-hidden="true"></i>
						My Transactions</a></li>
				<li><a href="myprofile"><i class="fa fa-user" aria-hidden="true"></i>
						My Profile</a></li>
				
				<!-- <li><a href="#"><i class="fa fa-usd" aria-hidden="true"></i>
						Funds</a></li> -->
				<li><a href="logout"><i class="fa fa-sign-out"
						aria-hidden="true"></i> Logout</a></li>

			</ul>
		</div>
		</nav>
		
		<div class="container" style="padding-top: 50px;">
			<div class="errorMessageDiv"><script>showErrorMessage('<c:out value='${param.errormsg}'/>')</script></div>
			<div class="successMessageDiv"><script>showSuccessMessage('<c:out value='${param.successmsg}'/>')</script></div>
			<div class="row">
		   		<div class="col-lg-12">
		   			<div class="breadcrumb">
						<div class="panel panel-info" >
							<div class="panel-heading">
		                     	<div class="panel-title">Send Token</div>
		                 	</div>
		                 	<div class="panel-body">
		                 		<form class="form-horizontal" role="form" action="buyTokenFromICO" method="post" id="buyTokenForm">
		                 			<div class="form-group">
										<div class="col-sm-2">
											<span>Token</span> 
										</div>
										<div class="col-sm-6">
											<input type="text" id="tokenname" name="tokenname"
													placeholder="e.g., Test Token" class="form-control" value="${tokenName}" readonly="readonly">
										</div>
									</div>
									<div class="form-group">
										<div class="col-sm-2">
											<span>Address</span> 
										</div>
										<div class="col-sm-6">
											<input type="text" id="tokenaddress" name="tokenaddress"
													placeholder="e.g., 1000" class="form-control" value="${tokenAddress}" readonly="readonly">
										</div>
									</div>
		                 			<div class="form-group">
										<div class="col-sm-2">
											<span>Amount</span>
										</div>
										<div class='col-sm-6'>
											<input type="number" id="etheramount" name="etheramount" step="any"
													placeholder="e.g., 1.25 (Amount should be in ether)" class="form-control" autofocus required>
										</div>
		
									</div>
									<div class="form-group">
										<div class="col-sm-7">
											
										</div>
										<div class='col-sm-2'>
											<input type="hidden" id="tokenid" name="tokenid" value="${tokenId}">
											<input type="button" value="Buy" onclick="buyTokenConfirmation();">
											<!-- <button>Buy</button> -->
										</div>
									</div>
		                 		</form>
							</div>
	                 	</div>
					</div>
		   		</div>
			</div>
		</div> 
	</div>
</body>
</html>