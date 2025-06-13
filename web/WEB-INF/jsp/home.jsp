<%-- 
    Document   : home
    Created on : Jun 13, 2025, 12:26:42 PM
    Author     : Maxim
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <%-- Include header chứa các thẻ meta, link CSS, Font Awesome, Google Fonts --%>
    <title>FindHouse - Nền Tảng Tìm Kiếm Nhà Cho Thuê</title>
    <%-- CSS riêng cho trang chủ nếu cần --%>
    <style>
        .hero-section {
            background-image: linear-gradient(rgba(0,0,0,0.3), rgba(0,0,0,0.1)), url('${pageContext.request.contextPath}/images/home-hero-bg.jpg');
            background-size: cover;
            background-position: center;
            padding: 6rem 2rem;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .search-box-container {
            background-color: rgba(45, 45, 45, 0.85);
            padding: 2rem;
            border-radius: 8px;
            width: 100%;
            max-width: 900px;
            backdrop-filter: blur(5px);
        }
        .search-tabs {
            margin-bottom: 1.5rem;
        }
        .search-tab {
            padding: 0.8rem 1.5rem;
            border: none;
            background-color: #555;
            color: #ccc;
            border-radius: 5px 5px 0 0;
            font-size: 1rem;
            font-weight: 500;
        }
        .search-tab.active {
            background-color: #fff;
            color: #333;
        }
        .search-main {
            background-color: #fff;
            border-radius: 5px;
            padding: 1rem;
        }
        .search-inputs {
            display: grid;
            grid-template-columns: 1fr 2fr;
            gap: 1rem;
            margin-bottom: 1rem;
        }
        .search-inputs .location-input, .search-inputs .keyword-input {
            display: flex;
            align-items: center;
            border: 1px solid #ccc;
            border-radius: 5px;
            padding: 0 0.5rem;
        }
        .search-inputs input, .search-inputs select {
            border: none;
            outline: none;
            width: 100%;
            padding: 0.8rem 0.5rem;
            font-size: 1rem;
        }
        .search-filters {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
            gap: 1rem;
        }
        .search-filters select {
            width: 100%;
            padding: 0.8rem;
            border: 1px solid #ccc;
            border-radius: 5px;
            background-color: #f8f9fa;
        }
        .search-button-wrapper {
            margin-left: 1rem;
            display: flex;
            align-items: flex-end; /* Căn nút Tìm kiếm với các dropdown */
        }
        .featured-section {
            padding: 4rem 2rem;
            max-width: 1200px;
            margin: auto;
        }
        .section-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 2rem;
            border-bottom: 2px solid var(--primary-color);
            padding-bottom: 0.5rem;
        }
        .section-header h2 {
            font-size: 1.8rem;
            margin: 0;
        }
        .section-header .view-more {
            text-decoration: none;
            color: var(--primary-color);
            font-weight: 500;
        }
    </style>
</head>
<body>
    <%-- Header chính của trang web (sẽ có logic hiển thị khác nhau cho Guest và User) --%>
    <jsp:include page="/WEB-INF/jsp/common/main_header.jsp" />

    <main>
        <%-- Hero Section với Search Box --%>
        <section class="hero-section">
            <div class="search-box-container">
                <div class="search-tabs">
                    <button class="search-tab active">Nhà đất cho thuê</button>
                    <%-- Các tab khác bị vô hiệu hóa hoặc ẩn đi --%>
                </div>
                <div class="search-main">
                    <form action="posts" method="get"> <%-- Action trỏ đến PostServlet (url-pattern là /posts) --%>
                        <div style="display: flex;">
                            <div style="flex-grow: 1;">
                                <div class="search-inputs">
                                    <div class="location-input">
                                        <i class="fa-solid fa-location-dot" style="margin-right: 8px; color: #757575;"></i>
                                        <select name="provinceId">
                                            <option value="">Toàn quốc</option>
                                            <c:forEach items="${provinces}" var="p">
                                                <option value="${p.id}">${p.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="keyword-input">
                                        <i class="fa-solid fa-magnifying-glass" style="margin-right: 8px; color: #757575;"></i>
                                        <input type="text" name="keyword" placeholder="Chức năng đang phát triển (Coming Soon)" disabled>
                                    </div>
                                </div>
                                <div class="search-filters">
                                    <select name="categoryId">
                                        <option value="">Loại nhà đất</option>
                                         <c:forEach items="${categories}" var="cat">
                                            <option value="${cat.id}">${cat.name}</option>
                                        </c:forEach>
                                    </select>
                                    <select name="priceRange">
                                        <option value="">Mức giá</option>
                                        <option value="0-3000000">Dưới 3 triệu</option>
                                        <option value="3000000-5000000">3 - 5 triệu</option>
                                        <option value="5000000-10000000">5 - 10 triệu</option>
                                        <%-- Thêm các khoảng giá khác --%>
                                    </select>
                                    <select name="areaRange">
                                        <option value="">Diện tích</option>
                                        <option value="0-30">Dưới 30 m²</option>
                                        <option value="30-50">30 - 50 m²</option>
                                        <option value="50-80">50 - 80 m²</option>
                                        <%-- Thêm các khoảng diện tích khác --%>
                                    </select>
                                </div>
                            </div>
                            <div class="search-button-wrapper">
                                <button type="submit" class="btn btn-primary" style="padding: 2.2rem 1.5rem; height: 100%;">
                                    <i class="fa-solid fa-magnifying-glass"></i> Tìm kiếm
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </section>

        <%-- Featured Section --%>
        <section class="featured-section">
            <div class="section-header">
                <h2>Tin Cho Thuê Nổi Bật</h2>
                <a href="${pageContext.request.contextPath}/posts" class="view-more">Xem thêm <i class="fa-solid fa-arrow-right"></i></a>
            </div>
            <div class="post-grid">
                <%-- Logic lặp qua các bài đăng mới nhất (recentPosts) sẽ được thêm ở đây --%>
                <%-- Ví dụ một card --%>
                <%--
                <c:forEach items="${recentPosts}" var="post">
                    <div class="post-card">
                        <a href="post-detail?id=${post.id}">
                            <img src="${post.thumbnailUrl}" alt="${post.title}">
                            <h3 class="post-title">${post.title}</h3>
                            <p class="post-price">${post.price} VNĐ/tháng</p>
                            <p class="post-address">${post.addressDetail}, ${post.district}, ${post.province.name}</p>
                        </a>
                    </div>
                </c:forEach>
                --%>
                <p>Phần hiển thị các bài đăng nổi bật sẽ được hoàn thiện ở các bước tiếp theo.</p>
            </div>
        </section>
    </main>
    
</body>
</html>