<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>企业员工考勤管理系统-登录</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: "Microsoft Yahei", sans-serif;
        }
        body {
            background-color: #f5f7fa;
            color: #333;
            line-height: 1.6;
        }
        .login-box {
            width: 350px;
            margin: 8rem auto;
            background: #fff;
            padding: 2rem;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.08);
        }
        h2 {
            text-align: center;
            color: #2c3e50;
            margin-bottom: 1.5rem;
        }
        .form-item {
            margin-bottom: 1rem;
        }
        label {
            display: block;
            margin-bottom: 0.5rem;
            color: #2c3e50;
        }
        input, select {
            width: 100%;
            padding: 0.8rem;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 1rem;
        }
        .btn {
            width: 100%;
            padding: 0.8rem;
            background-color: #3498db;
            color: #fff;
            border: none;
            border-radius: 4px;
            font-size: 1rem;
            cursor: pointer;
        }
        .btn:hover {
            background-color: #2980b9;
        }
        .msg {
            padding: 1rem;
            border-radius: 4px;
            margin-bottom: 1rem;
            background-color: #f8d7da;
            color: #721c24;
            text-align: center;
        }
        .reg-link {
            display: block;
            text-align: center;
            margin-top: 1rem;
            color: #27ae60;
            text-decoration: none;
        }
        .reg-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="login-box">
    <h2>考勤管理系统</h2>
    <% if (request.getAttribute("msg") != null) { %>
    <div class="msg"><%= request.getAttribute("msg") %></div>
    <% } %>

    <form action="${pageContext.request.contextPath}/login" method="post">
        <div class="form-item">
            <label>账号：</label>
            <input type="text" name="username" required>
        </div>
        <div class="form-item">
            <label>密码：</label>
            <input type="password" name="password" required>
        </div>
        <div class="form-item">
            <label>角色：</label>
            <select name="role">
                <option value="admin">管理员</option>
                <option value="employee">员工</option>
            </select>
        </div>
        <button type="submit" class="btn">登录</button>
    </form>
    <!-- 注册跳转按钮 -->
    <a href="${pageContext.request.contextPath}/register.jsp" class="reg-link">没有账号？立即注册</a>
</div>
</body>
</html>