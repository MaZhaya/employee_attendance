<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>管理员后台中心</title>
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
        .container {
            width: 80%;
            max-width: 900px;
            margin: 3rem auto;
            background: #fff;
            padding: 2rem;
            border-radius: 10px;
            box-shadow: 0 2px 12px rgba(0,0,0,0.08);
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding-bottom: 1rem;
            border-bottom: 1px solid #eee;
            margin-bottom: 2rem;
        }
        .header h1 {
            color: #2c3e50;
            font-size: 24px;
        }
        .logout-btn {
            background-color: #e74c3c;
            color: #fff;
            padding: 8px 16px;
            border-radius: 6px;
            text-decoration: none;
            font-size: 14px;
            transition: background 0.3s;
        }
        .logout-btn:hover {
            background-color: #c0392b;
        }
        h3 {
            color: #2c3e50;
            margin-bottom: 1.5rem;
            font-size: 20px;
        }
        .menu-list {
            list-style: none;
        }
        .menu-list li {
            margin: 12px 0;
        }
        .menu-list a {
            display: block;
            padding: 16px 20px;
            background-color: #3498db;
            color: #fff;
            text-decoration: none;
            border-radius: 8px;
            font-size: 16px;
            transition: all 0.3s;
        }
        .menu-list a:hover {
            background-color: #2980b9;
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <h1>欢迎您，管理员 ${loginUser.username}</h1>
        <a href="${pageContext.request.contextPath}/logout" class="logout-btn">退出登录</a>
    </div>

    <h3>管理功能菜单</h3>
    <ul class="menu-list">
        <li><a href="${pageContext.request.contextPath}/dept?action=list">部门信息管理</a></li>
        <li><a href="${pageContext.request.contextPath}/emp?action=list">员工信息管理</a></li>
        <li><a href="${pageContext.request.contextPath}/attendance?action=listAll">全员考勤记录</a></li>
        <li><a href="${pageContext.request.contextPath}/leave?action=listAll">员工请假审批</a></li>
    </ul>
</div>
</body>
</html>