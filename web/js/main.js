/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
// File: js/main.js

document.addEventListener('DOMContentLoaded', function() {
    // Logic chung cho tất cả các trang, ví dụ: xử lý menu user dropdown
    const userMenuTrigger = document.querySelector('.user-menu-trigger');
    const userMenuDropdown = document.querySelector('.user-menu .dropdown-content');

    if (userMenuTrigger && userMenuDropdown) {
        // Có thể thêm logic để menu đóng lại khi click ra ngoài
        document.addEventListener('click', function(event) {
            const isClickInside = userMenuTrigger.contains(event.target);
            if (!isClickInside) {
                // Nếu muốn menu tự đóng khi click ra ngoài, bạn có thể thêm logic ở đây
            }
        });
    }

    // Các hàm tiện ích có thể được định nghĩa ở đây
    // Ví dụ: hàm định dạng tiền tệ
    window.formatCurrency = function(number) {
        if (isNaN(number)) return "0 VNĐ";
        return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(number);
    };

});

