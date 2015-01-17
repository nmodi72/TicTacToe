<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Game History</title>
</head>
<body>



	<h3>- Game History -</h3>
	<br />

	<table border="1">
		<th>Detail</th>
		<th>Result</th>
		<tr>
			<td>Number of Games Completed :</td>
			<td align="center">${allgames}</td>
		</tr>
		<tr>
			<td>Number of 1- Player Games Completed:</td>
			<td align="center">${playergamesby1}</td>
		</tr>
		<tr>
			<td>Number of 2- Player Games Completed:</td>
			<td align="center">${playergamesby2}</td>
		</tr>

		<tr>
			<td>Winrate of Completed Games against AI:</td>
			<td align="center"> ${winrateagainstAI}</td>
		</tr>

		<tr>
			<td>Winrate of Completed Games against HUMAN:</td>
			<td align="center"> ${winrateagainstHUMAN}</td>
		</tr>


	</table>
	<br />
	<table border="1">
		<th>Sr. No.</th>
		<th>Opponent Name</th>
		<th>Duration</th>
		<th>Winner Name / Result</th>

		<c:forEach items="${allHistoryData}" var="data" varStatus="i">
			<tr>
				<td align="right">${i.index+1 }</td>
				<td align="right">${ data.opponent }</td>
				<td align="right">${ data.difference }</td>
				<td align="center">${ data.result }</td>
			</tr>
		</c:forEach>

	</table>
	<br />
	<br />
	<br /> |
	<a href="http://localhost:8080/ttt/gamehome.html">Home Page</a> |
	<a href="ttt/j_spring_security_logout">Logout</a>|
</body>
</html>