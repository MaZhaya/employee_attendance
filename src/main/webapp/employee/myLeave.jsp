<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/common/header.jsp">
    <jsp:param name="pageTitle" value="我的请假"/>
</jsp:include>

<div class="card">
    <h2 class="page-title">📄 我的请假记录</h2>

    <table>
        <tr>
            <th>序号</th>
            <th>请假类型</th>
            <th>开始日期</th>
            <th>结束日期</th>
            <th>原因</th>
            <th>审批状态</th>
        </tr>
        <c:choose>
            <c:when test="${not empty leaveList}">
                <c:forEach items="${leaveList}" var="leave" varStatus="vs">
                    <tr>
                        <td>${vs.count}</td>
                        <td>${leave.leaveType}</td>
                        <td>${leave.startTime}</td>
                        <td>${leave.endTime}</td>
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
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr><td colspan="6" style="color:#999;">暂无请假记录</td></tr>
            </c:otherwise>
        </c:choose>
    </table>

    <a href="${pageContext.request.contextPath}/employee/index.jsp" class="link-back">← 返回员工主页</a>
</div>

<jsp:include page="/WEB-INF/common/footer.jsp"/>
