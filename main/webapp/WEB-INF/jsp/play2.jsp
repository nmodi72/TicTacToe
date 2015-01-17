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
<title>Game</title>
<script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
<script type="text/javascript">
	$(function() {
		$.ajax({
			url : "gameagainstplayer.json",
			success : function(data) {
				$("#gamelist").empty();
				data.playstatus.forEach(function(playstatus) {
					$("#gamelist").append("<li>" + playstatus + "</li>");
				});
			},
		});
		update();
	});

	function update() {
		$
				.ajax({
					url : "gameagainstplayer.deferred.json",
					success : function(data) {
						$("#gamelist").empty();
						data
								.forEach(function(playstatus) {
									$("#gamelist").append(
											"<li>" + playstatus + "</li>");
									window.location
											.replace("http://localhost:8080/ttt/gameagainstplayer.html?x=0");
								});
						update();
					},
				});

	}
</script>
</head>
<body>
	${msg}
<h1 align="center">
		<FONT FACE="Lucida Calligraphy" color="#000080"
			size="6">Tic Tac Toe</FONT>
	</h1>
	<br />
	<br />
	<%-- <c:forEach items="${xpo}" var="x">${x}</c:forEach>
	<c:forEach items="${opo}" var="x">${x}</c:forEach>
 --%>
	<strong><span style="color: #0000FF;">X</span></strong> -
	${currenthoster} |
	<strong><span style="color: #FF0000;">O</span></strong> -
	${currentjoiner}
	<h4>
		<p>Please make your move:</p>
	</h4>
	<c:if test="${wincondition != null }">${wincondition}</c:if>
	<br />
	<br />
	<table border='1' cellspacing="1" cellpadding="25" align="center">
		<tr>
			<c:forEach begin="1" end="3" var="id">
				<td><c:choose>
						<c:when test="${xpo.contains(id) || opo.contains(id)}">
							<c:if test="${xpo.contains(id)}">
								<strong><span style="color: #0000FF;">X</span></strong>
							</c:if>
							<c:if test="${opo.contains(id)}">
								<strong><span style="color: #FF0000;">O</span></strong>
							</c:if>

						</c:when>
						<c:otherwise>

							<c:choose>
								<c:when test="${wincondition == null  }">
									<c:if test="${currentplayer.equals('me')}">
										<a href="gameagainstplayer.html?x=${id}"> _ </a>
									</c:if>
									<c:if test="${currentplayer.equals('you') }">
								_
							</c:if>
								</c:when>
								<c:otherwise>
								_
							</c:otherwise>

							</c:choose>


						</c:otherwise>
					</c:choose></td>
			</c:forEach>
		</tr>
		<tr>
			<c:forEach begin="4" end="6" var="id">
				<td><c:choose>
						<c:when test="${xpo.contains(id) || opo.contains(id)}">
							<c:if test="${xpo.contains(id)}">
								<strong><span style="color: #0000FF;">X</span></strong>
							</c:if>
							<c:if test="${opo.contains(id)}">
								<strong><span style="color: #FF0000;">O</span></strong>
							</c:if>

						</c:when>
						<c:otherwise>

							<c:choose>
								<c:when test="${wincondition == null  }">
									<c:if test="${currentplayer.equals('me')}">
										<a href="gameagainstplayer.html?x=${id}"> _ </a>
									</c:if>
									<c:if test="${currentplayer.equals('you') }">
								_
							</c:if>
								</c:when>
								<c:otherwise>
								_
							</c:otherwise>

							</c:choose>


						</c:otherwise>
					</c:choose></td>
			</c:forEach>
		</tr>
		<tr>
			<c:forEach begin="7" end="9" var="id">
				<td><c:choose>
						<c:when test="${xpo.contains(id) || opo.contains(id)}">
							<c:if test="${xpo.contains(id)}">
								<strong><span style="color: #0000FF;">X</span></strong>
							</c:if>
							<c:if test="${opo.contains(id)}">
								<strong><span style="color: #FF0000;">O</span></strong>
							</c:if>

						</c:when>
						<c:otherwise>

							<c:choose>
								<c:when test="${wincondition == null  }">
									<c:if test="${currentplayer.equals('me')}">
										<a href="gameagainstplayer.html?x=${id}"> _ </a>
									</c:if>
									<c:if test="${currentplayer.equals('you') }">
								_
							</c:if>
								</c:when>
								<c:otherwise>
								_
							</c:otherwise>

							</c:choose>


						</c:otherwise>
					</c:choose></td>
			</c:forEach>
		</tr>




		<%-- 

		<c:forEach begin="1" end="3" var="j">
			<td><c:if test="${wincondition == null }">

				</c:if> <c:if test="${wincondition != null }">
					<c:if
						test="${ twoplayergamestatus.get(id).isX() == false && twoplayergamestatus.get(id).isO() == false  }">
								 _
							</c:if>
				</c:if> <c:if
					test="${ twoplayergamestatus.get(id).isX() == true && twoplayergamestatus.get(id).isO() == false  }">
					
				</c:if> <c:if
					test="${ twoplayergamestatus.get(id).isX() == false && twoplayergamestatus.get(id).isO() == true  }">
					<strong><span style="color: #FF0000;">O</span></strong>
				</c:if></td>

			<c:set var="id" value="${id+1 }"></c:set>
		</tr>
		</c:forEach>
 --%>

	</table>
	
	<a href="redirectpage.html">GO TO HOME</a>
</body>
</html>