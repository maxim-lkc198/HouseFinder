<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/home.css?v=1.0">
    <title>FindHouse - Tìm kiếm nhà đất, phòng trọ, căn hộ</title>
</head>
<body class="page-home">
    <jsp:include page="/WEB-INF/jsp/common/header.jsp" />
    <main>
        <%-- PHẦN HERO VÀ THANH TÌM KIẾM --%>
        <section class="hero-section">
            <div class="hero-content">
                <h1>Tìm Kiếm Ngôi Nhà Mơ Ước Của Bạn</h1>
                <p>Nền tảng tìm kiếm và cho thuê bất động sản minh bạch, hiệu quả hàng đầu Việt Nam</p>
            </div>
            <%-- Trong home.jsp --%>
<div class="search-container">
    <h4 class="search-title">Tìm kiếm nhà cho thuê</h4>
    <form action="${pageContext.request.contextPath}/posts" method="get" class="search-form">
        <div class="search-main-input">
            <div class="input-group location-input">
                <i class="fa-solid fa-location-dot"></i>
                <select name="provinceId">
                    <option value="">Toàn quốc</option>
                    <c:forEach items="${provinces}" var="p">
                        <option value="${p.id}">${p.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="input-group keyword-input">
                <i class="fa-solid fa-magnifying-glass"></i>
                <input type="text" name="keyword" placeholder="Tìm kiếm theo từ khóa (sắp có)" disabled>
            </div>
            <button type="submit" class="btn btn-search">Tìm kiếm</button>
        </div>
        <div class="search-filters">
            <%-- Filter 1: Loại nhà đất (Checkbox Dropdown) --%>
            <div class="filter-dropdown">
                <button type="button" class="filter-dropdown-btn">
                    <span>Loại nhà đất</span>
                    <i class="fa-solid fa-chevron-down"></i>
                </button>
                <div class="filter-dropdown-content checkbox-list">
                    <div class="dropdown-actions">
                        <span class="choose-all">Chọn tất cả</span>
                        <span class="clear-all">Bỏ chọn</span>
                    </div>
                    <c:forEach items="${categories}" var="cat">
                        <label><input type="checkbox" name="categoryId" value="${cat.id}"> ${cat.name}</label>
                    </c:forEach>
                </div>
            </div>
            <%-- Filter 2: Mức giá (Slider mô phỏng) --%>
            <div class="filter-dropdown">
                <button type="button" class="filter-dropdown-btn">
                    <span>Mức giá</span>
                    <i class="fa-solid fa-chevron-down"></i>
                </button>
                 <div class="filter-dropdown-content range-slider">
                    <label for="priceMin">Từ</label>
                    <input type="number" name="priceMin" placeholder="0" min="0">
                     <label for="priceMax">Đến</label>
                    <input type="number" name="priceMax" placeholder="Không giới hạn">
                    <%-- Placeholder for a real slider --%>
                </div>
            </div>
            <%-- Filter 3: Diện tích (Slider mô phỏng) --%>
            <div class="filter-dropdown">
                 <button type="button" class="filter-dropdown-btn">
                    <span>Diện tích</span>
                    <i class="fa-solid fa-chevron-down"></i>
                </button>
                <div class="filter-dropdown-content range-slider">
                    <label for="areaMin">Từ (m²)</label>
                    <input type="number" name="areaMin" placeholder="0" min="0">
                     <label for="areaMax">Đến (m²)</label>
                    <input type="number" name="areaMax" placeholder="Không giới hạn">
                </div>
            </div>
        </div>
    </form>
</div>
        </section>

        <%-- PHẦN NỘI DUNG CHÍNH --%>
        <div class="main-content-container">
             <section class="featured-listings">
                <div class="container">
                    <div class="section-header">
                        <h2>Tin Cho Thuê Nổi Bật</h2>
                        <a href="${pageContext.request.contextPath}/posts" class="view-more-link">Xem thêm <i class="fa-solid fa-arrow-right"></i></a>
                    </div>
                    <div class="listing-grid">
                        <c:choose>
                            <c:when test="${not empty recentPosts}">
                                <c:forEach items="${recentPosts}" var="post">
                                    <div class="listing-card">
                                        <div class="card-image">
                                            <a href="post-detail?id=${post.id}">
                                                <img src="${not empty post.thumbnailUrl ? post.thumbnailUrl : 'https://via.placeholder.com/300x200.png?text=FindHouse'}" alt="${post.title}">
                                            </a>
                                            <button class="favorite-btn"><i class="fa-regular fa-heart"></i></button>
                                        </div>
                                        <div class="card-body">
                                            <h3 class="card-title"><a href="post-detail?id=${post.id}">${post.title}</a></h3>
                                            <div class="card-price"><fmt:formatNumber value="${post.price}" type="number"/> VNĐ/tháng</div>
                                            <div class="card-details">
                                                <span><i class="fa-solid fa-ruler-combined"></i> ${post.area} m²</span>
                                                <span><i class="fa-solid fa-bed"></i> ${post.bedrooms} PN</span>
                                                <span><i class="fa-solid fa-bath"></i> ${post.bathrooms} WC</span>
                                            </div>
                                            <div class="card-location">
                                                <i class="fa-solid fa-map-marker-alt"></i> ${post.addressDistrict}, ${post.province.name}
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <p>Hiện chưa có tin đăng nào. Hãy là người đầu tiên đăng tin!</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </section>
            <%-- Các section khác như location-showcase, cta-section giữ nguyên --%>
        </div>
    </main>
    
    <jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
</body>
</html>