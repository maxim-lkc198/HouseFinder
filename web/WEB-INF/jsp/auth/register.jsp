<%-- 
    Document   : register
    Created on : Jun 13, 2025, 12:35:51 PM
    Author     : Maxim
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/common/header.jsp" />
    <title>Đăng Ký Tài Khoản Mới</title>
</head>
<body>
    <div class="auth-container">
        <div class="auth-form-wrapper">
            <div class="auth-image-panel">
                <%-- Phần ảnh bên trái, có thể để trống hoặc dùng CSS background --%>
            </div>
            <div class="auth-form-panel">
                <p class="subtitle">Xin chào bạn</p>
                <h2>Đăng ký tài khoản mới</h2>

                <c:if test="${not empty errorMessage}">
                    <div class="error-message">
                        <i class="fa-solid fa-circle-exclamation"></i> ${errorMessage}
                    </div>
                </c:if>

                <form action="register" method="post" id="registerForm">
                    <div class="form-group">
                        <label for="username">Tên đăng nhập</label>
                        <div class="input-wrapper">
                            <i class="fa-solid fa-user input-icon"></i>
                            <input type="text" id="username" name="username" placeholder="Nhập tên đăng nhập" value="${param.username}" required>
                        </div>
                        <p class="input-hint">Tên đăng nhập không dấu, không ký tự đặc biệt.</p>
                    </div>

                    <div class="form-group">
                        <label for="email">Email</label>
                        <div class="input-wrapper">
                            <i class="fa-solid fa-envelope input-icon"></i>
                            <input type="email" id="email" name="email" placeholder="Nhập địa chỉ email" value="${param.email}" required>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="password">Mật khẩu</label>
                        <div class="input-wrapper">
                            <i class="fa-solid fa-lock input-icon"></i>
                            <input type="password" id="password" name="password" placeholder="Nhập mật khẩu" required>
                        </div>
                        <p class="input-hint">Mật khẩu phải có ít nhất 8 ký tự.</p>
                    </div>

                    <div class="form-group">
                        <label for="confirmPassword">Xác nhận mật khẩu</label>
                        <div class="input-wrapper">
                             <i class="fa-solid fa-lock input-icon"></i>
                            <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Nhập lại mật khẩu" required>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="firstName">Họ</label>
                        <input type="text" id="firstName" name="firstName" placeholder="Nhập họ" value="${param.firstName}" required>
                    </div>

                    <div class="form-group">
                        <label for="lastName">Tên</label>
                        <input type="text" id="lastName" name="lastName" placeholder="Nhập tên" value="${param.lastName}" required>
                    </div>

                    <div class="form-group">
                        <label for="phoneNumber">Số điện thoại</label>
                        <div class="input-wrapper">
                             <i class="fa-solid fa-phone input-icon"></i>
                            <input type="tel" id="phoneNumber" name="phoneNumber" placeholder="Nhập số điện thoại" value="${param.phoneNumber}" required>
                        </div>
                    </div>

                    <button type="submit" class="form-submit-btn">Tiếp tục</button>
                </form>

                <div class="auth-switch">
                    <p>Bạn đã có tài khoản? <a href="${pageContext.request.contextPath}/login">Đăng nhập ngay</a></p>
                </div>
            </div>
        </div>
    </div>
    
    <%-- (Tùy chọn) Thêm JavaScript để validation phía client --%>
    <script>
        // JS để kiểm tra mật khẩu khớp, độ dài mật khẩu... trước khi submit
        const form = document.getElementById('registerForm');
        form.addEventListener('submit', function(event) {
            const password = document.getElementById('password').value;
            const confirmPassword = document.getElementById('confirmPassword').value;

            if (password.length < 8) {
                alert('Mật khẩu phải có ít nhất 8 ký tự.');
                event.preventDefault(); // Ngăn form submit
                return;
            }

            if (password !== confirmPassword) {
                alert('Mật khẩu xác nhận không khớp.');
                event.preventDefault(); // Ngăn form submit
                return;
            }
        });
    </script>
</body>
</html>