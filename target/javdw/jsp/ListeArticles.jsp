<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <!--  on doit dire au servlet container d'inclure cette lib ou il y a le tag forEach
     il faudra le prfixé avec un 'c'  -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>List Articles</title>
</head>
<body>
<!-- -- Expression language -->
<!-- ici c:foreach est un indicateur de boucle -->
<c:forEach items="${ requestScope.articles}" var="article">
<!-- on affiche le title pour chaque iteration on appelle le getter de l'objet -->
<li> <a href = "ShowArticle?art=${article.id}">${article.title}</a> </li>
</c:forEach>
</body>
</html>