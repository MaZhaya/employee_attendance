<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户注册</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: "Microsoft Yahei", sans-serif;
        }
        body {
            background-color: #f5f7fa;
        }
        .register-box {
            width: 380px;
            margin: 6rem auto;
            background: #fff;
            padding: 2.5rem;
            border-radius: 10px;
            box-shadow: 0 2px 12px rgba(0,0,0,0.08);
        }
        h2 {
            text-align: center;
            color: #2c3e50;
            margin-bottom: 2rem;
        }
        .form-item {
            margin-bottom: 1.2rem;
        }
        label {
            display: block;
            margin-bottom: 0.5rem;
            color: #555;
        }
        input {
            width: 100%;
            padding: 0.9rem;
            border: 1px solid #ddd;
            border-radius: 6px;
            font-size: 1rem;
        }
        .btn-register {
            width: 100%;
            padding: 0.9rem;
            background-color: #27ae60;
            color: #fff;
            border: none;
            border-radius: 6px;
            font-size: 1rem;
            cursor: pointer;
            margin-top: 0.5rem;
        }
        .btn-register:hover {
            background-color: #219653;
        }
        .msg {
            padding: 0.8rem;
            background-color: #f8d7da;
            color: #721c24;
            text-align: center;
            border-radius: 6px;
            margin-bottom: 1rem;
        }
        .back-link {
            display: block;
            text-align: center;
            margin-top: 1.2rem;
            color: #3498db;
            text-decoration: none;
        }
    </style>
</head>
<body>
<div class="register-box">
    <h2>用户注册</h2>

    <% if(request.getAttribute("errorMsg") != null){ %>
    <div class="msg"><%= request.getAttribute("errorMsg") %></div>
    <% } %>

    <form action="${pageContext.request.contextPath}/registerServlet" method="post">
        <div class="form-item">
            <label>用户名</label>
            <input type="text" name="username" placeholder="请输入用户名" required>
        </div>
        <div class="form-item">
            <label>登录密码</label>
            <input type="password" name="password" placeholder="请输入密码" required>
        </div>

        <button type="submit" class="btn-register">立即注册</button>
    </form>

    <a href="${pageContext.request.contextPath}/login.jsp" class="back-link">已有账号？前往登录</a>
</div>
</body>
</html>