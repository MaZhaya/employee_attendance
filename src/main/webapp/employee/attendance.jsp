<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/WEB-INF/common/header.jsp">
    <jsp:param name="pageTitle" value="上下班打卡"/>
</jsp:include>

<div class="card card-sm" style="text-align:center;">
    <h2 class="page-title">⏰ 上下班打卡</h2>

    <p style="margin:1.5rem 0;color:var(--text-light);">当前用户：${sessionScope.loginUser.username}</p>

    <%-- 操作提示 --%>
    <c:if test="${not empty msg}">
        <div class="alert ${msg.contains('成功') ? 'alert-success' : 'alert-info'}">${msg}</div>
    </c:if>

    <div class="btn-group">
        <form action="${pageContext.request.contextPath}/attendance" method="post">
            <input type="hidden" name="action" value="checkIn">
            <button type="submit" class="btn btn-success">上班打卡</button>
        </form>
        <form action="${pageContext.request.contextPath}/attendance" method="post">
            <input type="hidden" name="action" value="checkOut">
            <button type="submit" class="btn btn-danger">下班签退</button>
        </form>
    </div>

    <a href="${pageContext.request.contextPath}/employee/index.jsp" class="link-back">← 返回员工主页</a>
</div>

<jsp:include page="/WEB-INF/common/footer.jsp"/>
