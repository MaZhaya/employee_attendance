<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>新增部门</title>
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
        input {
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
    <h2>新增部门</h2>
    <form action="${pageContext.request.contextPath}/dept" method="post">
        <input type="hidden" name="action" value="add">
        <div class="form-item">
            <label>部门名称</label>
            <input type="text" name="deptName" required>
        </div>
        <button class="btn">保存</button>
    </form>
    <a href="${pageContext.request.contextPath}/dept?action=list" class="back">← 返回部门列表</a>
</div>
</body>
</html>