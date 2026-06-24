<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/WEB-INF/common/header.jsp">
    <jsp:param name="pageTitle" value="新增部门"/>
</jsp:include>

<div class="card card-sm">
    <h2 class="page-title">新增部门</h2>

    <form action="${pageContext.request.contextPath}/dept" method="post">
        <input type="hidden" name="action" value="add">
        <div class="form-group">
            <label>部门名称</label>
            <input type="text" name="deptName" class="form-control" required>
        </div>
        <div class="form-group">
            <label>部门描述</label>
            <textarea name="deptDesc" class="form-control" rows="3" placeholder="选填"></textarea>
        </div>
        <button type="submit" class="btn btn-primary btn-block">保 存</button>
    </form>

    <a href="${pageContext.request.contextPath}/dept?action=list" class="link-back">← 返回部门列表</a>
</div>

<jsp:include page="/WEB-INF/common/footer.jsp"/>
