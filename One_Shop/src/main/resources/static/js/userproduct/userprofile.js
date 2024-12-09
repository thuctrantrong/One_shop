let products = [];
window.addEventListener('DOMContentLoaded', event => {
    fetchShop();  // Gọi hàm fetch để lấy dữ liệu từ API

    function fetchShop() {
        fetch('http://localhost:9090/admin/api/employees')
            .then(response => response.json())
            .then(data => {
                console.log(data);
                products = data;
                const productList = document.getElementById('product-list');
                productList.innerHTML = ''; // Clear previous data

                if (Array.isArray(data) && data.length > 0) {
                    data.forEach((product, index) => {
                        const productItem = document.createElement('div');
                        productItem.classList.add('product-item');
                        productItem.innerHTML = `
                            <img src="${product.imageUrl || 'default-image.jpg'}" alt="Image">
                            <h5>${product.name || 'N/A'}</h5>
                            <p>${product.description || 'N/A'}</p>
                            <p><strong>Price:</strong> ${product.price || 'N/A'}</p>
                            <p><strong>Stock:</strong> ${product.stock || 'N/A'}</p>
                        `;
                        productList.appendChild(productItem);
                    });

                    // Initialize Owl Carousel after appending all products
                    $('#product-list').owlCarousel({
                        loop: true,
                        margin: 10,
                        nav: true,
                        responsive: {
                            0: {
                                items: 1
                            },
                            600: {
                                items: 3
                            },
                            1000: {
                                items: 5
                            }
                        }
                    });
                } else {
                    const noDataItem = document.createElement('div');
                    noDataItem.classList.add('product-item');
                    noDataItem.innerHTML = '<p>Không có dữ liệu sản phẩm</p>';
                    productList.appendChild(noDataItem);
                }
            })
            .catch(error => {
                console.error('Error fetching data:', error);
                const productList = document.getElementById('product-list');
                productList.innerHTML = '<div class="product-item"><p>Lỗi khi tải dữ liệu</p></div>';
            });
    }
});
