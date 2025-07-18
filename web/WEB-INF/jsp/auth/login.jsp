<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <jsp:include page="/WEB-INF/jsp/common/header.jsp" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/auth.css?v=8.0">
    <title>Đăng Nhập - FindHouse</title>
</head>
<body class="page-auth">
    <div class="auth-container">
        <div class="auth-form-wrapper">
            <div class="auth-image-panel" style="background-image: url('${pageContext.request.contextPath}/image/auth/undraw_unlock_q1e5.svg');"></div>
            <div class="auth-form-panel">
                <p class="subtitle">Chào mừng trở lại!</p>
                <h2>Đăng nhập vào tài khoản</h2>

                <c:if test="${not empty errorMessage}">
                    <div class="error-message"><i class="fa-solid fa-circle-exclamation"></i> ${errorMessage}</div>
                </c:if>
                <c:if test="${not empty successMessage}">
                    <div class="success-message"><i class="fa-solid fa-circle-check"></i> ${successMessage}</div>
                </c:if>
                <c:if test="${param.logout == 'true'}">
                    <div class="success-message"><i class="fa-solid fa-circle-check"></i> Bạn đã đăng xuất thành công.</div>
                </c:if>

                <form action="login" method="post" style="margin-top: 1.5rem;" onsubmit="return validateLoginForm()">
                    <div class="form-group">
                        <label for="usernameOrEmail">Tên đăng nhập hoặc Email</label>
                        <div class="input-wrapper">
                            <i class="fa-solid fa-user input-icon"></i>
                            <input type="text" id="usernameOrEmail" name="usernameOrEmail" placeholder="Nhập tên đăng nhập hoặc email" required>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="password">Mật khẩu</label>
                        <div class="input-wrapper">
                            <i class="fa-solid fa-lock input-icon"></i>
                            <input type="password" id="password" name="password" placeholder="Nhập mật khẩu" required>
                        </div>
                    </div>

                    <div class="form-options" style="text-align: right; margin-bottom: 1rem;">
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
    
    <jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
    <script>
        function validateLoginForm() {
            var usernameOrEmail = document.getElementById('usernameOrEmail').value.trim();
            var password = document.getElementById('password').value;
            if (!usernameOrEmail || !password) {
                alert('Tên đăng nhập/email và mật khẩu không được để trống.');
                return false;
            }
            return true;
        }
    </script>
</body>
</html>