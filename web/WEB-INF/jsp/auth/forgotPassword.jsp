<%-- 
    Document   : forgotPassword
    Created on : Jun 13, 2025, 2:24:47 PM
    Author     : Maxim
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/common/auth_header.jsp" />
    <title>Quên Mật Khẩu - FindHouse</title>
</head>
<body>
    <div class="auth-container">
        <div class="auth-form-wrapper">
            <div class="auth-image-panel" style="background-image: url('${pageContext.request.contextPath}/images/forgot-password-bg.jpg');">
                 <%-- Một ảnh khác, có thể là hình một ổ khóa hoặc chìa khóa --%>
            </div>
            <div class="auth-form-panel">
                <p class="subtitle">Đặt lại mật khẩu của bạn</p>
                <h2>Quên mật khẩu?</h2>
                <p class="input-hint" style="margin-bottom: 2rem;">Đừng lo lắng! Nhập email của bạn dưới đây và chúng tôi sẽ gửi cho bạn một đường link để đặt lại mật khẩu.</p>

                <%-- Hiển thị thông báo chung sau khi submit --%>
                <c:if test="${not empty message}">
                    <div class="success-message">
                        <i class="fa-solid fa-paper-plane"></i> ${message}
                    </div>
                </c:if>

                <form action="forgot-password" method="post">
                    <div class="form-group">
                        <label for="email">Địa chỉ email đã đăng ký</label>
                        <div class="input-wrapper">
                            <i class="fa-solid fa-envelope input-icon"></i>
                            <input type="email" id="email" name="email" placeholder="Nhập địa chỉ email của bạn" required>
                        </div>
                    </div>
                    <button type="submit" class="form-submit-btn">Gửi Link Đặt Lại</button>
                </form>

                <div class="auth-switch">
                    <p>Đã nhớ ra mật khẩu? <a href="${pageContext.request.contextPath}/login">Quay lại Đăng nhập</a></p>
                </div>
            </div>
        </div>
    </div>
</body>
</html>