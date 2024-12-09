// script.js

window.addEventListener('DOMContentLoaded', () => {
    fetchShop(); // Gọi hàm fetch để lấy dữ liệu từ API

    const productsPerPage = 10; // Số sản phẩm hiển thị mỗi trang
    let currentPage = 1; // Trang hiện tại
    let totalPages = 1; // Tổng số trang

    function fetchShop() {
        const loader = document.getElementById('loader');
        if (loader) {
            loader.style.display = 'block'; // Hiển thị loader
        }

        fetch('http://localhost:9090/admin/api/product')
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                console.log(data);

                if (Array.isArray(data) && data.length > 0) {
                    // Lưu danh sách sản phẩm vào localStorage
                    localStorage.setItem('products', JSON.stringify(data));

                    totalPages = Math.ceil(data.length / productsPerPage);

                    renderPage(currentPage);
                    renderPagination();
                } else {
                    showError('Không có dữ liệu sản phẩm');
                }

                if (loader) {
                    loader.style.display = 'none'; // Ẩn loader sau khi tải xong
                }
            })
            .catch(error => {
                console.error('Lỗi khi tải dữ liệu:', error);
                showError('Lỗi khi tải dữ liệu');

                if (loader) {
                    loader.style.display = 'none'; // Ẩn loader nếu có lỗi
                }
            });
    }

    function renderPage(page) {
        const productList = document.getElementById('product-list');
        if (!productList) {
            console.error("Không tìm thấy phần tử #product-list trong DOM");
            return;
        }

        const products = JSON.parse(localStorage.getItem('products')) || [];
        const startIndex = (page - 1) * productsPerPage;
        const endIndex = startIndex + productsPerPage;
        const paginatedProducts = products.slice(startIndex, endIndex);

        productList.innerHTML = ''; // Xóa nội dung cũ

        paginatedProducts.forEach(product => {
            const productItem = document.createElement('div');
            productItem.classList.add('product-item');
            productItem.innerHTML = `
                <img src="${product.imageUrl || '/img/default-image.jpg'}" alt="Hình sản phẩm">
                <h5>${product.name || 'N/A'}</h5>
                <p><strong>Giá:</strong> ${formatPrice(product.price)}</p>
                <p><strong>Tồn kho:</strong> ${product.stock || 'Hết hàng'}</p>
                <button class="view-detail-btn" data-id="${product.productId}"><i class="fas fa-info-circle"></i> Xem Chi Tiết</button>
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

    function renderPagination() {
        const pagination = document.getElementById('pagination');
        if (!pagination) {
            console.error("Không tìm thấy phần tử #pagination trong DOM");
            return;
        }

        pagination.innerHTML = ''; // Xóa nội dung cũ

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

        // Hiển thị thông tin trang hiện tại
        const pageInfo = document.createElement('span');
        pageInfo.textContent = `Trang ${currentPage} / ${totalPages}`;
        pageInfo.style.alignSelf = 'center';
        pageInfo.style.margin = '0 15px';
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

    function showError(message) {
        const productList = document.getElementById('product-list');
        if (productList) {
            productList.innerHTML = `<div class="product-item"><p>${message}</p></div>`;
        } else {
            console.error("Không tìm thấy phần tử #product-list khi hiển thị lỗi.");
        }
    }

    function formatPrice(price) {
        if (price === undefined || price === null) return 'Không xác định';
        // Định dạng giá tiền theo định dạng Việt Nam, ví dụ: 1.000.000 VND
        return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price);
    }
});
