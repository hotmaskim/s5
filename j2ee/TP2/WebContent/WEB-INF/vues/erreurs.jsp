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
<title>Erreurs</title>
</head>
<body>

	<div class="container">
		<h1 class="alt">Erreurs</h1>

		<div class="error">
			${exc}
		</div>

		<a href="list">Retour à la liste</a>
	</div>

</body>
</html>