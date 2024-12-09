window.addEventListener('DOMContentLoaded', () => {
    const productsPerPage = 10; // Số sản phẩm hiển thị mỗi trang
    let currentPage = 1; // Trang hiện tại
    let totalPages = 1; // Tổng số trang
    let selectedCategoryId = 'all'; // Danh mục hiện tại

    // Khởi tạo ứng dụng
    init();

    function init() {
        fetchProducts(); // Gọi hàm để lấy dữ liệu sản phẩm
        setupCategoryFilter(); // Thiết lập sự kiện cho bộ lọc danh mục
    }

    // Hàm lấy sản phẩm từ API
    function fetchProducts() {
        const loader = document.getElementById('loader');
        const errorMessage = document.getElementById('error-message');

        if (loader) {
            loader.style.display = 'block'; // Hiển thị loader
        }

        fetch('http://localhost:9090/admin/api/product') // Thay đổi URL API nếu cần
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                console.log('Sản phẩm:', data);

                if (Array.isArray(data) && data.length > 0) {
                    // Lưu danh sách sản phẩm vào localStorage
                    localStorage.setItem('products', JSON.stringify(data));

                    // Trích xuất danh mục từ dữ liệu sản phẩm
                    const categories = extractCategories(data);
                    populateCategoryFilter(categories);

                    // Render trang đầu tiên và phân trang
                    renderPage(currentPage);
                    renderPagination();
                } else {
                    showError('Không có dữ liệu sản phẩm');
                }
            })
            .catch(error => {
                console.error('Lỗi khi tải dữ liệu sản phẩm:', error);
                showError('Lỗi khi tải dữ liệu sản phẩm');
            })
            .finally(() => {
                if (loader) {
                    loader.style.display = 'none'; // Ẩn loader sau khi tải xong
                }
            });
    }

    // Hàm trích xuất danh mục từ sản phẩm
    function extractCategories(products) {
        const categoryMap = {};
        products.forEach(product => {
            if (product.category && product.category.categoryId && product.category.name) {
                categoryMap[product.category.categoryId] = product.category.name;
            }
        });
        // Chuyển đổi đối tượng thành mảng
        const categories = Object.keys(categoryMap).map(id => ({
            categoryId: id,
            name: categoryMap[id]
        }));
        return categories;
    }

    // Hàm thêm các danh mục vào bộ lọc
    function populateCategoryFilter(categories) {
        const categoryButtons = document.getElementById('category-buttons');

        // Sắp xếp danh mục theo tên
        categories.sort((a, b) => a.name.localeCompare(b.name));

        categories.forEach(category => {
            const btn = document.createElement('button');
            btn.classList.add('category-btn');
            btn.setAttribute('data-id', category.categoryId);
            btn.textContent = category.name;
            categoryButtons.appendChild(btn);
        });
    }

    // Hàm thiết lập sự kiện cho bộ lọc danh mục
    function setupCategoryFilter() {
        const categoryButtons = document.getElementById('category-buttons');

        categoryButtons.addEventListener('click', (event) => {
            if (event.target && event.target.matches('button.category-btn')) {
                const selectedBtn = event.target;
                selectedCategoryId = selectedBtn.getAttribute('data-id');

                // Xóa lớp active khỏi tất cả các nút
                const allBtns = categoryButtons.querySelectorAll('button.category-btn');
                allBtns.forEach(btn => btn.classList.remove('active'));

                // Thêm lớp active cho nút đã chọn
                selectedBtn.classList.add('active');

                currentPage = 1; // Reset về trang đầu tiên khi thay đổi danh mục
                renderPage(currentPage);
                renderPagination();
                window.scrollTo(0, 0); // Cuộn lên đầu trang
            }
        });
    }

    // Hàm render sản phẩm cho trang hiện tại và danh mục đã chọn
    function renderPage(page) {
        const productList = document.getElementById('product-list');
        if (!productList) {
            console.error("Không tìm thấy phần tử #product-list trong DOM");
            return;
        }

        const allProducts = JSON.parse(localStorage.getItem('products')) || [];

        // Lọc sản phẩm theo danh mục đã chọn
        const filteredProducts = selectedCategoryId === 'all'
            ? allProducts
            : allProducts.filter(product => product.category && product.category.categoryId.toString() === selectedCategoryId);

        // Cập nhật tổng số trang
        totalPages = Math.ceil(filteredProducts.length / productsPerPage);

        const startIndex = (page - 1) * productsPerPage;
        const endIndex = startIndex + productsPerPage;
        const paginatedProducts = filteredProducts.slice(startIndex, endIndex);

        productList.innerHTML = ''; // Xóa nội dung cũ

        if (paginatedProducts.length === 0) {
            productList.innerHTML = `<div class="product-item"><p>Không có sản phẩm nào phù hợp.</p></div>`;
            return;
        }

        paginatedProducts.forEach(product => {
            const productItem = document.createElement('div');
            productItem.classList.add('product-item');
            productItem.innerHTML = `
                <img src="${product.imageUrl || '/img/default-image.jpg'}" alt="Hình sản phẩm">
                <h5>${product.name || 'N/A'}</h5>
                <p><strong>Giá:</strong> ${formatPrice(product.price)}</p>
                <p><strong>Tồn kho:</strong> ${product.stock > 0 ? product.stock : 'Hết hàng'}</p>
                <button class="view-detail-btn" data-id="${product.productId}">
                    <i class="fas fa-info-circle"></i> Xem Chi Tiết
                </button>
            `;
            productList.appendChild(productItem);
        });

        // Thêm sự kiện click cho các nút "Xem Chi Tiết"
        const viewDetailBtns = document.querySelectorAll('.view-detail-btn');
        viewDetailBtns.forEach(btn => {
            btn.addEventListener('click', () => {
                const productId = btn.getAttribute('data-id');
                const products = JSON.parse(localStorage.getItem('products')) || [];
                const selectedProduct = products.find(p => p.productId.toString() === productId);

                if (selectedProduct) {
                    localStorage.setItem('selectedProduct', JSON.stringify(selectedProduct));
                    window.location.href = `product-details?id=${productId}`;
                } else {
                    alert('Không tìm thấy sản phẩm!');
                }
            });
        });
    }

    // Hàm render các nút phân trang
    function renderPagination() {
        const pagination = document.getElementById('pagination');
        if (!pagination) {
            console.error("Không tìm thấy phần tử #pagination trong DOM");
            return;
        }

        pagination.innerHTML = ''; // Xóa nội dung cũ

        if (totalPages <= 1) {
            // Không cần phân trang nếu chỉ có một trang
            return;
        }

        // Nút Trang trước với icon
        const prevButton = document.createElement('button');
        prevButton.innerHTML = '<i class="fas fa-chevron-left"></i> Previous';
        prevButton.disabled = currentPage === 1;
        if (currentPage === 1) {
            prevButton.classList.add('disabled');
        }
        prevButton.addEventListener('click', () => {
            if (currentPage > 1) {
                currentPage--;
                renderPage(currentPage);
                renderPagination();
                window.scrollTo(0, 0); // Cuộn lên đầu trang
            }
        });
        pagination.appendChild(prevButton);

        // Hiển thị số trang
        for (let i = 1; i <= totalPages; i++) {
            const pageButton = document.createElement('button');
            pageButton.textContent = i;
            if (i === currentPage) {
                pageButton.classList.add('active-page');
            }
            pageButton.addEventListener('click', () => {
                currentPage = i;
                renderPage(currentPage);
                renderPagination();
                window.scrollTo(0, 0); // Cuộn lên đầu trang
            });
            pagination.appendChild(pageButton);
        }

        // Hiển thị thông tin trang hiện tại
        const pageInfo = document.createElement('span');
        pageInfo.textContent = `Trang ${currentPage} / ${totalPages}`;
        pagination.appendChild(pageInfo);

        // Nút Trang sau với icon
        const nextButton = document.createElement('button');
        nextButton.innerHTML = 'Next <i class="fas fa-chevron-right"></i>';
        nextButton.disabled = currentPage === totalPages;
        if (currentPage === totalPages) {
            nextButton.classList.add('disabled');
        }
        nextButton.addEventListener('click', () => {
            if (currentPage < totalPages) {
                currentPage++;
                renderPage(currentPage);
                renderPagination();
                window.scrollTo(0, 0); // Cuộn lên đầu trang
            }
        });
        pagination.appendChild(nextButton);
    }

    // Hàm hiển thị thông báo lỗi
    function showError(message) {
        const errorMessage = document.getElementById('error-message');
        if (errorMessage) {
            errorMessage.textContent = message;
            errorMessage.style.display = 'block';
        } else {
            console.error("Không tìm thấy phần tử #error-message để hiển thị lỗi.");
        }

        // Ẩn danh sách sản phẩm nếu có lỗi
        const productList = document.getElementById('product-list');
        if (productList) {
            productList.innerHTML = `<div class="product-item"><p>${message}</p></div>`;
        }
    }

    // Hàm định dạng giá tiền
    function formatPrice(price) {
        if (price === undefined || price === null) return 'Không xác định';
        // Định dạng giá tiền theo định dạng Việt Nam, ví dụ: 1.000.000 ₫
        return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price);
    }
});
