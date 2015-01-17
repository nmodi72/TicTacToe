<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Resume Game</title>
</head>
<body>
	Welcome your Saved Games are :
	<br />
	<br />
	<br />
	<c:forEach items="${allSavedGame}" var="game" varStatus="i">

		<a href="resume.html?id=${game.getGameid()}">${game.getSavedAt() }</a>
		<br />
		<br />
	</c:forEach>
	<br />
	<br />
	<br /> |
	<a href="http://localhost:8080/ttt/gamehome.html">Home Page</a> |
	<a href="ttt/j_spring_security_logout">Logout</a>|
</body>
</html>