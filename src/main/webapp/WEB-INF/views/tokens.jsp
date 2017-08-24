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
				<li class="active"><a href="#"><i class="fa fa-money" aria-hidden="true"></i>
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
		                     	<div class="panel-title">My Tokens <a href="newtoken" style="float:right;border-radius: 4px; background-color: #087480;color: #FFFFFF; font-size: 14px; padding: 4px; cursor: pointer;text-decoration: none;margin-top: -3px;">+ New Token</a></div>
		                 	</div>
		                 	<div class="panel-body">
		                 		<table id="products" class="table table-bordered table-hover">
									<tr>
										<th>#</th>
										<th>Created Date</th> 
										<th>Address</th> 
										<th>Name</th> 
										<th>Symbol</th> 
										<th>Balance</th>
										<th>Action</th>
									</tr>
									<c:set var="count" value="0" scope="page" />
									<c:forEach items="${myTokens}" var="token">
										<c:set var="count" value="${count + 1}" scope="page"/>
										<tr>
											<td>${count}</td>
											<td class="createdon${count}"><script>formatAndDisplayDate('<c:out value='${token.token.createdon}'/>', 'createdon' + '<c:out value='${count}'/>');</script></td>
											<td>${token.token.address}</td>
											<td>${token.token.name}</td>
											<td>${token.token.symbol}</td>
											<td>${token.balance}</td>
											<td class="sendtoken${count}"><script>showSendAction('<c:out value='${token.token.tokenid}'/>', 'sendtoken' + '<c:out value='${count}'/>', '<c:out value='${currentUser.bcaddress}'/>')</script>
										</tr>
									</c:forEach>
								</table>
							</div>
	                 	</div>
					</div>
		   		</div>
		   		
		   		<div class="col-lg-12">
		   			<div class="breadcrumb">
						<div class="panel panel-info" >
							<div class="panel-heading">
		                     	<div class="panel-title">Watch Tokens <a href="addtoken" style="float:right;border-radius: 4px; background-color: #087480;color: #FFFFFF; font-size: 14px; padding: 4px; cursor: pointer;text-decoration: none;margin-top: -3px;"> Add Token </a></div>
		                 	</div>
		                 	<div class="panel-body">
		                 		<table id="products" class="table table-bordered table-hover">
									<tr>
										<th>#</th>
										<th>Address</th> 
										<th>Name</th> 
										<th>Symbol</th> 
										<th>Balance</th>
										<th>Action</th>
									</tr>
									<c:set var="count" value="0" scope="page" />
									<c:forEach items="${otherTokens}" var="token">
										<c:set var="count" value="${count + 1}" scope="page"/>
										<tr>
											<td>${count}</td>
											<td>${token.token.address}</td>
											<td>${token.token.name}</td>
											<td>${token.token.symbol}</td>
											<td>${token.balance}</td>
											<td class="sendbuytoken${count}"><script>showSendBuyAction('<c:out value='${token.token.tokenid}'/>', 'sendbuytoken' + '<c:out value='${count}'/>', '<c:out value='${currentUser.bcaddress}'/>')</script>
										</tr>
									</c:forEach>
								</table>
							</div>
	                 	</div>
					</div>
		   		</div>
			</div>
		</div> 
	</div>
</body>
</html>