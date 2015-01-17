<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Join Game</title>
<script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
<script type="text/javascript">
	$(function() {
		$.ajax({
			url : "hostgame.json",
			success : function(data) {
				$("#hostlist").empty();
				data.usernames.forEach(function(username) {
					$("#hostlist").append("<li>" + username + "</li>");
				});
			},
		});
		update();
	});

	$(function() {
		$.ajax({
			url : "joingame.json",
			success : function(data) {
				$("#joinedlist").empty();
				data.usernames.forEach(function(username) {
					$("#joinedlist").append("<li>" + username + "</li>");
				});
			},
		});

		joinupdate();
	});

	function update() {
		$
				.ajax({
					url : "hostgame.deferred.json",
					success : function(data) {
						$("#hostlist").empty();
						data
								.forEach(function(username) {
									$("#hostlist").append(
											"<li>" + username + "</li>");
									window.location
											.replace("http://localhost:8080/ttt/play2game.html");
								});
						update();
					},
				});

	}

	function joinupdate() {
		$.ajax({
			url : "joingame.deferred.json",
			success : function(data) {
				$("#joinedlist").empty();
				data.forEach(function(username) {
					$("#joinedlist").append("<li>" + username + "</li>");
				});
				joinupdate();
			},
		});
	}

	/* 	function info() {
	 $.ajax({
	 url : "infogame.deferred.json",
	 success : function(data) {
	 $("#infolist").empty();
	 data.forEach(function(username) {
	 $("#joinedlist").append("<li>" + username + "</li>");
	 });
	 joinupdate();
	 },
	 });
	 } */
</script>
</head>
<body>
	<h3>Waiting for other player to host Game</h3>
	<ul id="hostlist">
	</ul>

	<h3>Here are the Joining Players:</h3>
	<ul id="joinedlist">
	</ul>
	<a href="leavejoinedgame.html">  Go HomePage </a> |
	<a href="ttt/j_spring_security_logout">Logout</a>
</body>
</html>
