<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/common/header.jsp" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/dashboard.css">
    <title>Tài Khoản Của Tôi - FindHouse</title>
</head>
<body>
    <main class="page-container">
        <div class="container">
            <h1>Tổng Quan Tài Khoản</h1>

            <div class="dashboard-grid">
                <%-- Sidebar điều hướng --%>
                <aside class="dashboard-sidebar">
                    <div class="profile-summary">
                        <div class="user-avatar-large">${sessionScope.loggedInUser.firstName.substring(0,1)}</div>
                        <h4>${sessionScope.loggedInUser.firstName} ${sessionScope.loggedInUser.lastName}</h4>
                        <p>${sessionScope.loggedInUser.email}</p>
                    </div>
                    <nav class="dashboard-nav">
                        <a href="${pageContext.request.contextPath}/my-account" class="active"><i class="fa-solid fa-user-circle"></i> Tổng quan</a>
                        <a href="${pageContext.request.contextPath}/my-posts"><i class="fa-solid fa-list-check"></i> Quản lý tin đăng</a>
                        <a href="${pageContext.request.contextPath}/my-profile-settings"><i class="fa-solid fa-user-pen"></i> Thông tin cá nhân</a>
                        <a href="${pageContext.request.contextPath}/change-password"><i class="fa-solid fa-key"></i> Đổi mật khẩu</a>
                        <a href="${pageContext.request.contextPath}/my-membership"><i class="fa-solid fa-layer-group"></i> Gói hội viên</a>
                        <a href="${pageContext.request.contextPath}/transaction-history"><i class="fa-solid fa-receipt"></i> Lịch sử giao dịch</a>
                    </nav>
                </aside>

                <%-- Nội dung chính --%>
                <section class="dashboard-content">
                    <div class="content-card">
                        <h3>Thông tin cá nhân</h3>
                        <p><strong>Họ và tên:</strong> ${sessionScope.loggedInUser.firstName} ${sessionScope.loggedInUser.lastName}</p>
                        <p><strong>Email:</strong> ${sessionScope.loggedInUser.email}</p>
                        <p><strong>Số điện thoại:</strong> ${sessionScope.loggedInUser.phoneNumber}</p>
                        <a href="${pageContext.request.contextPath}/my-profile-settings" class="btn btn-secondary">Chỉnh sửa</a>
                    </div>

                    <div class="content-card">
                        <h3>Số dư tài khoản</h3>
                        <p class="balance-amount">
                            <fmt:formatNumber value="${sessionScope.loggedInUser.accountBalance}" type="currency" currencyCode="VND" />
                        </p>
                        <p>Sử dụng số dư để thanh toán đăng tin hoặc nâng cấp gói hội viên.</p>
                        <a href="${pageContext.request.contextPath}/recharge" class="btn btn-accent">Nạp tiền ngay</a>
                    </div>
                    
                    <div class="content-card">
                        <h3>Xác thực tài khoản (KYC)</h3>
                        <c:choose>
                            <c:when test="${sessionScope.loggedInUser.verificationStatus == 'VERIFIED'}">
                                <p class="status-verified"><i class="fa-solid fa-check-circle"></i> Tài khoản đã được xác thực.</p>
                            </c:when>
                             <c:when test="${sessionScope.loggedInUser.verificationStatus == 'PENDING'}">
                                <p class="status-pending"><i class="fa-solid fa-clock"></i> Thông tin đang chờ duyệt.</p>
                            </c:when>
                            <c:otherwise>
                                <p class="status-unverified"><i class="fa-solid fa-times-circle"></i> Tài khoản chưa được xác thực.</p>
                                <p>Xác thực tài khoản để nhận huy hiệu "Tin cậy" và tăng uy tín cho tin đăng của bạn.</p>
                                <a href="${pageContext.request.contextPath}/verification" class="btn btn-secondary">Xác thực ngay</a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </section>
            </div>
        </div>
    </main>
    <jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
</body>
</html>