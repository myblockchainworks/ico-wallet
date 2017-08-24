<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>ICO Token - Login</title>
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
        <div id="loginbox" style="margin-top: 70px;" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
		<center>
			<%-- <img class="new_log" src="<c:url value="/resources/images/logo.svg" />" alt="" style="height: 100px;"> --%>
			<h1 style="color:#d8ac24">ICO Token</h1>
		</center>
            <div class="panel panel-info" >
                 <div class="panel-heading">
                     <div class="panel-title">Sign In</div>

                 </div>
                 <form action="loginUser" method="post">
                    <div style="padding-top:30px" class="panel-body" >
						<div class="errorMessageDiv"><script>showErrorMessage('<c:out value='${param.errormsg}'/>')</script></div>

                        <!-- <form id="loginform" class="form-horizontal" role="form"> -->
							<div class="form-horizontal">
                            <div style="margin-bottom: 25px" class="input-group">
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                                        <input id="userName" type="text" class="form-control" name="userName" value="" placeholder="Username">
                                    </div>

                            <div style="margin-bottom: 25px" class="input-group">
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                                        <input id="password" type="password" class="form-control" name="password" placeholder="Password">
                                    </div>
                            <div class="input-group">
                                      <div class="checkbox">
                                        <label>
                                          <input id="login-remember" type="checkbox" name="remember" value="1"> Remember me
                                        </label>
                                      </div>
                                    </div>
                                <div style="margin-top:10px" class="form-group">
                                    <!-- Button -->

                                    <div class="col-sm-12 controls">
                                     <!--  <a id="btn-login" href="index.html" class="btn btn-success">Login  </a> -->
										<button id="btn-login" type="submit" class="btn btn-success">Login</button>
                                		<div style="float:right; font-size: 80%; position: relative; top:-10px"><a href="#">Forgot password?</a></div>
                                    </div>
                                </div>


                                <div class="form-group">
                                    <div class="col-md-12 control">
                                        <div style="border-top: 1px solid#888; padding-top:15px; font-size:85%" >
                                            Don't have an account!
                                        <a href="register">
                                            Sign Up Here
                                        </a>
                                        </div>
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