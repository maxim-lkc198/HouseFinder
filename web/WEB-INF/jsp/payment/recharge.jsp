<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
    <jsp:include page="/WEB-INF/jsp/common/header.jsp" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/dashboard.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/form.css">
    <title>Nạp Tiền Vào Tài Khoản</title>
</head>
<body>
    <main class="page-container">
        <div class="container">
        <div class="dashboard-grid">
<%-- Include sidebar điều hướng --%>
    <jsp:include page="/WEB-INF/jsp/user/common/sidebar.jsp" />
    <section class="dashboard-content">
        <h2>Nạp Tiền (Mô Phỏng)</h2>
        <p>Nhập số tiền bạn muốn nạp vào tài khoản. Số dư sẽ được cập nhật ngay lập tức.</p>
        <form action="recharge" method="post" class="styled-form">
        <div class="form-group">
            <label for="amount">Số tiền (VNĐ)</label>
            <input type="number" id="amount" name="amount" min="10000" step="1000" required>
        </div>
        <button type="submit" class="btn btn-accent">Xác Nhận Nạp Tiền</button>
        </form>
    </section>
    </div>
</div>
</main>
    <jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
</body>
</html>