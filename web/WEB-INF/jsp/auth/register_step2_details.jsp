<%-- 
    Document   : register_step2_details
    Created on : Jun 13, 2025, 1:48:10 PM
    Author     : Maxim
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/common/auth_header.jsp" />
    <title>Đăng Ký - Bước 2</title>
</head>
<body>
    <div class="auth-container">
        <div class="auth-form-wrapper">
            <div class="auth-image-panel" style="background-image: url('${pageContext.request.contextPath}/images/register-bg.jpg');"></div>
            <div class="auth-form-panel">
                <p class="subtitle">Hoàn tất thông tin</p>
                <h2>Chào mừng bạn đến với FindHouse!</h2>
                <p>Đăng ký cho email: <strong>${requestScope.email}</strong></p> <%-- Hiển thị lại email --%>

                <c:if test="${not empty errorMessage}">
                    <div class="error-message">
                        <i class="fa-solid fa-circle-exclamation"></i> ${errorMessage}
                    </div>
                </c:if>

                <form action="register" method="post">
                    <input type="hidden" name="action" value="processRegistration">
                    <input type="hidden" name="email" value="${requestScope.email}"> <%-- Truyền lại email qua hidden input --%>

                    <div class="form-group">
                        <label for="username">Tên đăng nhập</label>
                        <input type="text" id="username" name="username" placeholder="Nhập tên đăng nhập" required>
                    </div>

                    <div class="form-group">
                        <label for="firstName">Họ</label>
                        <input type="text" id="firstName" name="firstName" placeholder="Nhập họ của bạn" required>
                    </div>

                    <div class="form-group">
                        <label for="lastName">Tên</label>
                        <input type="text" id="lastName" name="lastName" placeholder="Nhập tên của bạn" required>
                    </div>

                    <div class="form-group">
                        <label for="phoneNumber">Số điện thoại</label>
                        <input type="tel" id="phoneNumber" name="phoneNumber" placeholder="Nhập số điện thoại" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="password">Mật khẩu</label>
                        <input type="password" id="password" name="password" placeholder="Mật khẩu (ít nhất 8 ký tự)" required>
                    </div>

                    <div class="form-group">
                        <label for="confirmPassword">Xác nhận mật khẩu</label>
                        <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Nhập lại mật khẩu" required>
                    </div>

                    <button type="submit" class="form-submit-btn">Hoàn tất Đăng ký</button>
                </form>
            </div>
        </div>
    </div>
</body>
</html>