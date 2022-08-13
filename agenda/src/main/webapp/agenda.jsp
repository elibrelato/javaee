<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.JavaBeans"%>
<%@ page import="java.util.ArrayList"%>

<%
    @SuppressWarnings("unchecked")
	ArrayList<JavaBeans> lista = (ArrayList<JavaBeans>) request.getAttribute("contatos");
%>
	<%--
		System.out.println("Lista de contatos (agenda.jsp):");
		for (int i = 0; i < lista.size(); i++) { 
			System.out.println("[Id: " + lista.get(i).getIdcon()
            		+ ", Nome: " + lista.get(i).getNome()
		            + ", Telefone: " + lista.get(i).getFone()
		            + ", E-mail: " + lista.get(i).getEmail() + "]<br>");
        }
     --%>
<%
	
%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<title>Agenda de Contatos</title>
<link rel="icon" href="images/favicon.png">
<link rel="stylesheet" href="style/style.css">
</head>
<body>
	<h1>Agenda de Contatos</h1>
	<a href="novo.html" class="botao1">Novo contato</a>
	<a href="report" class="botao2">Relatório</a>
	<table id="tabela">
		<thead>
			<tr>
				<th>Id</th>
				<th>Nome</th>
				<th>Telefone</th>
				<th>E-mail</th>
				<th>Opções</th>
			</tr>
		</thead>
		<tbody>
			<%for (int i = 0; i < lista.size(); i++) { %>
				<tr>
					<td><%=lista.get(i).getIdcon()%></td>
					<td><%=lista.get(i).getNome()%></td>
					<td><%=lista.get(i).getFone()%></td>
					<td><%=lista.get(i).getEmail()%></td>
					<td>
						<a href="findById?idcon=<%=lista.get(i).getIdcon()%>" class="botao1">Editar</a>
						<a href="javascript: confirmar(<%=lista.get(i).getIdcon()%>)" class="botao2">Excluir</a>
					</td>
				</tr>
			<%} %>
		</tbody>
	</table>
	<script src="js/confirmador.js"></script>
</body>
</html>