<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>出错了 - 考勤管理系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/common.css">
</head>
<body style="display:flex;align-items:center;justify-content:center;min-height:100vh;">
<div class="card card-sm" style="text-align:center;">
    <h2 style="color:var(--danger);margin-bottom:1rem;">⚠️ 页面出错了</h2>
    <p style="color:var(--text-light);margin-bottom:1rem;">
        系统遇到了一个错误，请稍后再试。
    </p>
    <a href="${pageContext.request.contextPath}/login.jsp" class="btn btn-primary">返回登录页</a>
</div>
</body>
</html>
