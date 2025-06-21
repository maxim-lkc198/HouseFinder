<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/common/header.jsp" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/dashboard.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/form.css">
    <title>Thay Đổi Mật Khẩu</title>
</head>
<body>
    <main class="page-container">
        <div class="container">
            <div class="dashboard-grid">
                <%-- Include sidebar điều hướng --%>
                 <jsp:include page="/WEB-INF/jsp/user/common/sidebar.jsp" />

                <section class="dashboard-content">
                    <h2>Thay Đổi Mật Khẩu</h2>
                    <p>Để bảo vệ tài khoản, vui lòng không chia sẻ mật khẩu cho người khác.</p>
                    
                     <c:if test="${not empty errorMessage}"><div class="error-message">${errorMessage}</div></c:if>
                     <c:if test="${not empty successMessage}"><div class="success-message">${successMessage}</div></c:if>

                    <form action="change-password" method="post" class="styled-form">
                        <div class="form-group">
                            <label for="currentPassword">Mật khẩu hiện tại</label>
                            <input type="password" id="currentPassword" name="currentPassword" required>
                        </div>
                        <div class="form-group">
                            <label for="newPassword">Mật khẩu mới</label>
                            <input type="password" id="newPassword" name="newPassword" required>
                        </div>
                        <div class="form-group">
                            <label for="confirmNewPassword">Xác nhận mật khẩu mới</label>
                            <input type="password" id="confirmNewPassword" name="confirmNewPassword" required>
                        </div>
                        <button type="submit" class="btn btn-primary">Cập Nhật Mật Khẩu</button>
                    </form>
                </section>
            </div>
        </div>
    </main>
    <jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
</body>
</html>