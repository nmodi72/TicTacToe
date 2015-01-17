<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<style type="text/css">
.square {
	width: 100px;
	height: 100px;
}

td {
	font-size: 250%;
}

.v {
	border-left: 1px solid #000;
	border-right: 1px solid #000;
}

.h {
	border-top: 1px solid #000;
	border-bottom: 1px solid #000;
}
</style>


<title>Tic Tac Toe</title>
</head>
<body>
	<h1 align="center">
		<FONT FACE="Lucida Calligraphy" color="#000080"
			size="6">Tic Tac Toe</FONT>
			<br/><br/>
			<FONT FACE="Lucida Calligraphy" color="#000080"
			size="3">Welcome.. ${uname}</FONT>
	</h1>
		
<h4  align="center">
		<strong><span style="color: #0000FF;">X</span></strong> - You | <strong><span
			style="color: #FF0000;">O</span></strong> - Computer</h4>
		<h4 align="center">
			<p>Please make your move:</p>
		</h4>
		<h4 align="center">
		<c:if test="${message != null }">${message}</c:if>
		</h4>
	</tt>
	<table border='1' cellspacing="1" cellpadding="25" align="center">
		<c:set var="id" value="0"></c:set>
		<c:forEach begin="1" end="3" var="i">
			<tr>

				<c:forEach begin="1" end="3" var="j">
					<td><c:if test="${message == null }">
							<c:if
								test="${ gamestatus.get(id).isX() == false && gamestatus.get(id).isO() == false  }">
								<a href="tictactoe.html?i=${i}&j=${j}"> _ </a>
							</c:if>
						</c:if> <c:if test="${message != null }">
							<c:if
								test="${ gamestatus.get(id).isX() == false && gamestatus.get(id).isO() == false  }">
								 _
							</c:if>
						</c:if> <c:if
							test="${ gamestatus.get(id).isX() == true && gamestatus.get(id).isO() == false  }">
							<strong><span style="color: #0000FF;">X</span></strong>
						</c:if> <c:if
							test="${ gamestatus.get(id).isX() == false && gamestatus.get(id).isO() == true  }">
							<strong><span style="color: #FF0000;">O</span></strong>
						</c:if></td>

					<c:set var="id" value="${id+1 }"></c:set>
				</c:forEach>

			</tr>
		</c:forEach>


	</table>
<h3 align="center">
	<br />|
	<a href="startGame.html"> Go to New Game</a> | |
	<a href="http://localhost:8080/ttt/gamehome.html">Home Page</a> |
	<c:if test="${pageRec ==1 }">
		<c:if test="${message == null }">
			<a href="saveGame.html"> Save This Game</a>
		</c:if>
	</c:if>
	<br />
	<br />
	<br />|
	<a href="http://localhost:8080/ttt/gamehistory.html">Game History</a> |
	|<a href="resumegame.html"> Resume Game</a>|

	 |<a href="ttt/j_spring_security_logout">Logout</a> |
</h3>
	<br />
</body>
</html>