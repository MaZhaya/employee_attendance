<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/WEB-INF/common/header.jsp">
    <jsp:param name="pageTitle" value="员工中心"/>
</jsp:include>

<div class="card">
    <h1 class="page-title">欢迎您，员工 ${sessionScope.loginUser.username}</h1>

    <div class="stat-row">
        <div class="stat-card">
            <div class="stat-num">⏰</div>
            <div class="stat-label">上下班打卡</div>
            <a href="${pageContext.request.contextPath}/attendance" class="btn btn-success" style="margin-top:0.5rem;">去打考勤</a>
        </div>
        <div class="stat-card">
            <div class="stat-num">📋</div>
            <div class="stat-label">我的考勤</div>
            <a href="${pageContext.request.contextPath}/attendance?action=listMyAttendance" class="btn btn-primary" style="margin-top:0.5rem;">查看记录</a>
        </div>
        <div class="stat-card">
            <div class="stat-num">📝</div>
            <div class="stat-label">请假申请</div>
            <a href="${pageContext.request.contextPath}/leave?action=toApply" class="btn btn-warning" style="margin-top:0.5rem;">去请假</a>
        </div>
        <div class="stat-card">
            <div class="stat-num">📄</div>
            <div class="stat-label">我的请假</div>
            <a href="${pageContext.request.contextPath}/leave?action=listMyLeave" class="btn btn-primary" style="margin-top:0.5rem;">查看记录</a>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/common/footer.jsp"/>
