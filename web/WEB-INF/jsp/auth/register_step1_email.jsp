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
    <title>Đăng Ký - Bước 1 - FindHouse</title>
</head>
<body class="page-auth">
    <div class="auth-container">
        <div class="auth-form-wrapper">
            <div class="auth-image-panel" style="background-image: url('${pageContext.request.contextPath}/image/auth/undraw_enter_nwx3.svg');"></div>
            <div class="auth-form-panel">
                <p class="subtitle">Bắt đầu hành trình của bạn</p>
                <h2>Tạo tài khoản FindHouse</h2>
                 <p class="input-hint" style="margin-bottom: 2rem;">Chỉ một vài bước nữa là bạn sẽ tìm thấy ngôi nhà mơ ước.</p>

                <c:if test="${not empty errorMessage}">
                    <div class="error-message"><i class="fa-solid fa-circle-exclamation"></i> ${errorMessage}</div>
                </c:if>

                <form action="register" method="post">
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
    
    <jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
</body>
</html>