/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
// File: js/search_filter.js

document.addEventListener('DOMContentLoaded', function () {
    const searchContainer = document.querySelector('.search-container');
    if (!searchContainer) return;

    const dropdowns = searchContainer.querySelectorAll('.filter-dropdown');

    dropdowns.forEach(dropdown => {
        const toggle = dropdown.querySelector('.filter-dropdown-toggle');
        const menu = dropdown.querySelector('.filter-dropdown-menu');

        toggle.addEventListener('click', (event) => {
            event.stopPropagation();
            // Đóng tất cả các dropdown khác trước khi mở cái này
            dropdowns.forEach(d => {
                if (d !== dropdown) d.classList.remove('active');
            });
            dropdown.classList.toggle('active');
        });

        // QUAN TRỌNG: Ngăn menu tự đóng khi click vào bên trong nó
        menu.addEventListener('click', (event) => {
            event.stopPropagation();
        });
    });

    // Đóng tất cả dropdown khi click ra ngoài
    window.addEventListener('click', () => {
        dropdowns.forEach(d => d.classList.remove('active'));
    });


    // ---- Xử lý riêng cho Dropdown Checkbox (Loại nhà đất) ----
    const categoryDropdown = document.querySelector('.filter-dropdown[data-type="category"]');
    if (categoryDropdown) {
        const checkboxes = categoryDropdown.querySelectorAll('input[type="checkbox"]');
        const toggleButton = categoryDropdown.querySelector('.filter-dropdown-toggle');
        const clearBtn = categoryDropdown.querySelector('.clear-all');
        const selectAllBtn = categoryDropdown.querySelector('.select-all');

        function updateCategoryToggleText() {
            const selected = Array.from(checkboxes).filter(cb => cb.checked);
            if (selected.length === 0) {
                toggleButton.textContent = 'Loại nhà đất';
            } else if (selected.length === 1) {
                toggleButton.textContent = selected[0].parentElement.textContent.trim();
            } else {
                toggleButton.textContent = `${selected.length} loại nhà đất`;
            }
        }

        checkboxes.forEach(cb => {
            cb.addEventListener('change', updateCategoryToggleText);
        });

        clearBtn.addEventListener('click', () => {
            checkboxes.forEach(cb => cb.checked = false);
            updateCategoryToggleText();
        });
        
        selectAllBtn.addEventListener('click', () => {
            checkboxes.forEach(cb => cb.checked = true);
            updateCategoryToggleText();
        });
        
        updateCategoryToggleText(); // Cập nhật lần đầu khi tải trang
    }


    // ---- Xử lý riêng cho Dropdown Slider (Mức giá) ----
    const priceDropdown = document.querySelector('.filter-dropdown[data-type="price"]');
    if (priceDropdown) {
        const sliderMin = priceDropdown.querySelector('#price-slider-min');
        const sliderMax = priceDropdown.querySelector('#price-slider-max');
        const minValueDisplay = priceDropdown.querySelector('#price-min-value');
        const maxValueDisplay = priceDropdown.querySelector('#price-max-value');
        const sliderTrack = priceDropdown.querySelector('.slider-track-fill');

        function formatPrice(value) {
            const val = parseInt(value);
            if (val >= 1000000000) {
                return (val / 1000000000) + ' tỷ';
            }
            if (val >= 1000000) {
                return (val / 1000000) + ' triệu';
            }
            return new Intl.NumberFormat('vi-VN').format(val) + ' đ';
        }
        
        function updateSliderValues() {
            let minVal = parseInt(sliderMin.value);
            let maxVal = parseInt(sliderMax.value);
            
            // Đảm bảo min slider không vượt qua max slider
            if (minVal >= maxVal) {
                minVal = maxVal - parseInt(sliderMin.step);
                sliderMin.value = minVal;
            }
            
            minValueDisplay.textContent = formatPrice(minVal);
            maxValueDisplay.textContent = formatPrice(maxVal);

            // Cập nhật thanh màu xanh ở giữa
            const minPercent = (minVal / sliderMin.max) * 100;
            const maxPercent = (maxVal / sliderMax.max) * 100;
            sliderTrack.style.left = minPercent + '%';
            sliderTrack.style.width = (maxPercent - minPercent) + '%';
        }
        
        sliderMin.addEventListener('input', updateSliderValues);
        sliderMax.addEventListener('input', updateSliderValues);
        updateSliderValues(); // Cập nhật lần đầu
    }
    
    // Bạn có thể copy và sửa lại logic slider cho "Diện tích"
});

