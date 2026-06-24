<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/WEB-INF/common/header.jsp">
    <jsp:param name="pageTitle" value="后台首页"/>
</jsp:include>

<div class="card">
    <h1 class="page-title">欢迎您，管理员 ${sessionScope.loginUser.username}</h1>

    <%-- 统计面板 --%>
    <div class="stat-row">
        <div class="stat-card">
            <div class="stat-num">🏢</div>
            <div class="stat-label">部门管理</div>
            <a href="${pageContext.request.contextPath}/dept?action=list" class="btn btn-primary" style="margin-top:0.5rem;">进入管理</a>
        </div>
        <div class="stat-card">
            <div class="stat-num">👥</div>
            <div class="stat-label">员工管理</div>
            <a href="${pageContext.request.contextPath}/emp?action=list" class="btn btn-primary" style="margin-top:0.5rem;">进入管理</a>
        </div>
        <div class="stat-card">
            <div class="stat-num">📊</div>
            <div class="stat-label">考勤记录</div>
            <a href="${pageContext.request.contextPath}/attendance?action=listAll" class="btn btn-primary" style="margin-top:0.5rem;">查看记录</a>
        </div>
        <div class="stat-card">
            <div class="stat-num">📝</div>
            <div class="stat-label">请假审批</div>
            <a href="${pageContext.request.contextPath}/leave?action=listAll" class="btn btn-primary" style="margin-top:0.5rem;">进入审批</a>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/common/footer.jsp"/>
