<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.JavaBeans"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Agenda de Contatos</title>
<link rel="icon" href="images/favicon.png">
<link rel="stylesheet" href="style/style.css">
</head>
<body>
	<h1>Editar contato</h1>
	<div class="adicionar">
	<form name="frmContato" action="update">
		<table>
			<tr>
				<td><input id="caixa3" type="text" name="idcon" value="<%out.print(request.getAttribute("idcon"));%>" readonly></td>
			</tr>
			<tr>
				<td><input class="caixa1" type="text" name="nome" value="<%out.print(request.getAttribute("nome"));%>"></td>
			</tr>
			<tr>
				<td><input class="caixa2"type="text" name="fone" value="<%out.print(request.getAttribute("fone"));%>"></td>
			</tr>
			<tr>
				<td><input class="caixa1" type="text" name="email" value="<%out.print(request.getAttribute("email"));%>"></td>
			</tr>
		</table>
		<input class="botao1" type="button" value="Salvar" onclick="validar()">
	</form>
	<script type="text/javascript" src="js/validador.js"></script>
	</div>
</body>
</html>