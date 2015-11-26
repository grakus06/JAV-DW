<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>BlogAuTop</title>
</head>
<body>
<h1>${ requestScope.article.title}</h1>
<p>Article écrit par: ${ requestScope.article.authorId}</p>
<p>Article publié le : ${ requestScope.article.createdOn}</p>
${ requestScope.article.content}
<p>
<p> <a href = "ResourceServlet">"Back to the List of Articles"</a> </p>
</body>
</html>