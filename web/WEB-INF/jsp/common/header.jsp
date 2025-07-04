<%-- /WEB-INF/jsp/common/header.jsp (CHUẨN HÓA) --%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Be+Vietnam+Pro:wght@400;500;600;700&display=swap" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css"/>
<%-- Load các file CSS chung --%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/base.css?v=8.0">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css?v=8.0">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css?v=8.0">

<header class="site-header">
    <div class="header-left-wrapper">
        <div class="logo">
            <a href="${pageContext.request.contextPath}/home">
                <i class="fa-solid fa-house-chimney"></i> FindHouse
            </a>
        </div>
        <nav class="header-nav">
            <%-- Các link này hiện tại có thể chưa có servlet xử lý, để # hoặc trỏ đến /posts với param --%>
            <a href="${pageContext.request.contextPath}/posts?category=can-ho">Căn Hộ</a>
            <a href="${pageContext.request.contextPath}/posts?category=nha-rieng">Nhà Riêng</a>
            <a href="${pageContext.request.contextPath}/posts?category=phong-tro">Phòng Trọ</a>
        </nav>
    </div>

    <div class="header-right">
         <div class="header-actions">
            <%-- Wishlist và Notifications sẽ được làm ở Iteration 2/3 --%>
            <%-- Luôn yêu cầu đăng nhập để xem các mục này --%>
            <a href="${pageContext.request.contextPath}/wishlist" class="header-icon-link" title="Yêu thích"><i class="fa-regular fa-heart"></i></a>
            <a href="${pageContext.request.contextPath}/notifications" class="header-icon-link" title="Thông báo"><i class="fa-regular fa-bell"></i></a>
            
            <c:choose>
                <c:when test="${not empty sessionScope.loggedInUser}">
                    <div class="user-menu">
                        <div class="user-menu-trigger">
                            <%-- Hiển thị avatar nếu có, nếu không thì hiển thị chữ cái đầu --%>
                            <c:choose>
                                <c:when test="${not empty sessionScope.loggedInUser.avatarUrl}">
                                    <img src="${sessionScope.loggedInUser.avatarUrl}" class="user-avatar-img" alt="Avatar">
                                </c:when>
                                <c:otherwise>
                                    <div class="user-avatar-char">${sessionScope.loggedInUser.firstName.substring(0,1)}</div>
                                </c:otherwise>
                            </c:choose>
                            <span>${sessionScope.loggedInUser.firstName} ${sessionScope.loggedInUser.lastName}</span>
                            <i class="fa-solid fa-chevron-down fa-xs"></i>
                        </div>
                        <div class="dropdown-content">
                             <div class="dropdown-promo">
                                 <h4>Nâng cấp tài khoản</h4>
                                 <p>Tiết kiệm đến 39% chi phí với Gói Hội Viên của chúng tôi!</p>
                                 <a href="${pageContext.request.contextPath}/membership" class="btn-promo">Khám phá ngay</a>
                             </div>
                            <a href="${pageContext.request.contextPath}/my-account"><i class="fa-solid fa-chart-line"></i> Tổng quan tài khoản</a>
                            <a href="${pageContext.request.contextPath}/my-posts"><i class="fa-solid fa-list-check"></i> Quản lý tin đăng</a>
                            <a href="${pageContext.request.contextPath}/my-membership"><i class="fa-solid fa-layer-group"></i> Gói hội viên</a>
                            <a href="${pageContext.request.contextPath}/my-profile-settings"><i class="fa-solid fa-user-pen"></i> Thông tin cá nhân</a>
                            <a href="${pageContext.request.contextPath}/change-password"><i class="fa-solid fa-key"></i> Đổi mật khẩu</a>
                            <a href="${pageContext.request.contextPath}/recharge"><i class="fa-solid fa-wallet"></i> Nạp tiền</a>
                            <div class="dropdown-divider"></div>
                            <a href="${pageContext.request.contextPath}/logout" class="logout-link"><i class="fa-solid fa-arrow-right-from-bracket"></i> Đăng xuất</a>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/login" class="btn btn-link">Đăng nhập</a>
                    <a href="${pageContext.request.contextPath}/register" class="btn btn-link">Đăng ký</a>
                </c:otherwise>
            </c:choose>

                        <a href="${pageContext.request.contextPath}/create-post" class="btn btn-accent">Đăng tin</a>

        </div>
    </div>
</header>