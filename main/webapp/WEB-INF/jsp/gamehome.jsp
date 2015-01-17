<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
td {
	font-size: 20px;
}

th {
	font-size: 25px;
}
</style>


</head>
<body>


	<h1 align="center">
		<FONT FACE="Lucida Calligraphy" color="#4B0082" size="17">Tic
			Tac Toe</FONT><br /> <br /> <FONT FACE="Lucida Calligraphy" color="#000080"
			size="6">Welcome.. ${uname }</FONT>
	</h1>

	<table align="center" border="2">

		<tr>
			<th>1 Player Game</th>
			<th>2 Player Games</th>

		</tr>
		<tr>
			<td><a href="startGame.html"> Go to New Game</a></td>
			<td><a href="hostgame.html"> Host 2 player game</a></td>

		</tr>
		<tr>
			<td></td>
			<td><a href="joingame.html"> Join 2 player game</a></td>

		</tr>


		<tr>
			<td><a href="http://localhost:8080/ttt/gamehistory.html">Game
					History</a></td>
			<td><a href="resumegame.html"> Resume Game</a></td>

		</tr>
	</table>

	<br />
	<br />
	<h3 align="center">
		<a href="ttt/j_spring_security_logout">Logout</a>
	</h3>
	<br />


</body>
</html>