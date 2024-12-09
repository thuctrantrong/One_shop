window.addEventListener('DOMContentLoaded', event => {
    fetchShop();  // Gọi hàm fetch để lấy dữ liệu từ API

    function fetchShop() {
        fetch('http://localhost:9090/admin/api/product')
            .then(response => response.json())
            .then(data => {
                console.log(data);

                // Kiểm tra nếu có dữ liệu hợp lệ
                if (Array.isArray(data) && data.length > 0) {
                    // Lưu danh sách sản phẩm vào localStorage
                    localStorage.setItem('products', JSON.stringify(data));

                    const productList = document.getElementById('product-list');
                    productList.innerHTML = ''; // Xóa dữ liệu cũ

                    data.forEach(product => {
                        const productItem = document.createElement('div');
                        productItem.classList.add('product-item');
                        productItem.innerHTML = `
                            <img src="${product.imageUrl || '/img/default-image.jpg'}" alt="Hình sản phẩm">
                            <h5>${product.name || 'N/A'}</h5>
                            <p>${product.description || 'Không có mô tả'}</p>
                            <p><strong>Giá:</strong> ${product.price || 'Không xác định'}</p>
                            <p><strong>Tồn kho:</strong> ${product.stock || 'Hết hàng'}</p>
                            <button class="btn btn-primary view-detail-btn" data-id="${product.productId}">Xem Chi Tiết</button>
                        `;
                        productList.appendChild(productItem);
                    });

                    // Khởi tạo Owl Carousel
                    $('#product-list').owlCarousel({
                        loop: true,
                        margin: 10,
                        nav: true,
                        responsive: {
                            0: { items: 1 },
                            600: { items: 3 },
                            1000: { items: 5 }
                        }
                    });

                    // Thêm sự kiện click cho các nút "Xem Chi Tiết"
                    const viewDetailBtns = document.querySelectorAll('.view-detail-btn');
                    viewDetailBtns.forEach(btn => {
                        btn.addEventListener('click', function() {
                            const productId = btn.getAttribute('data-id');

                            // Tìm sản phẩm theo ID từ localStorage
                            const products = JSON.parse(localStorage.getItem('products')) || [];
                            const selectedProduct = products.find(p => p.productId.toString() === productId); // Sử dụng 'productId' đúng

                            if (selectedProduct) {
                                localStorage.setItem('selectedProduct', JSON.stringify(selectedProduct));
                                window.location.href = `product-details?id=${productId}`;
                            } else {
                                alert('Không tìm thấy sản phẩm!');
                            }
                        });
                    });
                } else {
                    showError('Không có dữ liệu sản phẩm');
                }
            })
            .catch(error => {
                console.error('Lỗi khi tải dữ liệu:', error);
                showError('Lỗi khi tải dữ liệu');
            });
    }

    function showError(message) {
        const productList = document.getElementById('product-list');
        productList.innerHTML = `<div class="product-item"><p>${message}</p></div>`;
    }
});
