let products = [];
window.addEventListener('DOMContentLoaded', event => {
    fetchproduct();  // Gọi hàm fetch để lấy dữ liệu từ API

    function fetchproduct() {
        fetch('http://localhost:9090/admin/api/product')
            .then(response => response.json())
            .then(data => {
                console.log(data);
                products = data;
                const tableBody = document.getElementById('employee-table-body');
                tableBody.innerHTML = '';

                if (Array.isArray(data) && data.length > 0) {
                    data.forEach((product, index) => {
                        const row = document.createElement('tr');
                        row.innerHTML = `
                        <td>${index + 1}</td>
                        <td>${product.name || 'N/A'}</td>
                        <td>${product.description || 'N/A'}</td>
                        <td>${product.createdAt || 'N/A'}</td>
                        <td>${product.price|| 'N/A'}</td>
                        <td>${product.stock || 'N/A'}</td>
                        <td><img src="${product.imageUrl || 'default-image.jpg'}" alt="Image" style="width: 100px; height: auto;"></td>
                        <td>${product.category?.name || 'N/A'}</td>
                        <td>
                            <button class="btn btn-warning btn-sm" data-bs-toggle="modal" data-bs-target="#editModal" onclick="editProduct(${product.productId})">Sửa</button>
                            <button class="btn btn-danger btn-sm" onclick="deleteProduct(${product.productId})">Xóa</button>
                        </td>
                        `;
                        tableBody.appendChild(row);
                    });

                    new simpleDatatables.DataTable("#datatablesSimple");
                } else {
                    const row = document.createElement('tr');
                    row.innerHTML = `<td colspan="9">Không có dữ liệu người dùng</td>`;
                    tableBody.appendChild(row);
                }
            })
            .catch(error => {
                console.error('Error fetching data:', error);
                const tableBody = document.getElementById('employee-table-body');
                tableBody.innerHTML = '<tr><td colspan="9">Lỗi khi tải dữ liệu</td></tr>';
            });
    }
});
// Hàm gọi khi nhấn nút "Delete"
function deleteProduct(ProductId) {
    console.log("Đang xóa khuyến mãi với ID: " + ProductId);

    fetch(`http://localhost:9090/admin/api/product/delete/${ProductId}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
        },
    })
        .then(response => {
            if (response.ok) {

                console.log('product deleted successfully');
                location.reload();
            } else {
                console.error('Failed to delete product');
            }
        })
        .catch(error => {
            console.error('Lỗi khi xóa product:', error);
        });
}

// Hàm gọi khi nhấn nút "Edit"
function editProduct(productId) {
    console.log("Đang xóa khuyến mãi với ID: " + productId);

    const product = products.find(p => p.productId === productId);

    if (product) {

        document.getElementById('productId').value = product.productId; // Gán lại productId
        document.getElementById('productName').value = product.name;
        document.getElementById('productDescription').value = product.description; // Sửa dòng này
        const createdAt = new Date(product.createdAt.replace(' ', 'T'));  // Thay đổi dấu cách thành dấu 'T'
        const formattedDate = createdAt.toISOString().slice(0, 16);
        document.getElementById('productDate').value = formattedDate;
        document.getElementById('productPrice').value = product.price;
        document.getElementById('productStock').value = product.stock;
        const currentImage = document.getElementById('currentProductImage');
        if (product.imageUrl) {
            currentImage.src = product.imageUrl;
            currentImage.style.display = 'block';  // Hiển thị ảnh
        } else {
            currentImage.style.display = 'none';  // Ẩn ảnh nếu không có URL
        }

        document.getElementById('productImageUrl').value = '';    }
}
document.getElementById('editForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Ngừng gửi form mặc định

    // Lấy productId từ input hoặc trực tiếp từ URL (nếu không có trong form)
    const productId = document.getElementById('productId').value;

    // Lấy các giá trị từ form
    const updatedProduct = {
        name: document.getElementById('productName').value,
        description: document.getElementById('productDescription').value,
        price: parseFloat(document.getElementById('productPrice').value), // Chuyển giá thành số thực
        stock: parseInt(document.getElementById('productStock').value, 10) // Chuyển stock thành số nguyên
    };

    // Lấy tệp ảnh (nếu có)
    const productImageUrl = document.getElementById('productImageUrl').files[0];

    // Tạo FormData
    const formData = new FormData();
    formData.append('name', updatedProduct.name);
    formData.append('description', updatedProduct.description);
    formData.append('price', updatedProduct.price.toString());  // Đảm bảo giá trị là chuỗi
    formData.append('stock', updatedProduct.stock.toString());    // Đảm bảo stock là chuỗi

    if (productImageUrl) {
        formData.append('file', productImageUrl);  // Đảm bảo gửi tệp ảnh dưới trường "file"
    } else {
        // Nếu không có ảnh mới, gửi URL ảnh cũ
        formData.append('imageUrl', document.getElementById('productImageUrl').value);
    }

    // Gửi yêu cầu PUT với FormData
    fetch(`http://localhost:9090/admin/api/product/edit/${productId}`, {
        method: 'PUT',
        body: formData,
    })
        .then(response => response.json())
        .then(updatedData => {
            console.log(updatedData);
            $('#editModal').modal('hide');
            location.reload(); // Tải lại trang sau khi cập nhật
        })
        .catch(error => {
            console.error('Lỗi khi cập nhật sản phẩm:', error);
        });
});
document.getElementById('productImageUrl').addEventListener('change', function(event) {
    const file = event.target.files[0];
    const imagePreview = document.getElementById('currentProductImage');

    if (file) {
        const reader = new FileReader();

        // Khi file được đọc xong, hiển thị ảnh
        reader.onload = function(e) {
            imagePreview.src = e.target.result;  // Đặt src của img bằng data URL
            imagePreview.style.display = 'block';  // Hiển thị ảnh
        };

        // Đọc file ảnh
        reader.readAsDataURL(file);
    } else {
        imagePreview.style.display = 'none';  // Ẩn ảnh nếu không có file
    }
});




