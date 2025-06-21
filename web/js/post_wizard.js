document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('postWizardForm');
    if (!form) return;

    const tabs = Array.from(document.querySelectorAll('.wizard-tab'));
    const navItems = Array.from(document.querySelectorAll('.wizard-nav-item'));
    let currentTab = 0;

    // Lấy danh sách các section trong tab 1
    const sections = Array.from(document.querySelectorAll('#tab-1 .form-section'));
    let currentSectionIndex = 0;

    // Ẩn tất cả các section trừ section đầu tiên khi khởi tạo
    sections.forEach((section, index) => {
        if (index > 0) section.classList.add('hidden');
    });

    function showTab(n) {
        tabs.forEach((tab, index) => {
            tab.style.display = (index === n) ? 'block' : 'none';
        });
        navItems.forEach((navItem, index) => {
            navItem.classList.toggle('active', index === n);
            navItem.classList.toggle('completed', index < n);
        });

        const prevBtn = document.getElementById('prevBtn');
        const nextBtn = document.getElementById('nextBtn');
        const submitBtn = document.getElementById('submitBtn');

        prevBtn.style.display = (n === 0) ? 'none' : 'inline-block';
        nextBtn.style.display = (n === tabs.length - 1) ? 'none' : 'inline-block';
        submitBtn.style.display = (n === tabs.length - 1) ? 'inline-block' : 'none';
    }

    function navigate(n) {
        if (n > 0 && !validateCurrentTab()) return;
        currentTab += n;
        if (currentTab >= tabs.length) {
            form.submit();
            return;
        }
        showTab(currentTab);
    }

    function validateCurrentTab() {
        if (currentTab === 0) {
            // Kiểm tra tất cả các section trong tab 1
            return sections.every(section => {
                const requiredInputs = section.querySelectorAll('input[required], select[required], textarea[required]');
                return Array.from(requiredInputs).every(input => input.value.trim() !== '');
            });
        } else if (currentTab === 1) {
            // Kiểm tra số lượng ảnh trong tab 2
            const imageCount = document.getElementById('image-preview-area').children.length;
            if (imageCount < 1 || imageCount > 10) {
                alert('Vui lòng tải lên từ 1 đến 10 ảnh.');
                return false;
            }
            return true;
        } else {
            const currentTabElement = tabs[currentTab];
            const inputs = currentTabElement.querySelectorAll('input[required], select[required], textarea[required]');
            return Array.from(inputs).every(input => input.value.trim() !== '');
        }
    }

    // Hàm kiểm tra và hiển thị section tiếp theo trong tab 1
    function showNextSection() {
        const currentSection = sections[currentSectionIndex];
        const requiredInputs = currentSection.querySelectorAll('input[required], select[required], textarea[required]');
        const allFilled = Array.from(requiredInputs).every(input => input.value.trim() !== '');

        if (allFilled && currentSectionIndex < sections.length - 1) {
            currentSectionIndex++;
            sections[currentSectionIndex].classList.remove('hidden');
            sections[currentSectionIndex].scrollIntoView({ behavior: 'smooth' });
        }
    }

    // Gắn sự kiện cho tất cả các input trong các section của tab 1
    sections.forEach(section => {
        section.querySelectorAll('input, select, textarea').forEach(input => {
            input.addEventListener('input', showNextSection);
            input.addEventListener('change', showNextSection);
        });
    });

    // Tự động ghép địa chỉ
    function updateAddressDetail() {
        const province = document.getElementById('province').selectedOptions[0]?.text || '';
        const district = document.getElementById('district').value || '';
        const ward = document.getElementById('ward').value || '';
        const street = document.getElementById('street').value || '';
        const addressDetail = [street, ward, district, province].filter(Boolean).join(', ');
        document.getElementById('addressDetail').value = addressDetail;
    }

    document.getElementById('province')?.addEventListener('change', updateAddressDetail);
    document.getElementById('district')?.addEventListener('input', updateAddressDetail);
    document.getElementById('ward')?.addEventListener('input', updateAddressDetail);
    document.getElementById('street')?.addEventListener('input', updateAddressDetail);

    // Xử lý nút điều hướng
    document.getElementById('nextBtn')?.addEventListener('click', () => navigate(1));
    document.getElementById('prevBtn')?.addEventListener('click', () => navigate(-1));

    // Tab 2: Tải ảnh
    const imageUploadInput = document.getElementById('imageUploadInput');
    const imagePreviewArea = document.getElementById('image-preview-area');
    const thumbnailIdentifier = document.getElementById('thumbnailIdentifier');
    let uploadedImages = [];

    imageUploadInput.addEventListener('change', (event) => {
        const files = event.target.files;
        if (files.length + uploadedImages.length > 10) {
            alert('Bạn chỉ có thể tải lên tối đa 10 ảnh!');
            return;
        }

        Array.from(files).forEach(file => {
            if (!file.type.startsWith('image/')) return;

            const reader = new FileReader();
            reader.onload = (e) => {
                const imgWrapper = document.createElement('div');
                imgWrapper.classList.add('image-preview-item');

                const img = document.createElement('img');
                img.src = e.target.result;
                img.alt = 'Preview';

                const starIcon = document.createElement('i');
                starIcon.classList.add('fa-solid', 'fa-star');
                starIcon.addEventListener('click', () => {
                    document.querySelectorAll('.image-preview-item .fa-star').forEach(icon => icon.classList.remove('selected'));
                    starIcon.classList.add('selected');
                    thumbnailIdentifier.value = file.name;
                });

                const deleteIcon = document.createElement('i');
                deleteIcon.classList.add('fa-solid', 'fa-trash');
                deleteIcon.addEventListener('click', () => {
                    imgWrapper.remove();
                    uploadedImages = uploadedImages.filter(f => f.name !== file.name);
                    if (thumbnailIdentifier.value === file.name) {
                        thumbnailIdentifier.value = uploadedImages.length > 0 ? uploadedImages[0].name : '';
                    }
                });

                imgWrapper.appendChild(img);
                imgWrapper.appendChild(starIcon);
                imgWrapper.appendChild(deleteIcon);
                imagePreviewArea.appendChild(imgWrapper);

                uploadedImages.push(file);
                if (uploadedImages.length === 1) starIcon.click();
            };
            reader.readAsDataURL(file);
        });
    });

    const imageUploadZone = document.getElementById('imageUploadZone');
    imageUploadZone.addEventListener('click', () => imageUploadInput.click());
    imageUploadZone.addEventListener('dragover', (e) => {
        e.preventDefault();
        imageUploadZone.classList.add('dragover');
    });
    imageUploadZone.addEventListener('dragleave', () => {
        imageUploadZone.classList.remove('dragover');
    });
    imageUploadZone.addEventListener('drop', (e) => {
        e.preventDefault();
        imageUploadZone.classList.remove('dragover');
        imageUploadInput.files = e.dataTransfer.files;
        imageUploadInput.dispatchEvent(new Event('change'));
    });

    // Tab 3: Cấu hình và tính toán chi phí
    function updateCost() {
        const listingType = document.querySelector('input[name="listingType"]:checked').value;
        const duration = parseInt(document.querySelector('input[name="displayDuration"]:checked').value);
        const basePricePerDay = 2000; // Giá cơ bản cho tin thường
        const vipMultiplier = 50; // Nhân 50 cho tin VIP
        const discountRates = {7: 0, 15: 10, 30: 20}; // Phần trăm discount

        let pricePerDay = listingType === 'VIP' ? basePricePerDay * vipMultiplier : basePricePerDay;
        let discount = discountRates[duration] || 0;
        let totalCost = pricePerDay * duration * (1 - discount / 100);

        // Hiển thị chi phí trong tab 3
        document.getElementById('pricePerDay').textContent = pricePerDay.toLocaleString() + ' VNĐ';
        document.getElementById('totalCost').textContent = totalCost.toLocaleString() + ' VNĐ';
        document.getElementById('discountNote').textContent = discount > 0 ? `(-${discount}%)` : '';
    }

    document.querySelectorAll('input[name="listingType"], input[name="displayDuration"]').forEach(input => {
        input.addEventListener('change', updateCost);
    });
    updateCost(); // Cập nhật ban đầu

    // Tab 4: Tóm tắt và thanh toán
    function updateSummary() {
        const postTitle = document.getElementById('postTitle').value || 'Chưa nhập';
        const addressDetail = document.getElementById('addressDetail').value || 'Chưa nhập';
        const listingType = document.querySelector('input[name="listingType"]:checked').value;
        const displayDuration = document.querySelector('input[name="displayDuration"]:checked').value;

        document.getElementById('summary-title').textContent = postTitle;
        document.getElementById('summary-address').textContent = addressDetail;
        document.getElementById('summary-listing-type').textContent = listingType === 'VIP' ? 'Tin VIP' : 'Tin Thường';
        document.getElementById('summary-duration').textContent = `${displayDuration} ngày`;

        const basePricePerDay = 2000;
        const vipMultiplier = 50;
        const discountRates = {7: 0, 15: 10, 30: 20};
        let pricePerDay = listingType === 'VIP' ? basePricePerDay * vipMultiplier : basePricePerDay;
        let discount = discountRates[displayDuration] || 0;
        let totalCost = pricePerDay * parseInt(displayDuration) * (1 - discount / 100);

        document.getElementById('summary-price').textContent = totalCost.toLocaleString() + ' VNĐ';
        const discountElement = document.getElementById('summary-benefit-note');
        discountElement.textContent = discount > 0 ? `Giảm giá: -${discount}%` : '';
        discountElement.style.display = discount > 0 ? 'block' : 'none';
    }

    // Xử lý submit form
    form.addEventListener('submit', (e) => {
        if (!validateCurrentTab()) {
            e.preventDefault();
            alert('Vui lòng kiểm tra lại thông tin trước khi gửi!');
        } else if (currentTab === 1 && uploadedImages.length < 1) {
            e.preventDefault();
            alert('Vui lòng tải lên ít nhất 1 ảnh!');
        }
    });

    // Khởi tạo tab đầu tiên
    showTab(currentTab);
});