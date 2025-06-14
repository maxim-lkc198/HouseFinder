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
    <title>Quên Mật Khẩu - FindHouse</title>
</head>
<body class="page-auth">
    <div class="auth-container">
        <div class="auth-form-wrapper">
            <div class="auth-image-panel" style="background-image: url('${pageContext.request.contextPath}/image/auth/undraw_forgot-password_odai.svg');"></div>
            <div class="auth-form-panel">
                <p class="subtitle">Đặt lại mật khẩu</p>
                <h2>Quên mật khẩu?</h2>
                
                <c:if test="${not empty message}">
                    <div class="success-message">
                        <i class="fa-solid fa-paper-plane"></i> ${message}
                    </div>
                    <p class="input-hint" style="margin-top: 1rem;">Nếu không nhận được email, vui lòng kiểm tra thư mục Spam hoặc thử lại sau 1 phút.</p>
                    <form action="forgot-password" method="post" style="margin-top: 1rem;">
                        <input type="hidden" name="email" value="${param.email}">
                        <button type="submit" class="btn btn-secondary" id="resendBtn" style="width: 100%; padding: 14px;">Gửi lại Email</button>
                    </form>
                </c:if>

                <c:if test="${empty message}">
                    <p class="input-hint" style="margin-bottom: 2rem;">Đừng lo lắng! Nhập email của bạn dưới đây, chúng tôi sẽ gửi một liên kết để bạn đặt lại mật khẩu.</p>
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
                    <p>Đã nhớ ra? <a href="${pageContext.request.contextPath}/login">Quay lại Đăng nhập</a></p>
                </div>
            </div>
        </div>
    </div>
    
    <jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
    <script>
        const resendBtn = document.getElementById('resendBtn');
        if (resendBtn) {
            let countdown = 60;
            const originalText = resendBtn.textContent;
            
            const disableButton = () => {
                resendBtn.disabled = true;
                resendBtn.textContent = 'Gửi lại sau (' + countdown + 's)';
            };

            const interval = setInterval(() => {
                countdown--;
                if (countdown <= 0) {
                    clearInterval(interval);
                    resendBtn.disabled = false;
                    resendBtn.textContent = originalText;
                } else {
                    resendBtn.textContent = 'Gửi lại sau (' + countdown + 's)';
                }
            }, 1000);

            disableButton(); // Call it once immediately
        }
    </script>
</body>
</html>