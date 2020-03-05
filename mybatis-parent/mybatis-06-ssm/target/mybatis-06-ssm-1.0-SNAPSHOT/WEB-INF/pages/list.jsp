<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <title>员工列表</title>
</head>
<body>
    <table>
        <tr>
            <td>id</td>
            <td>lastName</td>
            <td>email</td>
            <td>gender</td>
        </tr>
        <c:forEach items="${allEmps}" var="emp">
            <tr>
                <td>${emp.id}</td>
                <td>${emp.lastName}</td>
                <td>${emp.email}</td>
                <td>${emp.gender}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>