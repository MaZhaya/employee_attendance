<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/common/header.jsp">
    <jsp:param name="pageTitle" value="全员考勤"/>
</jsp:include>

<div class="card">
    <h2 class="page-title">📊 所有员工考勤</h2>

    <table>
        <tr>
            <th>ID</th>
            <th>员工ID</th>
            <th>日期</th>
            <th>上班</th>
            <th>下班</th>
            <th>状态</th>
        </tr>
        <c:choose>
            <c:when test="${not empty attList}">
                <c:forEach items="${attList}" var="a">
                    <tr>
                        <td>${a.id}</td>
                        <td>${a.empId}</td>
                        <td>${a.checkDate}</td>
                        <td>${a.checkIn}</td>
                        <td>${a.checkOut != null ? a.checkOut : '—'}</td>
                        <td>
                            <span class="badge ${a.status == '正常' ? 'badge-success' : 'badge-danger'}">
                                ${a.status}
                            </span>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr><td colspan="6" style="color:#999;">暂无考勤记录</td></tr>
            </c:otherwise>
        </c:choose>
    </table>

    <a href="${pageContext.request.contextPath}/admin/index.jsp" class="link-back">← 返回管理员主页</a>
</div>

<jsp:include page="/WEB-INF/common/footer.jsp"/>
