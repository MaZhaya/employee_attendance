<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>请假申请</title>
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
            width: 500px;
            margin: 4rem auto;
            background: #fff;
            padding: 2rem;
            border-radius: 10px;
            box-shadow: 0 2px 12px rgba(0,0,0,0.08);
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
            color: #555;
        }
        input,select,textarea {
            width: 100%;
            padding: 0.8rem;
            border: 1px solid #ddd;
            border-radius: 6px;
        }
        .btn {
            width: 100%;
            padding: 0.9rem;
            background-color: #3498db;
            color: #fff;
            border: none;
            border-radius: 6px;
            font-size: 16px;
        }
        .back {
            display: inline-block;
            margin-top: 1rem;
            color: #3498db;
            text-decoration: none;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>请假申请</h2>
    <form action="${pageContext.request.contextPath}/leave" method="post">
        <input type="hidden" name="action" value="apply">
        <div class="form-item">
            <label>请假类型</label>
            <select name="leaveType">
                <option value="事假">事假</option>
                <option value="病假">病假</option>
            </select>
        </div>
        <div class="form-item">
            <label>开始日期</label>
            <input type="date" name="startDate">
        </div>
        <div class="form-item">
            <label>结束日期</label>
            <input type="date" name="endDate">
        </div>
        <div class="form-item">
            <label>请假原因</label>
            <textarea name="reason" rows="4"></textarea>
        </div>
        <button class="btn">提交申请</button>
    </form>
    <a href="${pageContext.request.contextPath}/employee/index.jsp" class="back">← 返回员工主页</a>
</div>
</body>
</html>