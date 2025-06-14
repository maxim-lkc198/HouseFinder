<%-- login.jsp --%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <jsp:include page="/WEB-INF/jsp/common/header.jsp" />
    <%-- Load file CSS riêng của trang auth --%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/auth.css?v=8.0">
    <title>Đăng Ký - Bước 2 - FindHouse</title>
</head>
<body>
    <main class="auth-container">
        <div class="auth-form-wrapper">
            <div class="auth-image-panel" style="background-image: url('${pageContext.request.contextPath}/images/auth/register.svg');"></div>
            <div class="auth-form-panel">
                <p class="subtitle">Sắp xong rồi!</p>
                <h2>Hoàn tất thông tin</h2>
                <p class="input-hint" style="margin-bottom: 1.5rem;">Đăng ký cho email: <strong>${requestScope.email}</strong></p>

                <c:if test="${not empty errorMessage}">
                    <div class="error-message"><i class="fa-solid fa-circle-exclamation"></i> ${errorMessage}</div>
                </c:if>

                <form action="register" method="post">
                    <input type="hidden" name="action" value="processRegistration">
                    <input type="hidden" name="email" value="${requestScope.email}">

                    <div class="form-grid">
                        <div class="form-group">
                            <label for="firstName">Họ</label>
                            <input type="text" id="firstName" name="firstName" placeholder="Nhập họ" required>
                        </div>
                        <div class="form-group">
                            <label for="lastName">Tên</label>
                            <input type="text" id="lastName" name="lastName" placeholder="Nhập tên" required>
                        </div>
                        <div class="form-group">
                            <label for="username">Tên đăng nhập</label>
                            <input type="text" id="username" name="username" placeholder="Tên hiển thị" required>
                        </div>
                        <div class="form-group">
                            <label for="phoneNumber">Số điện thoại</label>
                            <input type="tel" id="phoneNumber" name="phoneNumber" placeholder="09xxxxxxxx" required>
                        </div>
                        <div class="form-group">
                            <label for="password">Mật khẩu</label>
                            <input type="password" id="password" name="password" placeholder="Tối thiểu 8 ký tự" required>
                        </div>
                        <div class="form-group">
                            <label for="confirmPassword">Xác nhận mật khẩu</label>
                            <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Nhập lại mật khẩu" required>
                        </div>
                    </div>

                    <button type="submit" class="form-submit-btn">Hoàn tất Đăng ký</button>
                </form>
            </div>
        </div>
    </main>
    <jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
</body>
</html>