<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List,entity.Leave" %>
<html>
<head>
    <title>员工请假审批</title>
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
            width: 95%;
            max-width: 1100px;
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
        .pass {
            color: #27ae60;
            text-decoration: none;
            margin-right: 10px;
        }
        .refuse {
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
    <h2>员工请假审批管理</h2>
    <table>
        <tr>
            <th>ID</th>
            <th>员工ID</th>
            <th>请假类型</th>
            <th>起止日期</th>
            <th>请假原因</th>
            <th>当前状态</th>
            <th>操作</th>
        </tr>
        <%
            List<Leave> leaveAllList = (List<Leave>)request.getAttribute("leaveAllList");
            if(leaveAllList != null){
                for(Leave l : leaveAllList){
        %>
        <tr>
            <td><%=l.getId()%></td>
            <td><%=l.getEmpId()%></td>
            <td><%=l.getLeaveType()%></td>
            <td><%=l.getStartDate()%> ~ <%=l.getEndDate()%></td>
            <td><%=l.getReason()%></td>
            <td><%=l.getStatus()%></td>
            <td>
                <% if("待审批".equals(l.getStatus())){ %>
                <a href="${pageContext.request.contextPath}/leave?action=pass&id=<%=l.getId()%>" class="pass">通过</a>
                <a href="${pageContext.request.contextPath}/leave?action=refuse&id=<%=l.getId()%>" class="refuse">拒绝</a>
                <% }else{ %>
                已处理
                <% } %>
            </td>
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