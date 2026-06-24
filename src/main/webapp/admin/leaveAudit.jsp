<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/common/header.jsp">
    <jsp:param name="pageTitle" value="请假审批"/>
</jsp:include>

<div class="card">
    <h2 class="page-title">📝 员工请假审批管理</h2>

    <c:if test="${not empty msg}">
        <div class="alert ${msg.contains('成功') || msg.contains('通过') ? 'alert-success' : 'alert-info'}">${msg}</div>
    </c:if>

    <table>
        <tr>
            <th>编号</th>
            <th>员工ID</th>
            <th>请假类型</th>
            <th>起止日期</th>
            <th>原因</th>
            <th>当前状态</th>
            <th>操作</th>
        </tr>
        <c:choose>
            <c:when test="${not empty leaveList}">
                <c:forEach items="${leaveList}" var="leave">
                    <tr>
                        <td>${leave.id}</td>
                        <td>${leave.empId}</td>
                        <td>${leave.leaveType}</td>
                        <td>${leave.startTime} ~ ${leave.endTime}</td>
                        <td>${leave.reason}</td>
                        <td>
                            <c:choose>
                                <c:when test="${leave.approveStatus == '已通过'}">
                                    <span class="badge badge-success">已通过</span>
                                </c:when>
                                <c:when test="${leave.approveStatus == '已拒绝'}">
                                    <span class="badge badge-danger">已拒绝</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge badge-warning">待审批</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:if test="${leave.approveStatus == '待审批'}">
                                <a href="${pageContext.request.contextPath}/leave?action=pass&id=${leave.id}"
                                   class="btn btn-success btn-sm">通过</a>
                                <a href="${pageContext.request.contextPath}/leave?action=refuse&id=${leave.id}"
                                   class="btn btn-danger btn-sm"
                                   onclick="return confirm('确定要拒绝吗？')">拒绝</a>
                            </c:if>
                            <c:if test="${leave.approveStatus != '待审批'}">
                                <span style="color:#999;">已处理</span>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr><td colspan="7" style="color:#999;">暂无请假申请</td></tr>
            </c:otherwise>
        </c:choose>
    </table>

    <a href="${pageContext.request.contextPath}/admin/index.jsp" class="link-back">← 返回管理员主页</a>
</div>

<jsp:include page="/WEB-INF/common/footer.jsp"/>
