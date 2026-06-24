<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/WEB-INF/common/header.jsp">
    <jsp:param name="pageTitle" value="我的考勤"/>
</jsp:include>

<div class="card">
    <h2 class="page-title">📋 我的考勤记录</h2>

    <table>
        <tr>
            <th>序号</th>
            <th>打卡日期</th>
            <th>上班时间</th>
            <th>下班时间</th>
            <th>考勤状态</th>
        </tr>
        <c:choose>
            <c:when test="${not empty attendanceList}">
                <c:forEach items="${attendanceList}" var="att" varStatus="vs">
                    <tr>
                        <td>${vs.count}</td>
                        <td>${att.checkDate}</td>
                        <td>${att.checkIn}</td>
                        <td>${att.checkOut != null ? att.checkOut : '—'}</td>
                        <td>
                            <span class="badge ${att.status == '正常' ? 'badge-success' : 'badge-danger'}">
                                ${att.status}
                            </span>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr><td colspan="5" style="color:#999;">暂无考勤记录</td></tr>
            </c:otherwise>
        </c:choose>
    </table>

    <a href="${pageContext.request.contextPath}/employee/index.jsp" class="link-back">← 返回员工主页</a>
</div>

<jsp:include page="/WEB-INF/common/footer.jsp"/>
