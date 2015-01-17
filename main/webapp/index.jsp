
<html>
<head>
<title>Tic Tac Toe</title>
</head>
<body>


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
</style>
	<div id="tableContainer-1">
		<div id="tableContainer-2">
			<table id="myTable" border="0">
				<tr>
					<td colspan='2'>
						<h1 align="center">
							<FONT FACE="Lucida Calligraphy" color="#FAF0E6" size="25">Tic Tac Toe</FONT>
						</h1>
					</td>
				</tr>
				<tr>

					<td><a href="http://localhost:8080/ttt/login.html"  target="_blank">| Go to
							Log in |</a></td>
					<td><a href="http://localhost:8080/ttt/startGuestGame.html"  target="_blank">|
							Guest Play |</a></td>
				</tr>
			</table>
		</div>
	</div>
	
</body>
</html>
