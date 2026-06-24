<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>新增员工</title>
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
        input,select {
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
    <h2>新增员工</h2>
    <form action="${pageContext.request.contextPath}/emp" method="post">
        <input type="hidden" name="action" value="add">
        <div class="form-item">
            <label>员工姓名</label>
            <input type="text" name="empName" required>
        </div>
        <div class="form-item">
            <label>性别</label>
            <select name="gender">
                <option value="男">男</option>
                <option value="女">女</option>
            </select>
        </div>
        <div class="form-item">
            <label>所属部门ID</label>
            <input type="number" name="deptId" required>
        </div>
        <button class="btn">保存</button>
    </form>
    <a href="${pageContext.request.contextPath}/emp?action=list" class="back">← 返回员工列表</a>
</div>
</body>
</html>