<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/common/header.jsp" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/dashboard.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/form.css">
    <title>Xác Thực Tài Khoản</title>
</head>
<body>
    <main class="page-container">
        <div class="container">
            <div class="dashboard-grid">
                <%-- Include sidebar điều hướng --%>
                 <jsp:include page="/WEB-INF/jsp/user/common/sidebar.jsp" />

                <section class="dashboard-content">
                    <h2>Xác Thực Tài Khoản (KYC)</h2>
                    <p>Cung cấp thông tin bên dưới để nhận huy hiệu "Tài khoản tin cậy".</p>
                    
                    <form action="verification" method="post" enctype="multipart/form-data" class="styled-form">
                        <div class="form-group">
                            <label for="idCardNumber">Số CMND/CCCD</label>
                            <input type="text" id="idCardNumber" name="idCardNumber" required>
                        </div>
                        <div class="form-group">
                            <label for="idCardIssueDate">Ngày cấp</label>
                            <input type="date" id="idCardIssueDate" name="idCardIssueDate" required>
                        </div>
                        <div class="form-group">
                            <label for="idCardIssuePlace">Nơi cấp</label>
                            <input type="text" id="idCardIssuePlace" name="idCardIssuePlace" required>
                        </div>
                        
                        <div class="form-group">
                            <label for="frontIdCardImage">Ảnh mặt trước CMND/CCCD</label>
                            <input type="file" id="frontIdCardImage" name="frontIdCardImage" accept="image/*" required>
                        </div>
                        <div class="form-group">
                            <label for="backIdCardImage">Ảnh mặt sau CMND/CCCD</label>
                            <input type="file" id="backIdCardImage" name="backIdCardImage" accept="image/*" required>
                        </div>

                        <button type="submit" class="btn btn-primary">Gửi Thông Tin Xác Thực</button>
                    </form>
                </section>
            </div>
        </div>
    </main>
    <jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
</body>
</html>