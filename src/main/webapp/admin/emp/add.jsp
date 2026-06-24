<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/WEB-INF/common/header.jsp">
    <jsp:param name="pageTitle" value="新增员工"/>
</jsp:include>

<div class="card card-sm">
    <h2 class="page-title">新增员工</h2>

    <form action="${pageContext.request.contextPath}/emp" method="post">
        <div class="form-group">
            <label>员工姓名</label>
            <input type="text" name="empName" class="form-control" required>
        </div>
        <div class="form-group">
            <label>性别</label>
            <select name="gender" class="form-control">
                <option value="男">男</option>
                <option value="女">女</option>
            </select>
        </div>
        <div class="form-group">
            <label>所属部门ID</label>
            <input type="number" name="deptId" class="form-control" required>
        </div>
        <button type="submit" class="btn btn-primary btn-block">保 存</button>
    </form>

    <a href="${pageContext.request.contextPath}/emp?action=list" class="link-back">← 返回员工列表</a>
</div>

<jsp:include page="/WEB-INF/common/footer.jsp"/>
