<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tic Tac Toe : Guest Mode</title>
</head>
<body>
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


	

		<h2 align="center">
			<FONT FACE="Lucida Calligraphy">Tic Tac Toe</FONT>
		</h2>

<br/>
	<h2 align="center">
		<strong  ><span style="color: #0000FF;">X</span></strong> - You | <strong><span
			style="color: #FF0000;">O</span></strong> - Computer
		<h4 align="center">
			<p>Please make your move:</p>
		</h4>
		<c:if test="${message != null }">${message}</c:if>
	<br/>	


	<div id="board">
		<table border='1' cellspacing="1" cellpadding="25" align="center">
			<c:set var="id" value="0"></c:set>
			<c:forEach begin="1" end="3" var="i">
				<tr>

					<c:forEach begin="1" end="3" var="j">
						<td><c:if test="${message == null }">
								<c:if
									test="${ gamestatus.get(id).isX() == false && gamestatus.get(id).isO() == false  }">
									<a href="guesttictactoe.html?i=${i}&j=${j}"> _ </a>
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
	</div>
	
	<h3 align="center">
	<a href="startGuestGame.html"> Go to New Game</a> |
	<a href="login.html"> Play as Member</a>
</h2>

</h3>
</body>
</html>