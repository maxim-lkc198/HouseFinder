<%-- 
    Document   : footer
    Created on : Jun 14, 2025, 4:31:29 AM
    Author     : Maxim
--%>

<%-- /WEB-INF/jsp/common/footer.jsp --%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<footer class="site-footer">
    <div class="footer-container">
        <div class="footer-about">
            <h4>Về FindHouse</h4>
            <p>
                FindHouse là nền tảng kết nối người cho thuê và người cần thuê bất động sản một cách nhanh chóng,
                minh bạch và hiệu quả. Sứ mệnh của chúng tôi là đơn giản hóa quá trình tìm kiếm không gian sống lý tưởng của bạn.
            </p>
        </div>
        <div class="footer-links">
            <h4>Liên kết hữu ích</h4>
            <ul>
                <li><a href="${pageContext.request.contextPath}/terms-of-service">Điều khoản dịch vụ</a></li>
                <li><a href="${pageContext.request.contextPath}/privacy-policy">Chính sách bảo mật</a></li>
                <li><a href="${pageContext.request.contextPath}/faq">Câu hỏi thường gặp</a></li>
                <li><a href="${pageContext.request.contextPath}/contact">Hỗ trợ khách hàng</a></li>
            </ul>
        </div>
    </div>
    <div class="footer-copyright">
        <p>© <%= new java.util.Date().getYear() + 1900 %> FindHouse. All rights reserved.</p>
    </div>
</footer>