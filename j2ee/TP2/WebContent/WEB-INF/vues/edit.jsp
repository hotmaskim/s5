<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="../blueprint/screen.css" type="text/css"
	media="screen, projection">
<link rel="stylesheet" href="../blueprint/print.css" type="text/css"
	media="print">
<!--[if lt IE 8]><link rel="stylesheet" href="../blueprint/ie.css" type="text/css" media="screen, projection"><![endif]-->
<link rel="stylesheet" href="../blueprint/plugins/fancy-type/screen.css"
	type="text/css" media="screen, projection">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Formulaire</title>
</head>
<body>
<div class="container">

<h1 class="alt">Ajout/Modification d'un élève</h1>

<c:if test="${!empty erreurs[0]}">
	<div class="error">${erreurs[0]}</div>
</c:if> <c:if test="${!empty erreurs[1]}">
	<div class="error">${erreurs[1]}</div>
</c:if>
<form action="validate" method="post">

<table>
	<tr>
		<td>Id</td>
		<c:choose>
			<c:when test="${empty eleve}">
				<c:set var="id" value="-1" />
			</c:when>
			<c:otherwise>
				<c:set var="id" value="${eleve.id}" />
			</c:otherwise>
		</c:choose>
		<td>${id}</td>
		<input type="hidden" name="id" value="${id}" />
	</tr>
	<tr>
		<td>Version</td>
		<c:choose>
			<c:when test="${empty eleve}">
				<c:set var="version" value="1" />
			</c:when>
			<c:otherwise>
				<c:set var="version" value="${eleve.version}" />
			</c:otherwise>
		</c:choose>
		<td>${version}</td>
		<input type="hidden" name="version" value="${version}" />
	</tr>
	<tr>
		<td>Prénom</td>
		<td><input type="text" name="prenom" value="${eleve.prenom}" /></td>
		<td style="color:red;">${erreurs[2]}</td>
	</tr>
	<tr>
		<td>Nom</td>
		<td><input type="text" name="nom" value="${eleve.nom}" /></td>
		<td style="color:red;">${erreurs[3]}</td>
	</tr>
	<tr>
		<td>Date de naissance (JJ/MM/AAAA)</td>
		<td><input type="text" name="dateNaissance"
			value="${eleve.dateNaissanceString}" /></td>
		<td style="color:red;">${erreurs[4]}</td>
	</tr>
	<tr>
		<td>Redoublant</td>
		<td><input type="radio" value="true" name="redoublant" checked />
		Oui <input type="radio" value="false" name="redoublant"
			<c:if test="${eleve.redoublant == false}">checked</c:if> /> Non</td>
	</tr>
	<tr>
		<td>Année</td>
		<td><input type="text" name="annee" value="${eleve.annee}" /></td>
		<td style="color:red;">${erreurs[5]}</td>
	</tr>
	<tr>
		<td>Filière</td>
		<td><select name="filiere">
			<option value="ELEC"
				<c:if test="${eleve.filiere == 'ELEC'}">selected</c:if>>ELEC</option>
			<option value="INFO"
				<c:if test="${eleve.filiere == 'INFO'}">selected</c:if>>INFO</option>
			<option value="MCF"
				<c:if test="${eleve.filiere == 'MCF'}">selected</c:if>>MCF</option>
		</select></td>
		<td style="color:red;">${erreurs[6]}</td>
	</tr>
</table>
<input type="submit" value="Valider" /> <a href="list">Annuler</a></form>
</div>
</body>
</html>