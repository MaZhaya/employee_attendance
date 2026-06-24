<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>所有考勤</title>
    <style>table{width:900px;margin:50px auto;border-collapse:collapse}td,th{border:1px solid #ccc;padding:8px}</style>
</head>
<body>
<h2 style="text-align:center">所有员工考勤</h2>
<table>
    <tr>
        <th>ID</th><th>员工ID</th><th>日期</th><th>上班</th><th>下班</th><th>状态</th>
    </tr>
    <c:forEach items="${attList}" var="a">
        <tr>
            <td>${a.id}</td>
            <td>${a.empId}</td>
            <td>${a.checkDate}</td>
            <td>${a.checkIn}</td>
            <td>${a.checkOut}</td>
            <td>${a.status}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>