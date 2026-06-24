<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/common/header.jsp">
    <jsp:param name="pageTitle" value="员工管理"/>
</jsp:include>

<div class="card">
    <h2 class="page-title">👥 员工信息管理</h2>

    <a href="${pageContext.request.contextPath}/emp?action=toAdd" class="btn btn-success" style="margin-bottom:1rem;">＋ 新增员工</a>

    <table>
        <tr>
            <th>编号</th>
            <th>姓名</th>
            <th>性别</th>
            <th>所属部门</th>
            <th>操作</th>
        </tr>
        <c:choose>
            <c:when test="${not empty empList}">
                <c:forEach items="${empList}" var="emp">
                    <tr>
                        <td>${emp.id}</td>
                        <td>${emp.empName}</td>
                        <td>${emp.gender}</td>
                        <td>${emp.deptId}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/emp?action=delete&id=${emp.id}"
                               class="link-danger"
                               onclick="return confirm('确定要删除员工「${emp.empName}」吗？')">删除</a>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr><td colspan="5" style="color:#999;">暂无员工数据</td></tr>
            </c:otherwise>
        </c:choose>
    </table>

    <a href="${pageContext.request.contextPath}/admin/index.jsp" class="link-back">← 返回管理员主页</a>
</div>

<jsp:include page="/WEB-INF/common/footer.jsp"/>
