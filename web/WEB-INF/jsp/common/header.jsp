<%-- 
    Document   : header
    Created on : Jun 13, 2025, 12:35:27 PM
    Author     : Maxim
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- Link CSS và Font Awesome --%>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">

<header class="main-header">
    <div class="logo">
        <a href="${pageContext.request.contextPath}/home"><i class="fa-solid fa-house-chimney"></i> FindHouse</a>
    </div>
    <nav class="main-nav">
        <%-- Các link menu chính --%>
        <a href="#">Nhà đất bán</a>
        <a href="#">Nhà đất cho thuê</a>
        <a href="#">Dự án</a>
    </nav>
    <div class="header-actions">
        <%-- Logic hiển thị tùy theo trạng thái đăng nhập --%>
        <c:choose>
            <c:when test="${not empty sessionScope.loggedInUser}">
                <%-- Hiển thị khi đã đăng nhập --%>
                <span>Chào, ${sessionScope.loggedInUser.firstName}!</span>
                <a href="${pageContext.request.contextPath}/logout" class="btn btn-secondary">Đăng xuất</a>
                <a href="${pageContext.request.contextPath}/create-post" class="btn btn-primary">Đăng tin</a>
            </c:when>
            <c:otherwise>
                <%-- Hiển thị khi là guest --%>
                <a href="${pageContext.request.contextPath}/login" class="btn btn-secondary">Đăng nhập</a>
                <a href="${pageContext.request.contextPath}/register" class="btn btn-secondary">Đăng ký</a>
                <a href="${pageContext.request.contextPath}/create-post" class="btn btn-primary">Đăng tin</a>
            </c:otherwise>
        </c:choose>
    </div>
</header>