<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>登录 - 考勤管理系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/common.css">
</head>
<body style="display:flex;align-items:center;justify-content:center;min-height:100vh;background:linear-gradient(135deg,#667eea 0%,#764ba2 100%);">
<div class="card card-sm" style="margin:0;">
    <h2 style="text-align:center;color:var(--dark);margin-bottom:1.5rem;">📋 考勤管理系统</h2>

    <%-- 错误提示 --%>
    <c:if test="${not empty msg}">
        <div class="alert alert-error">${msg}</div>
    </c:if>
    <c:if test="${not empty param.msg}">
        <div class="alert alert-success">${param.msg}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/login" method="post">
        <div class="form-group">
            <label>账号</label>
            <input type="text" name="username" class="form-control" placeholder="请输入账号" required>
        </div>
        <div class="form-group">
            <label>密码</label>
            <input type="password" name="password" class="form-control" placeholder="请输入密码" required>
        </div>
        <div class="form-group">
            <label>角色</label>
            <select name="role" class="form-control">
                <option value="admin">管理员</option>
                <option value="employee">员工</option>
            </select>
        </div>
        <button type="submit" class="btn btn-primary btn-block" style="padding:12px;">登 录</button>
    </form>

    <p style="text-align:center;margin-top:1rem;">
        <a href="${pageContext.request.contextPath}/register.jsp">没有账号？立即注册</a>
    </p>
</div>
</body>
</html>
