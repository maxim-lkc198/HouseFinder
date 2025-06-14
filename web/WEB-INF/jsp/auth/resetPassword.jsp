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
    <title>Đặt Lại Mật Khẩu - FindHouse</title>
</head>
<body class="page-auth">
    <div class="auth-container">
        <div class="auth-form-wrapper">
            <div class="auth-image-panel" style="background-image: url('${pageContext.request.contextPath}/image/auth/undraw_forgot-password_odai.svg');"></div>
            <div class="auth-form-panel">
                <p class="subtitle">An toàn là trên hết</p>
                <h2>Tạo mật khẩu mới</h2>
                <p class="input-hint" style="margin-bottom: 2rem;">Mật khẩu mới của bạn phải khác với các mật khẩu đã sử dụng trước đây.</p>
                
                <c:if test="${not empty errorMessage}">
                    <div class="error-message"><i class="fa-solid fa-circle-exclamation"></i> ${errorMessage}</div>
                </c:if>

                <form action="reset-password" method="post">
                    <input type="hidden" name="token" value="${requestScope.token}">
                    
                    <div class="form-group">
                        <label for="newPassword">Mật khẩu mới</label>
                        <div class="input-wrapper">
                            <i class="fa-solid fa-lock input-icon"></i>
                            <input type="password" id="newPassword" name="newPassword" placeholder="Nhập mật khẩu mới" required>
                        </div>
                        <p class="input-hint">Mật khẩu phải có ít nhất 8 ký tự.</p>
                    </div>

                    <div class="form-group">
                        <label for="confirmPassword">Xác nhận mật khẩu mới</label>
                        <div class="input-wrapper">
                             <i class="fa-solid fa-lock input-icon"></i>
                            <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Nhập lại mật khẩu mới" required>
                        </div>
                    </div>
                    
                    <button type="submit" class="form-submit-btn">Đặt Lại Mật Khẩu</button>
                </form>
            </div>
        </div>
    </div>
    
    <jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
</body>
</html>