<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/WEB-INF/common/header.jsp">
    <jsp:param name="pageTitle" value="请假申请"/>
</jsp:include>

<div class="card card-sm">
    <h2 class="page-title">📝 请假申请</h2>

    <c:if test="${not empty msg}">
        <div class="alert ${msg.contains('成功') ? 'alert-success' : 'alert-error'}">${msg}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/leave/apply" method="post">
        <div class="form-group">
            <label>请假类型</label>
            <select name="leaveType" class="form-control">
                <option value="事假">事假</option>
                <option value="病假">病假</option>
                <option value="年假">年假</option>
            </select>
        </div>
        <div class="form-group">
            <label>开始日期</label>
            <input type="date" name="startDate" class="form-control">
        </div>
        <div class="form-group">
            <label>结束日期</label>
            <input type="date" name="endDate" class="form-control">
        </div>
        <div class="form-group">
            <label>请假原因</label>
            <textarea name="reason" class="form-control" rows="4" placeholder="请详细说明请假原因" required></textarea>
        </div>
        <button type="submit" class="btn btn-primary btn-block">提交申请</button>
    </form>

    <a href="${pageContext.request.contextPath}/employee/index.jsp" class="link-back">← 返回员工主页</a>
</div>

<jsp:include page="/WEB-INF/common/footer.jsp"/>
