<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Adivinar numero</title>
	</head>
	<body>
	<h1>Adivinar el número</h1>
		<form method="POST" action="logica">
			Introduce intervalo 
			<input name="min" type="text" /> 
			- 
			<input name="max" type="text"> 
			<br/><br/> 
			<input name="jugar" type="submit" value="¡JUGAR!" />
		</form>
		<% if (request.getAttribute("mensaje") != null) {%>
		<p> <%=request.getAttribute("mensaje") %> </p>
		<% } %>
	</body>
</html>