<%-- 
    Document   : login
    Created on : Jun 13, 2025, 2:24:24 PM
    Author     : Maxim
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/common/auth_header.jsp" />
    <title>Đăng Nhập - FindHouse</title>
</head>
<body>
    <div class="auth-container">
        <div class="auth-form-wrapper">
            <div class="auth-image-panel" style="background-image: url('${pageContext.request.contextPath}/images/login-bg.jpg');">
                <%-- Bạn có thể tìm một ảnh phù hợp cho trang đăng nhập, ví dụ cảnh một người đang nhận chìa khóa nhà --%>
            </div>
            <div class="auth-form-panel">
                <p class="subtitle">Chào mừng trở lại!</p>
                <h2>Đăng nhập vào tài khoản</h2>

                <%-- Hiển thị thông báo lỗi chung --%>
                <c:if test="${not empty errorMessage}">
                    <div class="error-message">
                        <i class="fa-solid fa-circle-exclamation"></i> ${errorMessage}
                    </div>
                </c:if>

                <%-- Hiển thị thông báo khi đăng ký/reset pass thành công --%>
                <c:if test="${not empty successMessage}">
                    <div class="success-message">
                        <i class="fa-solid fa-circle-check"></i> ${successMessage}
                    </div>
                </c:if>
                
                <%-- Hiển thị thông báo khi logout --%>
                 <c:if test="${param.logout == 'true'}">
                    <div class="success-message">
                        <i class="fa-solid fa-circle-check"></i> Bạn đã đăng xuất thành công.
                    </div>
                </c:if>

                <form action="login" method="post">
                    <div class="form-group">
                        <label for="username">Tên đăng nhập hoặc Email</label>
                        <div class="input-wrapper">
                            <i class="fa-solid fa-user input-icon"></i>
                            <input type="text" id="username" name="username" placeholder="Nhập tên đăng nhập hoặc email" required>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="password">Mật khẩu</label>
                        <div class="input-wrapper">
                            <i class="fa-solid fa-lock input-icon"></i>
                            <input type="password" id="password" name="password" placeholder="Nhập mật khẩu" required>
                        </div>
                    </div>

                    <div class="form-options">
                        <%-- Thêm checkbox "Remember Me" nếu bạn triển khai trong tương lai --%>
                        <a href="${pageContext.request.contextPath}/forgot-password" class="forgot-password-link">Quên mật khẩu?</a>
                    </div>

                    <button type="submit" class="form-submit-btn">Đăng Nhập</button>
                </form>

                <div class="auth-switch">
                    <p>Chưa có tài khoản? <a href="${pageContext.request.contextPath}/register">Đăng ký ngay</a></p>
                </div>
            </div>
        </div>
    </div>
</body>
</html>