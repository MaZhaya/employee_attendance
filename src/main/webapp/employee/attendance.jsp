<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>上下班打卡</title>
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
        .container {
            width: 450px;
            margin: 6rem auto;
            background: #fff;
            padding: 2.5rem;
            border-radius: 10px;
            box-shadow: 0 2px 12px rgba(0,0,0,0.08);
            text-align: center;
        }
        h2 {
            color: #2c3e50;
            margin-bottom: 2rem;
        }
        .tip {
            margin-bottom: 2rem;
            font-size: 16px;
            color: #666;
        }
        .btn-box {
            display: flex;
            justify-content: center;
            gap: 20px;
            margin-bottom: 2rem;
        }
        .btn {
            padding: 12px 30px;
            border: none;
            border-radius: 6px;
            font-size: 16px;
            cursor: pointer;
            color: #fff;
            text-decoration: none;
        }
        .btn-in {
            background-color: #27ae60;
        }
        .btn-out {
            background-color: #e74c3c;
        }
        .msg {
            padding: 1rem;
            background-color: #d4edda;
            color: #155724;
            border-radius: 6px;
            margin-bottom: 1rem;
        }
        .back {
            color: #3498db;
            text-decoration: none;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>员工上下班打卡</h2>
    <div class="tip">当前登录用户：${loginUser.username}</div>

    <% if(request.getAttribute("msg") != null){ %>
    <div class="msg">${msg}</div>
    <% } %>

    <div class="btn-box">
        <form action="${pageContext.request.contextPath}/attendance" method="post">
            <input type="hidden" name="action" value="checkIn">
            <button class="btn btn-in">上班打卡</button>
        </form>
        <form action="${pageContext.request.contextPath}/attendance" method="post">
            <input type="hidden" name="action" value="checkOut">
            <button class="btn btn-out">下班签退</button>
        </form>
    </div>

    <a href="${pageContext.request.contextPath}/employee/index.jsp" class="back">← 返回员工主页</a>
</div>
</body>
</html>