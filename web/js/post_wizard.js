document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('postWizardForm');
    if (!form) {
        console.error('PostWizardForm not found');
        return;
    }

    const tabs = Array.from(document.querySelectorAll('.wizard-tab'));
    const navItems = Array.from(document.querySelectorAll('.wizard-nav-item'));
    let currentTab = 0;
    let pricingData = [];
    let currentSectionIndex = 0;
    const sections = Array.from(document.querySelectorAll('#tab-1 .form-section'));

    // Initialize sections in tab 1
    sections.forEach((section, index) => {
        if (index > 0) section.classList.add('hidden');
    });

    // Fetch pricing data
    fetch('/HouseFinder/api/pricing')
        .then(response => {
            if (!response.ok) throw new Error(`Failed to fetch pricing data: ${response.status} ${response.statusText}`);
            return response.json();
        })
        .then(data => {
            pricingData = data;
            console.log('Pricing data loaded:', pricingData);
            updateCost();
        })
        .catch(error => {
            console.error('Error fetching pricing data:', error);
            // Fallback pricing data
            pricingData = [
                { postType: 'NORMAL', durationDays: 7, pricePerDay: 2000, discountPercentage: 0 },
                { postType: 'NORMAL', durationDays: 15, pricePerDay: 1800, discountPercentage: 10 },
                { postType: 'NORMAL', durationDays: 30, pricePerDay: 1500, discountPercentage: 20 },
                { postType: 'VIP', durationDays: 7, pricePerDay: 100000, discountPercentage: 0 },
                { postType: 'VIP', durationDays: 15, pricePerDay: 90000, discountPercentage: 10 },
                { postType: 'VIP', durationDays: 30, pricePerDay: 75000, discountPercentage: 20 }
            ];
            console.log('Using fallback pricing data:', pricingData);
            updateCost();
        });

    function showTab(n) {
        tabs.forEach((tab, index) => {
            tab.style.display = (index === n) ? 'block' : 'none';
        });
        navItems.forEach((navItem, index) => {
            navItem.classList.toggle('active', index === n);
            navItem.classList.toggle('completed', index < n);
            navItem.disabled = index > n;
        });

        const prevBtn = document.getElementById('prevBtn');
        const nextBtn = document.getElementById('nextBtn');
        const submitBtn = document.getElementById('submitBtn');

        prevBtn.style.display = (n === 0) ? 'none' : 'inline-block';
        nextBtn.style.display = (n === tabs.length - 1) ? 'none' : 'inline-block';
        submitBtn.style.display = (n === tabs.length - 1) ? 'inline-block' : 'none';
    }

    function navigate(n) {
        if (n > 0 && !validateCurrentTab()) {
            alert('Vui lòng điền đầy đủ các trường bắt buộc.');
            return;
        }
        currentTab += n;
        if (currentTab >= tabs.length) {
            form.submit();
            return;
        }
        currentSectionIndex = 0;
        sections.forEach((section, index) => {
            section.classList.toggle('hidden', index !== 0);
        });
        showTab(currentTab);
    }

    function validateCurrentTab() {
        if (currentTab === 0) {
            const requiredInputs = tabs[currentTab].querySelectorAll('input[required], select[required], textarea[required]');
            let allValid = true;
            requiredInputs.forEach(input => {
                if (input.value.trim() === '') {
                    console.warn(`Validation failed for input: ${input.id}`);
                    allValid = false;
                }
            });
            console.log('Tab 1 validation result:', allValid);
            return allValid;
        } else if (currentTab === 1) {
            const imageCount = document.getElementById('image-preview-area').children.length;
            if (imageCount < 1 || imageCount > 10) {
                console.warn('Validation failed: Image count is ' + imageCount);
                alert('Vui lòng tải lên từ 1 đến 10 ảnh.');
                return false;
            }
            return true;
        } else {
            const requiredInputs = tabs[currentTab].querySelectorAll('input[required], select[required], textarea[required]');
            let allValid = true;
            requiredInputs.forEach(input => {
                if (input.value.trim() === '') {
                    console.warn(`Validation failed for input: ${input.id}`);
                    allValid = false;
                }
            });
            console.log(`Tab ${currentTab + 1} validation result:`, allValid);
            return allValid;
        }
    }

    function showNextSection() {
        const currentSection = sections[currentSectionIndex];
        const requiredInputs = currentSection.querySelectorAll('input[required], select[required], textarea[required]');
        const allFilled = Array.from(requiredInputs).every(input => {
            const isValid = input.value.trim() !== '';
            console.log(`Checking input ${input.id}: ${isValid}`);
            return isValid;
        });

        if (allFilled && currentSectionIndex < sections.length - 1) {
            currentSectionIndex++;
            sections[currentSectionIndex].classList.remove('hidden');
            sections[currentSectionIndex].scrollIntoView({ behavior: 'smooth' });
            console.log('Showing section:', sections[currentSectionIndex].id);
        }
    }

    sections.forEach(section => {
        section.querySelectorAll('input, select, textarea').forEach(input => {
            input.addEventListener('input', () => {
                console.log(`Input event on ${input.id}: ${input.value}`);
                showNextSection();
            });
            input.addEventListener('change', () => {
                console.log(`Change event on ${input.id}: ${input.value}`);
                showNextSection();
            });
        });
    });

    // Address dropdown handling
    const provinceSelect = document.getElementById('province');
    const districtSelect = document.getElementById('district');
    const wardSelect = document.getElementById('ward');

    provinceSelect.addEventListener('change', () => {
        const provinceId = provinceSelect.value;
        console.log('Province selected:', provinceId);
        districtSelect.innerHTML = '<option value="">-- Chọn Quận/Huyện --</option>';
        wardSelect.innerHTML = '<option value="">-- Chọn Phường/Xã --</option>';
        if (provinceId) {
            fetch(`/HouseFinder/api/districts?provinceId=${provinceId}`)
                .then(response => {
                    if (!response.ok) throw new Error(`Failed to fetch districts for provinceId=${provinceId}: ${response.status} ${response.statusText}`);
                    return response.json();
                })
                .then(districts => {
                    console.log('Districts received:', districts);
                    districts.forEach(d => {
                        const option = document.createElement('option');
                        option.value = d.id;
                        option.textContent = d.name;
                        districtSelect.appendChild(option);
                    });
                    updateAddressDetail();
                })
                .catch(error => console.error('Error fetching districts:', error));
        }
    });

    districtSelect.addEventListener('change', () => {
        const districtId = districtSelect.value;
        console.log('District selected:', districtId);
        wardSelect.innerHTML = '<option value="">-- Chọn Phường/Xã --</option>';
        if (districtId) {
            fetch(`/HouseFinder/api/wards?districtId=${districtId}`)
                .then(response => {
                    if (!response.ok) throw new Error(`Failed to fetch wards for districtId=${districtId}: ${response.status} ${response.statusText}`);
                    return response.json();
                })
                .then(wards => {
                    console.log('Wards received:', wards);
                    wards.forEach(w => {
                        const option = document.createElement('option');
                        option.value = w.id;
                        option.textContent = w.name;
                        wardSelect.appendChild(option);
                    });
                    updateAddressDetail();
                })
                .catch(error => console.error('Error fetching wards:', error));
        }
    });

    function updateAddressDetail() {
        const province = provinceSelect.selectedOptions[0]?.textContent || '';
        const district = districtSelect.selectedOptions[0]?.textContent || '';
        const ward = wardSelect.selectedOptions[0]?.textContent || '';
        const street = document.getElementById('street').value || '';
        const addressDetail = [street, ward, district, province].filter(Boolean).join(', ');
        document.getElementById('addressDetail').value = addressDetail;
        console.log('Address detail updated:', addressDetail);
        updateSummary();
    }

    document.getElementById('province').addEventListener('change', updateAddressDetail);
    document.getElementById('district').addEventListener('change', updateAddressDetail);
    document.getElementById('ward').addEventListener('change', updateAddressDetail);
    document.getElementById('street').addEventListener('input', updateAddressDetail);
    document.getElementById('postTitle').addEventListener('input', updateSummary);

    // Image upload handling with retry logic
    const imageUploadInput = document.getElementById('imageUploadInput');
    const imagePreviewArea = document.getElementById('image-preview-area');
    const thumbnailIdentifier = document.getElementById('thumbnailIdentifier');
    let uploadedImages = [];

    async function uploadImageWithRetry(file, retries = 3, delay = 1000) {
        for (let i = 0; i < retries; i++) {
            try {
                const formData = new FormData();
                formData.append('images', file);

                const response = await fetch('/HouseFinder/api/image-upload', {
                    method: 'POST',
                    body: formData
                });

                if (!response.ok) {
                    const errorText = await response.text();
                    throw new Error(`Failed to upload image: ${response.status} ${response.statusText} - ${errorText}`);
                }
                return await response.json();
            } catch (error) {
                console.error(`Image upload attempt ${i + 1} failed:`, error);
                if (i < retries - 1) {
                    await new Promise(resolve => setTimeout(resolve, delay));
                    continue;
                }
                throw error;
            }
        }
    }

    imageUploadInput.addEventListener('change', async (event) => {
        const files = event.target.files;
        if (files.length + uploadedImages.length > 10) {
            alert('Bạn chỉ có thể tải lên tối đa 10 ảnh!');
            return;
        }

        for (const file of Array.from(files)) {
            if (!file.type.startsWith('image/')) {
                alert('Chỉ được tải lên các file ảnh!');
                continue;
            }

            try {
                const data = await uploadImageWithRetry(file);
                if (data.success) {
                    data.images.forEach(img => {
                        const imgWrapper = document.createElement('div');
                        imgWrapper.classList.add('image-preview-item');

                        const imgElement = document.createElement('img');
                        imgElement.src = img.imageUrl;
                        imgElement.alt = 'Preview';

                        const starIcon = document.createElement('i');
                        starIcon.classList.add('fa-solid', 'fa-star');
                        starIcon.addEventListener('click', () => {
                            document.querySelectorAll('.image-preview-item .fa-star').forEach(icon => icon.classList.remove('selected'));
                            starIcon.classList.add('selected');
                            thumbnailIdentifier.value = img.cloudinaryPublicId;
                        });

                        const deleteIcon = document.createElement('i');
                        deleteIcon.classList.add('fa-solid', 'fa-trash');
                        deleteIcon.addEventListener('click', () => {
                            fetch(`/HouseFinder/api/image-delete/${img.cloudinaryPublicId}`, { method: 'GET' })
                                .then(res => {
                                    if (!res.ok) throw new Error(`Failed to delete image: ${res.status} ${res.statusText}`);
                                    return res.json();
                                })
                                .then(resData => {
                                    if (resData.success) {
                                        imgWrapper.remove();
                                        uploadedImages = uploadedImages.filter(f => f.cloudinaryPublicId !== img.cloudinaryPublicId);
                                        if (thumbnailIdentifier.value === img.cloudinaryPublicId) {
                                            thumbnailIdentifier.value = uploadedImages.length > 0 ? uploadedImages[0].cloudinaryPublicId : '';
                                        }
                                    }
                                })
                                .catch(error => console.error('Error deleting image:', error));
                        });

                        imgWrapper.appendChild(imgElement);
                        imgWrapper.appendChild(starIcon);
                        imgWrapper.appendChild(deleteIcon);
                        imagePreviewArea.appendChild(imgWrapper);

                        uploadedImages.push({ name: file.name, cloudinaryPublicId: img.cloudinaryPublicId });
                        if (uploadedImages.length === 1) starIcon.click();
                    });
                } else {
                    console.error('Image upload failed:', data.message);
                    alert('Tải ảnh thất bại: ' + (data.message || 'Lỗi không xác định. Vui lòng thử lại.'));
                }
            } catch (error) {
                console.error('Error uploading image:', error);
                alert('Tải ảnh thất bại: ' + error.message);
            }
        }
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

    // Pricing and summary
    function updateCost() {
        const listingType = document.querySelector('input[name="listingType"]:checked')?.value;
        const duration = parseInt(document.querySelector('input[name="displayDuration"]:checked')?.value);
        if (!listingType || !duration) {
            console.warn('Listing type or duration not selected');
            return;
        }

        const pricing = pricingData.find(p => p.postType === listingType && p.durationDays === duration);
        if (pricing) {
            const pricePerDay = pricing.pricePerDay;
            const discount = pricing.discountPercentage;
            const originalTotalCost = pricePerDay * duration;
            const totalCost = originalTotalCost * (1 - discount / 100);

            document.getElementById('summary-listing-type').textContent = listingType === 'NORMAL' ? 'Tin Thường' : 'Tin VIP';
            document.getElementById('summary-duration').textContent = `${duration} ngày`;
            document.getElementById('summary-price').textContent = totalCost.toLocaleString() + ' VNĐ';
            console.log('Cost updated:', { listingType, duration, totalCost });
        } else {
            console.warn('No pricing found for', listingType, duration);
            document.getElementById('summary-price').textContent = 'Chưa xác định';
        }
    }

    function updateSummary() {
        document.getElementById('summary-title').textContent = document.getElementById('postTitle').value || 'Chưa nhập tiêu đề';
        document.getElementById('summary-address').textContent = document.getElementById('addressDetail').value || 'Chưa nhập địa chỉ';
        updateCost();
    }

    document.querySelectorAll('input[name="listingType"], input[name="displayDuration"]').forEach(input => {
        input.addEventListener('change', updateCost);
    });

    // Navigation buttons
    document.getElementById('nextBtn').addEventListener('click', () => {
        console.log('Next button clicked, currentTab:', currentTab);
        navigate(1);
    });

    document.getElementById('prevBtn').addEventListener('click', () => {
        console.log('Previous button clicked, currentTab:', currentTab);
        navigate(-1);
    });

    // Form submission
    form.addEventListener('submit', (e) => {
        if (!validateCurrentTab()) {
            e.preventDefault();
            alert('Vui lòng kiểm tra lại thông tin trước khi gửi!');
            console.warn('Form submission prevented due to validation failure');
        } else if (currentTab === 1 && uploadedImages.length < 1) {
            e.preventDefault();
            alert('Vui lòng tải lên ít nhất 1 ảnh!');
            console.warn('Form submission prevented: No images uploaded');
        }
    });

    showTab(currentTab);
});