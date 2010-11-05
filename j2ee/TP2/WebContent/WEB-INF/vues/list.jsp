<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Liste des élèves</title>
</head>
<body>
	<h1>Liste des élèves</h1>
	<table style='border:solid 1px; text-align:center;' >
		<tr>
			<th>ID</th><th>Version</th><th>Prénom</th><th>Nom</th><th>Date de naissance</th><th>Redoublant</th><th>Année</th><th>Filière</th>
		</tr>
		<c:forEach var="e" items="${requestScope['eleves']}">
			<tr>
				<td>${e.id}</td>
				<td>${e.version}</td>
				<td>${e.prenom}</td>
				<td>${e.nom}</td>
				<td>${e.dateNaissance}</td>
				<td>${e.redoublant}</td>
				<td>${e.annee}</td>
				<td>${e.filiere}</td>
				<td><a href='<c:url value="/do/edit?id=${e.id}"/>'>Modifier</a></td>
				<td><a href='<c:url value="/do/delete?id=${e.id}"/>'>Supprimer</a></td>
			</tr>
		</c:forEach>
	</table>
	
	<a href='<c:url value="/do/edit?id=-1"/>'>Ajout</a>
	
</body>
</html>