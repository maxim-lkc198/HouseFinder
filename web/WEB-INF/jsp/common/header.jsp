<%-- home.jsp (PHIÊN BẢN LAYOUT TUẦN TỰ) --%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <jsp:include page="/WEB-INF/jsp/common/header.jsp" />
    <title>FindHouse - Tìm kiếm nhà đất, phòng trọ, căn hộ</title>
</head>
<body>
    <main>
        <%-- PHẦN HERO CHỈ CÒN TIÊU ĐỀ --%>
        <section class="hero-section">
            <div class="hero-content">
                <h1>Tìm kiếm ngôi nhà mơ ước của bạn</h1>
                <p>Nền tảng tìm kiếm và cho thuê bất động sản hàng đầu Việt Nam</p>
            </div>
        </section>

        <%-- THANH TÌM KIẾM GIỜ LÀ MỘT SECTION RIÊNG BIỆT --%>
        <section class="search-section">
            <div class="search-container">
                <h4 class="search-title">Tìm kiếm nhà cho thuê</h4>
                <div class="search-bar">
                    <div class="input-group">
                        <i class="fa-solid fa-location-dot"></i>
                        <input type="text" placeholder="Hà Nội">
                    </div>
                     <div class="input-group" style="flex-grow: 2;">
                        <i class="fa-solid fa-magnifying-glass"></i>
                        <input type="text" placeholder="Nhập địa điểm, dự án. Ví dụ: Quận Hoàn Kiếm">
                    </div>
                    <button class="btn btn-search">Tìm kiếm</button>
                </div>
                <div class="search-filters">
                    <select> <option>Loại nhà đất</option> </select>
                    <select> <option>Mức giá</option> </select>
                    <select> <option>Diện tích</option> </select>
                </div>
            </div>
        </section>

        <%-- PHẦN NỘI DUNG CHÍNH (Tin nổi bật, Khám phá...) --%>
        <div class="main-content">
            <section class="featured-listings">
                <div class="container">
                    <div class="section-header">
                        <h2>Nhà cho thuê nổi bật</h2>
                        <a href="#" class="view-more-link">Xem thêm <i class="fa-solid fa-arrow-right"></i></a>
                    </div>
                    <div class="listing-grid">
                        <% for(int i = 0; i < 8; i++) { %>
                        <div class="listing-card">
                            <div class="card-image">
                                <img src="https://images.unsplash.com/photo-1570129477492-45c003edd2be?q=80&w=2070&auto=format&fit=crop" alt="House Image">
                                <button class="favorite-btn"><i class="fa-regular fa-heart"></i></button>
                            </div>
                            <div class="card-body">
                                <h3 class="card-title"><a href="#">Cho thuê căn hộ mini full nội thất Quận 1</a></h3>
                                <div class="card-price">5 triệu/tháng</div>
                                <div class="card-details">
                                    <span><i class="fa-solid fa-ruler-combined"></i> 35 m²</span>
                                    <span><i class="fa-solid fa-bed"></i> 1 PN</span>
                                    <span><i class="fa-solid fa-bath"></i> 1 WC</span>
                                </div>
                                <div class="card-location">
                                    <i class="fa-solid fa-map-marker-alt"></i> Phường Bến Nghé, Quận 1, TP. Hồ Chí Minh
                                </div>
                            </div>
                        </div>
                        <% } %>
                    </div>
                </div>
            </section>
            
            <section class="location-showcase">
                <div class="container">
                     <div class="section-header">
                        <h2>Khám phá theo khu vực</h2>
                    </div>
                    <div class="location-grid">
                        <a href="#" class="location-card">
                            <img src="https://images.unsplash.com/photo-1596436826649-5a82a46a234a?q=80&w=1974&auto=format&fit=crop" alt="Hà Nội">
                            <div class="location-name">Hà Nội</div>
                        </a>
                        <a href="#" class="location-card">
                            <img src="https://images.unsplash.com/photo-1583417319070-4a69db38a432?q=80&w=2070&auto=format&fit=crop" alt="TP. Hồ Chí Minh">
                             <div class="location-name">TP. Hồ Chí Minh</div>
                        </a>
                        <a href="#" class="location-card">
                            <img src="https://images.unsplash.com/photo-1563492065599-3520f775ee05?q=80&w=1974&auto=format&fit=crop" alt="Đà Nẵng">
                             <div class="location-name">Đà Nẵng</div>
                        </a>
                    </div>
                </div>
            </section>

            <section class="cta-section">
                <div class="container cta-container">
                    <div class="cta-text">
                        <h2>Bạn có bất động sản cho thuê?</h2>
                        <p>Tiếp cận hàng triệu khách hàng tiềm năng mỗi tháng một cách dễ dàng và hiệu quả cùng FindHouse.</p>
                        <a href="${pageContext.request.contextPath}/create-post" class="btn btn-cta">Đăng tin ngay</a>
                    </div>
                    <div class="cta-image">
                        <img src="${pageContext.request.contextPath}/images/auth/register.svg" alt="Đăng tin ngay">
                    </div>
                </div>
            </section>
        </div>
    </main>
    
    <jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
</body>
</html>