<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <h2>User Registration</h2>
        <c:if test="${not empty registerError}">
            <p class="error-message">${registerError}</p>
        </c:if>
        <form action="${pageContext.request.contextPath}/register" method="post">
            <div>
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" value="${param.username}" required>
            </div>
            <div>
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required>
            </div>
            <div>
                <label for="fullname">Full Name:</label>
                <input type="text" id="fullname" name="fullname" value="${param.fullname}" required>
            </div>
            <div>
                <button type="submit">Register</button>
            </div>
        </form>
        <p>Already have an account? <a href="${pageContext.request.contextPath}/login">Login here</a></p>
    </div>
</body>
</html>