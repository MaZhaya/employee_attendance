<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List,entity.Attendance" %>
<html>
<head>
    <title>我的考勤记录</title>
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
    <h2>我的考勤记录</h2>
    <table>
        <tr>
            <th>序号</th>
            <th>打卡日期</th>
            <th>上班时间</th>
            <th>下班时间</th>
            <th>考勤状态</th>
        </tr>
        <%
            List<Attendance> attList = (List<Attendance>)request.getAttribute("myAttList");
            if(attList != null){
                for(int i=0;i<attList.size();i++){
                    Attendance a = attList.get(i);
        %>
        <tr>
            <td><%=i+1%></td>
            <td><%=a.getCheckDate()%></td>
            <td><%=a.getCheckIn()%></td>
            <td><%=a.getCheckOut()%></td>
            <td><%=a.getStatus()%></td>
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