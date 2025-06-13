<%-- 
    Document   : main_header
    Created on : Jun 13, 2025, 5:02:59 PM
    Author     : Maxim
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- Link CSS và Font Awesome --%>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">

<header class="main-header">
    <div class="logo">
        <a href="${pageContext.request.contextPath}/home"><i class="fa-solid fa-house-chimney"></i> FindHouse</a>
    </div>
    
    <nav class="main-nav">
        <%-- Các link menu chính của bạn --%>
        <a href="${pageContext.request.contextPath}/posts?type=nha-tro">Phòng Trọ</a>
        <a href="${pageContext.request.contextPath}/posts?type=can-ho">Căn Hộ</a>
        <a href="${pageContext.request.contextPath}/posts?type=nha-nguyen-can">Nhà Nguyên Căn</a>
    </nav>
    
    <div class="header-actions">
        <%-- Logic hiển thị tùy theo trạng thái đăng nhập --%>
        <c:choose>
            <c:when test="${not empty sessionScope.loggedInUser}">
                <%-- Hiển thị khi đã đăng nhập --%>
                <a href="${pageContext.request.contextPath}/wishlist" class="header-icon-link" title="Yêu thích"><i class="fa-regular fa-heart"></i></a>
                
                <div class="user-menu-dropdown">
                    <a href="${pageContext.request.contextPath}/my-account" class="user-menu-trigger">
                        <i class="fa-regular fa-circle-user"></i>
                        <span>${sessionScope.loggedInUser.firstName}</span>
                    </a>
                    <div class="dropdown-content">
                        <a href="${pageContext.request.contextPath}/my-posts">Quản lý tin đăng</a>
                        <a href="${pageContext.request.contextPath}/my-profile-settings">Thông tin cá nhân</a>
                        <a href="${pageContext.request.contextPath}/recharge">Nạp tiền</a>
                        <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
                    </div>
                </div>
                
                <a href="${pageContext.request.contextPath}/create-post" class="btn btn-primary"><i class="fa-solid fa-plus"></i> Đăng tin</a>
            </c:when>
            <c:otherwise>
                <%-- Hiển thị khi là guest --%>
                <a href="${pageContext.request.contextPath}/login" class="header-icon-link" title="Yêu thích"><i class="fa-regular fa-heart"></i></a>
                <a href="${pageContext.request.contextPath}/login" class="btn-link">Đăng nhập</a>
                <a href="${pageContext.request.contextPath}/register" class="btn-link">Đăng ký</a>
                <a href="${pageContext.request.contextPath}/create-post" class="btn btn-primary">Đăng tin</a>
            </c:otherwise>
        </c:choose>
    </div>
</header>