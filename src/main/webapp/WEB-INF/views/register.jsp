<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>TWG Token - Register</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/font-awesome.min.css" />" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700" rel="stylesheet">  
<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
<script src="<c:url value="/resources/js/ico.js" />"></script>
</head>
<body>

	<div class="center_form1">
      <div class="container">
        <div id="loginbox" style="margin-top: 10px;" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
		<center>
			<%-- <img class="new_log" src="<c:url value="/resources/images/logo.svg" />" alt="" style="height: 100px;"> --%>
			<h1 style="color:#d8ac24">ICO Token</h1>
		</center>
            <div class="panel panel-info" >
                 <div class="panel-heading">
                     <div class="panel-title">Registration</div>

                 </div>
                 <form action="registerUser" method="post">
                    <div style="padding-top:30px" class="panel-body" >
						<div class="errorMessageDiv"><script>showErrorMessage('<c:out value='${param.errormsg}'/>')</script></div>
						<div class="successMessageDiv"><script>showSuccessMessage('<c:out value='${param.successmsg}'/>')</script></div>
							<div class="form-horizontal">
                            	<div style="margin-bottom: 25px" class="input-group">
                                       <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                                       <input id="fullname" type="text" class="form-control" name="fullname" value="" placeholder="Fullname" required>
                                </div>
                                
                                <div style="margin-bottom: 25px" class="input-group">
                                       <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                                       <input id="username" type="text" class="form-control" name="username" value="" placeholder="Username" required>
                                </div>

                            	<div style="margin-bottom: 25px" class="input-group">
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                                        <input id="password" type="password" class="form-control" name="password" placeholder="Password" required>
                                 </div>
                                 
                                 <div style="margin-bottom: 25px" class="input-group">
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                                        <input id="confirmPassword" type="password" class="form-control" name="confirmPassword" placeholder="Confirm Password" required>
                                 </div>
                                 
                                 <div style="margin-bottom: 25px" class="input-group">
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-earphone"></i></span>
                                        <input id="contactNumber" type="text" class="form-control" name="contactNumber" placeholder="Contact Number" required>
                                 </div>
                                 <div style="margin-bottom: 25px" class="input-group">
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-globe"></i></span>
                                        <input id="email" type="text" class="form-control" name="email" placeholder="Email" required>
                                 </div>
                                <div style="margin-top:10px" class="form-group">
                                    <!-- Button -->
                                    <div class="col-sm-12 controls">
                                    	<input type="hidden" name="privatekey" value="<c:out value='${param.privatekey}'/>">
	                    				<input type="hidden" name="address" value="<c:out value='${param.address}'/>">
										<button id="btn-login" type="submit" class="btn btn-success">Register</button>
										<button class="btn" style="background-color: gray;" id="hideAddProductScreen" type="reset">Reset</button> <a href="index" class="btn btn-info">Back</a>
                                    </div>
                                </div>

                                </div>
	                        </div>
	                        
                        </form>
                    </div>
        	</div>
    	</div>
	</div>
</body>
</html>