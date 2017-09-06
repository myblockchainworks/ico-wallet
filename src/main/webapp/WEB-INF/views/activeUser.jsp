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
                     <div class="panel-title">User Registration Activation</div>

                 </div>
                 <form action="loginUser" method="post">
                    <div style="padding-top:30px" class="panel-body" >
						<div class="errorMessageDiv"><script>showErrorMessage('<c:out value='${param.errormsg}'/>')</script></div>

                        <!-- <form id="loginform" class="form-horizontal" role="form"> -->
							<div class="form-horizontal">
                                <div style="margin-top:10px" class="form-group">

                                    <div class="col-sm-12 controls">
                                    	${result }
                                    </div>
                                </div>


                                <div class="form-group">
                                    <div class="col-md-12 control">
                                        <div style="border-top: 1px solid#888; padding-top:15px; font-size:85%" >
                                            Don't have an account! <a href="register"> Sign Up Here </a>
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