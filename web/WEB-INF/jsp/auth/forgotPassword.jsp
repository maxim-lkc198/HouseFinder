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
            <div class="auth-image-panel" style="background-image: url('${pageContext.request.contextPath}/images/forgot-password-bg.jpg');"></div>
            <div class="auth-form-panel">
                <p class="subtitle">Đặt lại mật khẩu của bạn</p>
                <h2>Quên mật khẩu?</h2>
                
                <c:if test="${not empty message}">
                    <%-- Đây là thông báo sau khi gửi email thành công --%>
                    <div class="success-message">
                        <i class="fa-solid fa-paper-plane"></i> ${message}
                    </div>
                    <p class="input-hint" style="margin-top: 1rem;">Nếu bạn không nhận được email, vui lòng kiểm tra hộp thư Spam hoặc thử gửi lại sau 1 phút.</p>
                    
                    <%-- Nút Resend (có thể dùng JavaScript để disable trong 60s) --%>
                    <form action="forgot-password" method="post" style="margin-top: 1rem;">
                        <input type="hidden" name="email" value="${param.email}"> <%-- Giữ lại email đã nhập --%>
                        <button type="submit" class="btn btn-secondary" id="resendBtn">Gửi lại Email</button>
                    </form>
                </c:if>

                <c:if test="${empty message}">
                    <%-- Hiển thị form ban đầu --%>
                    <p class="input-hint" style="margin-bottom: 2rem;">Nhập email của bạn dưới đây và chúng tôi sẽ gửi cho bạn một đường link để đặt lại mật khẩu.</p>
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
                </c:if>
                
                <div class="auth-switch">
                    <p>Đã nhớ ra mật khẩu? <a href="${pageContext.request.contextPath}/login">Quay lại Đăng nhập</a></p>
                </div>
            </div>
        </div>
    </div>
    
    <script>
        // JS để disable nút resend trong 60 giây
        const resendBtn = document.getElementById('resendBtn');
        if (resendBtn) {
            let countdown = 60;
            resendBtn.disabled = true;
            resendBtn.textContent = 'Gửi lại sau (' + countdown + 's)';
            
            const interval = setInterval(() => {
                countdown--;
                resendBtn.textContent = 'Gửi lại sau (' + countdown + 's)';
                if (countdown <= 0) {
                    clearInterval(interval);
                    resendBtn.disabled = false;
                    resendBtn.textContent = 'Gửi lại Email';
                }
            }, 1000);
        }
    </script>
</body>
</html>