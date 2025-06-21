<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<aside class="dashboard-sidebar">
    <div class="profile-summary">
        <div class="user-avatar-large">${sessionScope.loggedInUser.firstName.substring(0,1)}</div>
        <h4>${sessionScope.loggedInUser.firstName} ${sessionScope.loggedInUser.lastName}</h4>
        <p>${sessionScope.loggedInUser.email}</p>
    </div>
    <nav class="dashboard-nav">
        <%-- Sử dụng EL để kiểm tra URL hiện tại và thêm class 'active' --%>
        <c:set var="activePage" value="${pageContext.request.servletPath}" />

        <a href="${pageContext.request.contextPath}/my-account" class="${activePage == '/my-account' ? 'active' : ''}"><i class="fa-solid fa-user-circle"></i> Tổng quan</a>
        <a href="${pageContext.request.contextPath}/my-posts" class="${activePage == '/my-posts' ? 'active' : ''}"><i class="fa-solid fa-list-check"></i> Quản lý tin đăng</a>
        <a href="${pageContext.request.contextPath}/my-profile-settings" class="${activePage == '/my-profile-settings' ? 'active' : ''}"><i class="fa-solid fa-user-pen"></i> Thông tin cá nhân</a>
        <a href="${pageContext.request.contextPath}/change-password" class="${activePage == '/change-password' ? 'active' : ''}"><i class="fa-solid fa-key"></i> Đổi mật khẩu</a>
        <a href="${pageContext.request.contextPath}/my-membership" class="${activePage == '/my-membership' ? 'active' : ''}"><i class="fa-solid fa-layer-group"></i> Gói hội viên</a>
        <a href="${pageContext.request.contextPath}/transaction-history" class="${activePage == '/transaction-history' ? 'active' : ''}"><i class="fa-solid fa-receipt"></i> Lịch sử giao dịch</a>
    </nav>
</aside>