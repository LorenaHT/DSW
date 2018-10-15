<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page session="true" import="java.util.*, modelo.Intento"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Adivinar numero</title>
</head>
<body>
	<h3>¡Intenta adivinar!</h3>
	<p>
		Ingresa un numero que sea entre
		<%=session.getAttribute("min")%>
		y
		<%=session.getAttribute("max")%></p>
	<form method="POST" action="logica">
		<input name="numero" type="text" />
		<%
			if ((Integer) session.getAttribute("numero") != (Integer) session.getAttribute("aleatorio")) {
		%><input
			id="jugar" name="jugar" type="submit" value="¡PROBAR SUERTE!" />
			<% } else { %> Has tardado	<%= session.getAttribute("tiempoTotal") %> segundos en acertar
			<%} if (request.getAttribute("mensaje") != null) {
		%>

		<%=request.getAttribute("mensaje")%>

		<%
			}
		%>

	</form>
	<%
		if (session.getAttribute("numero") != null) {
	%>
	<table>
		<tr>
			<th>Orden</th>
			<th>FechaHora</th>
			<th>NumeroJugado</th>
			<th>Mensaje</th>
		</tr>
		<%
			ArrayList<Intento> listaIntentos = (ArrayList<Intento>) session.getAttribute("listaIntentos");

				for (int i = 0; i < listaIntentos.size(); i++) {
		%>
		<tr>
			<td><%=listaIntentos.get(i).getOrden()%></td>
			<td><%=listaIntentos.get(i).getFechaHora()%></td>
			<td><%=listaIntentos.get(i).getNumeroJugado()%></td>
			<td><%=listaIntentos.get(i).getMensaje()%></td>
		</tr>
		<%
			}
		%>
	
</table>
	<%
		}
	%>
	
	<form method="POST" action="logica">
	<input name="jugar" type="submit" value="REGRESAR"/>
	</form>
</body>
</html>
