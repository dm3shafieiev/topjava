<%--
  Created by IntelliJ IDEA.
  User: dm3drummer
  Date: 09.11.2017
  Time: 20:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<html>
<head>
    <title>Meal calories Application</title>
    <style>
        table {
            width: 100%;
        }

        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
        }

        th, td {
            padding: 5px;
            text-align: left;
        }

        table th {
            background-color: black;
            color: white;
        }
    </style>
</head>
<body>

<table>
    <tr>
        <th>Description</th>
        <th>Date</th>
        <th>Calories</th>
    </tr>
    <c:forEach items="${mealsWithExceed}" var="meal">
        <tr  <c:if test="${meal.exceed}"> bgcolor="red"</c:if> >
            <javatime:format value="${meal.dateTime}" pattern="yyyy-MM-dd HH:MM:SS" var="parsedDate"/>
            <td><c:out value="${meal.description}"/></td>
            <td><c:out value="${parsedDate}"/></td>
            <td><c:out value="${meal.calories}"/></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
