<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/common/header.jsp" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/dashboard.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/form.css">
    <title>Chỉnh Sửa Thông Tin Cá Nhân</title>
</head>
<body>
    <main class="page-container">
        <div class="container">
            <div class="dashboard-grid">
                <%-- Include sidebar điều hướng (giống myAccount.jsp) --%>
                <jsp:include page="/WEB-INF/jsp/user/common/sidebar.jsp" />

                <section class="dashboard-content">
                    <h2>Chỉnh Sửa Thông Tin Cá Nhân</h2>
                    <p>Cập nhật thông tin của bạn để mọi người có thể liên lạc dễ dàng hơn.</p>
                    
                    <form action="my-profile-settings" method="post" class="styled-form">
                        <div class="form-group">
                            <label for="firstName">Họ</label>
                            <input type="text" id="firstName" name="firstName" value="${sessionScope.loggedInUser.firstName}" required>
                        </div>
                        <div class="form-group">
                            <label for="lastName">Tên</label>
                            <input type="text" id="lastName" name="lastName" value="${sessionScope.loggedInUser.lastName}" required>
                        </div>
                         <div class="form-group">
                            <label for="phoneNumber">Số điện thoại</label>
                            <input type="tel" id="phoneNumber" name="phoneNumber" value="${sessionScope.loggedInUser.phoneNumber}" required>
                        </div>
                        <div class="form-group">
                            <label for="dob">Ngày sinh</label>
                            <input type="date" id="dob" name="dob" value="${sessionScope.loggedInUser.dob}">
                        </div>
                        <div class="form-group">
                            <label for="address">Địa chỉ</label>
                            <input type="text" id="address" name="address" value="${sessionScope.loggedInUser.address}">
                        </div>
                         <div class="form-group">
                            <label for="avatar">Ảnh đại diện (URL)</label>
                            <input type="text" id="avatar" name="avatarUrl" value="${sessionScope.loggedInUser.avatarUrl}" placeholder="Dán link ảnh đại diện của bạn">
                        </div>
                        <button type="submit" class="btn btn-primary">Lưu Thay Đổi</button>
                    </form>
                </section>
            </div>
        </div>
    </main>
    <jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
</body>
</html>