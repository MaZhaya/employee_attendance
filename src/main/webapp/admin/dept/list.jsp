<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/common/header.jsp">
    <jsp:param name="pageTitle" value="部门管理"/>
</jsp:include>

<div class="card">
    <h2 class="page-title">🏢 部门管理</h2>

    <a href="${pageContext.request.contextPath}/dept?action=toAdd" class="btn btn-success" style="margin-bottom:1rem;">＋ 新增部门</a>

    <table>
        <tr>
            <th>部门编号</th>
            <th>部门名称</th>
            <th>操作</th>
        </tr>
        <c:choose>
            <c:when test="${not empty deptList}">
                <c:forEach items="${deptList}" var="dept">
                    <tr>
                        <td>${dept.id}</td>
                        <td>${dept.deptName}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/dept?action=delete&id=${dept.id}"
                               class="link-danger"
                               onclick="return confirm('确定要删除部门「${dept.deptName}」吗？')">删除</a>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr><td colspan="3" style="color:#999;">暂无部门数据</td></tr>
            </c:otherwise>
        </c:choose>
    </table>

    <a href="${pageContext.request.contextPath}/admin/index.jsp" class="link-back">← 返回管理员主页</a>
</div>

<jsp:include page="/WEB-INF/common/footer.jsp"/>
