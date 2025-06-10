<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Home Page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <c:if test="${sessionScope.currentUser != null}">
            <h2>Welcome, <c:out value="${sessionScope.currentUser.fullname}"/>!</h2>
            <p>This is your home page.</p>
            <p>Your username: <c:out value="${sessionScope.currentUser.username}"/></p>
            <p><a href="${pageContext.request.contextPath}/logout">Logout</a></p>
        </c:if>
        <c:if test="${sessionScope.currentUser == null}">
             <p>You are not logged in. Please <a href="${pageContext.request.contextPath}/login">login</a>.</p>
        </c:if>
    </div>
</body>
</html>