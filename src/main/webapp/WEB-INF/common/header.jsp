<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${param.pageTitle} - 考勤管理系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/common.css">
</head>
<body>

<%-- 顶部导航栏 --%>
<div class="topbar">
    <span class="logo">📋 考勤管理系统</span>
    <span class="user-info">
        👤 ${sessionScope.loginUser.username}
        <c:choose>
            <c:when test="${sessionScope.loginUser.role == 'admin'}">（管理员）</c:when>
            <c:otherwise>（员工）</c:otherwise>
        </c:choose>
        <a href="${pageContext.request.contextPath}/logout" class="logout-btn">退出登录</a>
    </span>
</div>

<div class="main">
    <%-- 管理员侧边栏 --%>
    <c:if test="${sessionScope.loginUser.role == 'admin'}">
    <div class="sidebar">
        <div class="menu-title">管理菜单</div>
        <a href="${pageContext.request.contextPath}/admin/index.jsp">🏠 后台首页</a>
        <a href="${pageContext.request.contextPath}/dept?action=list">🏢 部门管理</a>
        <a href="${pageContext.request.contextPath}/emp?action=list">👥 员工管理</a>
        <a href="${pageContext.request.contextPath}/attendance?action=listAll">📊 考勤记录</a>
        <a href="${pageContext.request.contextPath}/leave?action=listAll">📝 请假审批</a>
    </div>
    </c:if>

    <div class="content">
