<%--
  Created by IntelliJ IDEA.
  User: dm3drummer
  Date: 10.11.2017
  Time: 0:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/meals?action=${param.action}" method="post">
    <p>Add meal</p>
    <p>
        <input hidden type="number" name="id" value="${param.id}">
        <input type="text" name="description">Description<Br>
        <input type="number" name="calories">Calories<Br>
        <input type="datetime" name="dateTime">Date</p>
    <p>
        <input type="submit"></p>
</form>
</body>
</html>
