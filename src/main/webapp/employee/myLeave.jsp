<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List,entity.Leave" %>
<html>
<head>
  <title>我的请假记录</title>
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
  <h2>我的请假记录</h2>
  <table>
    <tr>
      <th>序号</th>
      <th>请假类型</th>
      <th>开始日期</th>
      <th>结束日期</th>
      <th>原因</th>
      <th>审批状态</th>
    </tr>
    <%
      List<Leave> leaveList = (List<Leave>)request.getAttribute("myLeaveList");
      if(leaveList != null){
        for(int i=0;i<leaveList.size();i++){
          Leave l = leaveList.get(i);
    %>
    <tr>
      <td><%=i+1%></td>
      <td><%=l.getLeaveType()%></td>
      <td><%=l.getStartDate()%></td>
      <td><%=l.getEndDate()%></td>
      <td><%=l.getReason()%></td>
      <td><%=l.getStatus()%></td>
    </tr>
    <%
        }
      }
    %>
  </table>
  <a href="${pageContext.request.contextPath}/employee/index.jsp" class="back">← 返回员工主页</a>
</div>
</body>
</html>