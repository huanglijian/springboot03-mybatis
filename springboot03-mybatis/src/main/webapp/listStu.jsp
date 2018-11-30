<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

</head>

<body>
<%--第一此更新项目到GitHub--%>

<center>
    <table align='center' border='1' cellspacing='0'>
        <tr>
            <td>id</td>
            <td>姓名</td>
            <td>年龄</td>
            <td>成绩</td>
        </tr>
        <c:forEach items="${stus}" var="stu">
        <tr>
            <td>${stu.id}</td>
            <td>${stu.name}</td>
            <td>${stu.age}</td>
            <td>${stu.score}</td>
        </tr>
        </c:forEach>
    </table>

</center>
</body>
</html>
