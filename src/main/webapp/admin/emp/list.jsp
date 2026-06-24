<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List,entity.Employee" %>
<html>
<head>
    <title>员工信息管理</title>
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
            width: 80%;
            max-width: 900px;
            margin: 3rem auto;
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
        .add-btn {
            display: inline-block;
            padding: 8px 16px;
            background-color: #27ae60;
            color: #fff;
            text-decoration: none;
            border-radius: 6px;
            margin-bottom: 1rem;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            text-align: center;
        }
        th,td {
            padding: 12px;
            border-bottom: 1px solid #eee;
        }
        th {
            background-color: #f8f9fa;
        }
        .del {
            color: #e74c3c;
            text-decoration: none;
        }
        .back {
            display: inline-block;
            margin-top: 1.5rem;
            color: #3498db;
            text-decoration: none;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>员工信息管理</h2>
    <a href="${pageContext.request.contextPath}/emp?action=toAdd" class="add-btn">新增员工</a>
    <table>
        <tr>
            <th>员工ID</th>
            <th>姓名</th>
            <th>性别</th>
            <th>所属部门ID</th>
            <th>操作</th>
        </tr>
        <%
            List<Employee> empList = (List<Employee>)request.getAttribute("empList");
            if(empList != null){
                for(Employee e : empList){
        %>
        <tr>
            <td><%=e.getId()%></td>
            <td><%=e.getEmpName()%></td>
            <td><%=e.getGender()%></td>
            <td><%=e.getDeptId()%></td>
            <td><a href="${pageContext.request.contextPath}/emp?action=delete&id=<%=e.getId()%>" class="del">删除</a></td>
        </tr>
        <%
                }
            }
        %>
    </table>
    <a href="${pageContext.request.contextPath}/admin/index.jsp" class="back">← 返回管理员主页</a>
</div>
</body>
</html>