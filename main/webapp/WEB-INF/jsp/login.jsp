
<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<html>
<head>
<title>Login Page</title>


<style>

/* unvisited link */
a:link {
	color: #FFFAF0;
}

/* visited link */
a:visited {
	color: #E6E6FA;
}

/* mouse over link */
a:hover {
	color: #F0FFFF;
}

/* selected link */
a:active {
	color: #FFF0F5;
}

html,body {
	height: 100%;
	background-image: url("image/1.jpg");
}

#tableContainer-1 {
	height: 100%;
	width: 100%;
	display: table;

	/*
	background-image: url("http://fc08.deviantart.net/fs70/i/2011/109/8/8/background_m3_login_for_m1_by_andreascy-d3ecbzg.jpg");
	 background-image: url("http://nitv.hq.nato.int/library/layout/images/nato/background.jpg"); */
}

#tableContainer-2 {
	vertical-align: middle;
	display: table-cell;
	height: 100%;
}

#myTable {
	margin: 0 auto;
}

.errorblock {
	color: #ff0000;
	background-color: #ffEEEE;
	border: 3px solid #ff0000;
	padding: 8px;
	margin: 16px;
}
</style>
</head>
<body onload='document.f.j_username.focus();'>
	<div id="tableContainer-1">
		<div id="tableContainer-2">

			<table id="myTable" border="0">
				<tr>

					<h1 align="center">
						<FONT FACE="Lucida Calligraphy" color="#FAF0E6" size="25">Login</FONT>
					</h1>
				</tr>
				<tr>

					<c:if test="${not empty error}">
						<div class="errorblock">
							${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</div>
					</c:if>

				</tr>
				<tr>
					<form name='f' action="<c:url value='j_spring_security_check' />"
						method='POST'>

						<table id="myTable" border="0">
							<tr>
								<td>Username:</td>
								<td><input type='text' name='j_username' value=''></td>
							</tr>
							<tr>
								<td>Password:</td>
								<td><input type='password' name='j_password' /></td>
							</tr>
							<tr>
								<td><input name="submit" type="submit" value="submit" /></td>
								<td colspan='2'><input name="Cancel" type="reset" /></td>
							</tr>
						</table>

					</form>
				</tr>
				<tr align="center">
					<h3 align="center">
						<a href="http://localhost:8080/ttt/registration.html"
							target="_blank">Click to Register</a>
					</h3>




				</tr>
			</table>
		</div>
	</div>

</body>
</html>