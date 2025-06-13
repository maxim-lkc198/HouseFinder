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
    <jsp:include page="/WEB-INF/jsp/common/auth_header.jsp" />
    <title>Đăng Ký - Bước 1</title>
</head>
<body>
    <div class="auth-container">
        <div class="auth-form-wrapper">
            <div class="auth-image-panel" style="background-image: url('${pageContext.request.contextPath}/images/register-bg.jpg');">
                <%-- Ảnh nền hiện đại --%>
            </div>
            <div class="auth-form-panel">
                <p class="subtitle">Bắt đầu hành trình của bạn</p>
                <h2>Tạo tài khoản FindHouse</h2>

                <c:if test="${not empty errorMessage}">
                    <div class="error-message">
                        <i class="fa-solid fa-circle-exclamation"></i> ${errorMessage}
                    </div>
                </c:if>

                <form action="register" method="post">
                    <%-- Dùng một hidden input để chỉ định action cho Servlet --%>
                    <input type="hidden" name="action" value="checkEmail">
                    
                    <div class="form-group">
                        <label for="email">Địa chỉ email</label>
                        <div class="input-wrapper">
                            <i class="fa-solid fa-envelope input-icon"></i>
                            <input type="email" id="email" name="email" placeholder="Nhập địa chỉ email của bạn" value="${param.email}" required>
                        </div>
                    </div>
                    <button type="submit" class="form-submit-btn">Tiếp tục với Email</button>
                </form>

                <div class="auth-switch">
                    <p>Bạn đã có tài khoản? <a href="${pageContext.request.contextPath}/login">Đăng nhập ngay</a></p>
                </div>
            </div>
        </div>
    </div>
</body>
</html>