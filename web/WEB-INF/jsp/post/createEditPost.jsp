<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/common/header.jsp" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/form_wizard.css">
    <title>Đăng Tin Mới - FindHouse</title>
</head>
<body>
    <div class="container page-container">
        <form action="create-post" method="post" enctype="multipart/form-data" id="postWizardForm">
            
            <div class="wizard-nav">
                <button type="button" class="wizard-nav-item active" data-tab="tab-1">
                    <span>1</span> Thông tin Bất động sản
                </button>
                <button type="button" class="wizard-nav-item" data-tab="tab-2" disabled>
                    <span>2</span> Hình ảnh & Video
                </button>
                <button type="button" class="wizard-nav-item" data-tab="tab-3" disabled>
                    <span>3</span> Cấu hình Hiển thị
                </button>
                <button type="button" class="wizard-nav-item" data-tab="tab-4" disabled>
                    <span>4</span> Xác nhận & Thanh toán
                </button>
            </div>

            <div class="wizard-content">
                <%-- ============================ TAB 1: THÔNG TIN BẤT ĐỘNG SẢN ============================ --%>
                <div id="tab-1" class="wizard-tab active">
                    <%-- Phần 1.1: Địa chỉ --%>
                    <div class="form-section" id="section-location">
                        <h4>1.1 Địa chỉ bất động sản</h4>
                        <div class="form-grid-2">
                            <div class="form-group">
                                <label for="province">Tỉnh/Thành phố *</label>
                                <select id="province" name="province" required>
                                    <option value="">-- Chọn Tỉnh/Thành --</option>
                                    <c:forEach items="${provinces}" var="p"><option value="${p.id}">${p.name}</option></c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="district">Quận/Huyện *</label>
                                <input type="text" id="district" name="district" required>
                            </div>
                            <div class="form-group">
                                <label for="ward">Phường/Xã *</label>
                                <input type="text" id="ward" name="ward" required>
                            </div>
                            <div class="form-group">
                                <label for="street">Đường/Phố *</label>
                                <input type="text" id="street" name="street" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="addressDetail">Địa chỉ hiển thị trên tin đăng *</label>
                            <input type="text" id="addressDetail" name="addressDetail" placeholder="Địa chỉ sẽ tự động được tạo ở đây...">
                            <p class="input-hint">Bạn có thể chỉnh sửa lại địa chỉ này nếu cần.</p>
                        </div>
                    </div>

                    <%-- Phần 1.2: Thông tin chính --%>
                    <div class="form-section hidden" id="section-basics">
                        <h4>1.2 Thông tin cơ bản</h4>
                        <div class="form-grid-3">
                            <div class="form-group">
                                <label for="category">Loại BĐS *</label>
                                <select id="category" name="category" required>
                                    <option value="">-- Chọn Loại BĐS --</option>
                                    <c:forEach items="${categories}" var="cat"><option value="${cat.id}">${cat.name}</option></c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="price">Mức giá (VNĐ/tháng) *</label>
                                <input type="number" id="price" name="price" required min="0" placeholder="Ví dụ: 5000000">
                            </div>
                            <div class="form-group">
                                <label for="area">Diện tích (m²) *</label>
                                <input type="number" id="area" name="area" step="0.1" required min="1" placeholder="Ví dụ: 45.5">
                            </div>
                        </div>
                    </div>
                    
                    <%-- Phần 1.3: Thông tin bổ sung --%>
                    <div class="form-section hidden" id="section-details">
                        <h4>1.3 Thông tin bổ sung (tùy chọn)</h4>
                        <div class="form-grid-4">
                            <div class="form-group"><label for="bedrooms">Số phòng ngủ</label><input type="number" id="bedrooms" name="bedrooms" min="0"></div>
                            <div class="form-group"><label for="bathrooms">Số phòng tắm</label><input type="number" id="bathrooms" name="bathrooms" min="0"></div>
                            <div class="form-group"><label for="floors">Số tầng</label><input type="number" id="floors" name="floors" min="0"></div>
                            <div class="form-group"><label for="facade">Mặt tiền (m)</label><input type="number" id="facade" name="facade" min="0" step="0.1"></div>
                            <div class="form-group"><label for="houseDirection">Hướng nhà</label><select id="houseDirection" name="houseDirection"><option value="">Không xác định</option><option>Đông</option><option>Tây</option><option>Nam</option><option>Bắc</option></select></div>
                            <div class="form-group"><label for="balconyDirection">Hướng ban công</label><select id="balconyDirection" name="balconyDirection"><option value="">Không xác định</option><option>Đông</option><option>Tây</option><option>Nam</option><option>Bắc</option></select></div>
                        </div>
                        <h5>Nội thất & Tiện ích</h5>
                        <div class="checkbox-group-container">
                            <div class="amenity-group">
                                <h6>Nội thất</h6>
                                <label><input type="checkbox" name="furniture[]" value="dieu_hoa"> Điều hòa</label>
                                <label><input type="checkbox" name="furniture[]" value="nong_lanh"> Nóng lạnh</label>
                                <label><input type="checkbox" name="furniture[]" value="bep"> Bếp</label>
                                <label><input type="checkbox" name="furniture[]" value="giuong"> Giường</label>
                                <label><input type="checkbox" name="furniture[]" value="tu_quan_ao"> Tủ quần áo</label>
                            </div>
                            <div class="amenity-group">
                                <h6>Tiện ích chung</h6>
                                <label><input type="checkbox" name="amenities[]" value="cho_de_xe"> Chỗ để xe</label>
                                <label><input type="checkbox" name="amenities[]" value="thang_may"> Thang máy</label>
                                <label><input type="checkbox" name="amenities[]" value="internet"> Internet/Wifi</label>
                                <label><input type="checkbox" name="amenities[]" value="thu_cung"> Cho phép thú cưng</label>
                            </div>
                            <div class="amenity-group">
                                <h6>An toàn</h6>
                                <label><input type="checkbox" name="safety[]" value="bao_ve_24_7"> Bảo vệ 24/7</label>
                                <label><input type="checkbox" name="safety[]" value="pccc"> Hệ thống PCCC</label>
                                <label><input type="checkbox" name="safety[]" value="camera"> Camera an ninh</label>
                            </div>
                        </div>
                    </div>

                    <%-- Phần 1.4: Tiêu đề và Liên hệ --%>
                    <div class="form-section hidden" id="section-finalize">
                        <h4>1.4 Tiêu đề & Thông tin liên hệ</h4>
                        <div class="form-group">
                            <label for="postTitle">Tiêu đề tin đăng *</label>
                            <input type="text" id="postTitle" name="postTitle" required minlength="30" maxlength="99" placeholder="VD: Cho thuê căn hộ mini full nội thất Quận 1">
                        </div>
                        <div class="form-group">
                            <label for="postDescription">Mô tả chi tiết *</label>
                            <textarea id="postDescription" name="postDescription" rows="8" required minlength="30" maxlength="3000" placeholder="Mô tả chi tiết về vị trí, diện tích, tiện ích, tình trạng nội thất..."></textarea>
                        </div>
                        <div class="form-grid-3">
                            <div class="form-group">
                                <label for="contactName">Tên liên hệ *</label>
                                <input type="text" id="contactName" name="contactName" value="${sessionScope.loggedInUser.firstName} ${sessionScope.loggedInUser.lastName}" required>
                            </div>
                            <div class="form-group">
                                <label for="contactPhone">Số điện thoại *</label>
                                <input type="tel" id="contactPhone" name="contactPhone" value="${sessionScope.loggedInUser.phoneNumber}" required>
                            </div>
                            <div class="form-group">
                                <label for="contactEmail">Email</label>
                                <input type="email" id="contactEmail" name="contactEmail" value="${sessionScope.loggedInUser.email}">
                            </div>
                        </div>
                    </div>
                </div>

                <%-- ============================ TAB 2: HÌNH ẢNH ============================ --%>
                <div id="tab-2" class="wizard-tab">
                    <h4>Tải lên hình ảnh và video</h4>
                    <p>Tải lên ít nhất 1 ảnh, tối đa 10 ảnh. Click vào ngôi sao <i class="fa-solid fa-star"></i> để chọn ảnh làm ảnh đại diện.</p>
                    <div class="image-upload-zone" id="imageUploadZone">
                        <input type="file" name="images" multiple accept="image/*" id="imageUploadInput" style="display: none;">
                        <i class="fa-solid fa-cloud-arrow-up"></i>
                        <p>Kéo thả ảnh vào đây hoặc <strong>nhấn để chọn file</strong></p>
                    </div>
                    <div id="image-preview-area" class="image-preview-grid">
                        <%-- JS sẽ hiển thị preview ảnh ở đây --%>
                    </div>
                    <input type="hidden" name="thumbnailIdentifier" id="thumbnailIdentifier">
                </div>

                <%-- ============================ TAB 3: CẤU HÌNH ============================ --%>
                <div id="tab-3" class="wizard-tab">
                    <h4>Cấu hình hiển thị</h4>
                    <p>Lựa chọn loại tin và thời gian hiển thị phù hợp với nhu cầu của bạn.</p>
                    <div class="config-options-grid">
                        <div class="config-section">
                            <h5>Loại tin</h5>
                            <label class="config-option-box">
                                <input type="radio" name="listingType" value="NORMAL" checked>
                                <div class="option-content">
                                    <strong>Tin Thường</strong>
                                    <span>Hiển thị tiêu chuẩn</span>
                                </div>
                            </label>
                            <label class="config-option-box">
                                <input type="radio" name="listingType" value="VIP">
                                <div class="option-content">
                                    <strong>Tin VIP <i class="fa-solid fa-star" style="color: #ffc107;"></i></strong>
                                    <span>Luôn ở top, màu sắc nổi bật</span>
                                </div>
                            </label>
                        </div>
                        <div class="config-section">
                            <h5>Thời gian đăng</h5>
                            <label class="config-option-box">
                                <input type="radio" name="displayDuration" value="7">
                                <div class="option-content">
                                    <strong>7 ngày</strong>
                                    <span>(Giá/ngày: 2,000đ)</span>
                                </div>
                            </label>
                            <label class="config-option-box">
                                <input type="radio" name="displayDuration" value="15" checked>
                                <div class="option-content">
                                    <strong>15 ngày</strong>
                                    <span>(Giá/ngày: 1,800đ)</span>
                                </div>
                            </label>
                            <label class="config-option-box">
                                <input type="radio" name="displayDuration" value="30">
                                <div class="option-content">
                                    <strong>30 ngày</strong>
                                    <span>(Giá/ngày: 1,500đ)</span>
                                </div>
                            </label>
                        </div>
                    </div>
                </div>

                <%-- ============================ TAB 4: XÁC NHẬN ============================ --%>
                <div id="tab-4" class="wizard-tab">
                    <h4>Xác nhận và Thanh toán</h4>
                    <p>Vui lòng xem lại thông tin trước khi gửi duyệt. Sau khi gửi, bạn sẽ cần thanh toán (nếu là tin mua lẻ) hoặc sử dụng quyền lợi từ gói hội viên.</p>
                    <div class="invoice-summary">
                        <p><strong>Tiêu đề:</strong> <span id="summary-title"></span></p>
                        <p><strong>Địa chỉ:</strong> <span id="summary-address"></span></p>
                        <p><strong>Loại tin:</strong> <span id="summary-listing-type"></span></p>
                        <p><strong>Thời gian đăng:</strong> <span id="summary-duration"></span></p>
                        <hr>
                        <p class="final-price-label">Chi phí dự kiến:</p>
                        <p class="final-price" id="summary-price">0 VNĐ</p>
                        <p class="promo-text" id="summary-benefit-note" style="display:none; color: green; font-weight: 500;"></p>
                    </div>
                </div>
            </div>

            <div class="wizard-footer">
                <button type="button" id="prevBtn" class="btn btn-secondary" style="display: none;">Quay lại</button>
                <button type="button" id="nextBtn" class="btn btn-primary">Tiếp theo</button>
                <button type="submit" id="submitBtn" class="btn btn-accent" style="display: none;">Xác nhận & Gửi Duyệt</button>
            </div>
        </form>
    </div>
    
    <jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
    <script src="${pageContext.request.contextPath}/js/post_wizard.js"></script>
</body>
</html>