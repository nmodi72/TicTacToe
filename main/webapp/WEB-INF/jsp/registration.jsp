<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registation</title>


<style>
	
	/* unvisited link */
a:link {
    color: #FFFAF0;
}


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
	
}

#tableContainer-2 {
	vertical-align: middle;
	display: table-cell;
	height: 100%;
	
}

#myTable {
	margin: 0 auto;
}
td,h3{
color:white;
}
input{
width:100;}
</style>



</head>
<body>
	<br />
	<h3 align="center">Please Register your details to create account..</h3>
	<form:form modelAttribute="userdetail">
		<table align="center">
			<tr>
				<td>Username:</td>
				<td><form:input path="username" /> <br /></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><form:input path="password" /> <br /></td>
			<tr>
				<td>Email:</td>
				<td><form:input path="email" /> <br /></td>
			</tr>
			
			<tr colspan="2" align="center">
				<td colspan="2" ><input width="100" type="submit" name="add"
					value="add" /></td>
		</table>
	</form:form>



</body>
</html>