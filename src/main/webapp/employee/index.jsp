<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>员工中心</title>
    <!-- 上面的全局样式 -->
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
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.08);
        }
        h1, h2, h3 {
            color: #2c3e50;
            margin-bottom: 1.5rem;
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 2rem;
            padding-bottom: 1rem;
            border-bottom: 1px solid #eee;
        }
        .logout-btn {
            color: #fff;
            background-color: #e74c3c;
            padding: 0.5rem 1rem;
            border-radius: 4px;
            text-decoration: none;
            font-size: 0.9rem;
        }
        .logout-btn:hover {
            background-color: #c0392b;
        }
        .menu-list {
            list-style: none;
        }
        .menu-list li {
            margin: 1rem 0;
        }
        .menu-list a {
            display: block;
            padding: 1rem;
            background-color: #3498db;
            color: #fff;
            text-decoration: none;
            border-radius: 4px;
            transition: background 0.3s;
        }
        .menu-list a:hover {
            background-color: #2980b9;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <h1>欢迎您，员工 ${loginUser.username}</h1>
        <a href="${pageContext.request.contextPath}/logout" class="logout-btn">退出登录</a>
    </div>

    <h3>功能菜单</h3>
    <ul class="menu-list">
        <li><a href="${pageContext.request.contextPath}/attendance">上下班打卡</a></li>
        <li><a href="${pageContext.request.contextPath}/attendance?action=listMyAttendance">我的考勤记录</a></li>
        <li><a href="${pageContext.request.contextPath}/leave?action=toApply">请假申请</a></li>
        <li><a href="${pageContext.request.contextPath}/leave?action=listMyLeave">我的请假记录</a></li>
    </ul>
</div>
</body>
</html>