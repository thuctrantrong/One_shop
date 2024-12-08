let shops = [];
window.addEventListener('DOMContentLoaded', event => {
    fetchShop();  // Gọi hàm fetch để lấy dữ liệu từ API

    function fetchShop() {
        fetch('http://localhost:9090/admin/api/shop')
            .then(response => response.json())
            .then(data => {
                console.log(data);
                shops = data;
                const tableBody = document.getElementById('employee-table-body');
                tableBody.innerHTML = '';

                if (Array.isArray(data) && data.length > 0) {
                    data.forEach((shop, index) => {
                        const row = document.createElement('tr');
                        row.innerHTML = `
                        <td>${index + 1}</td>
                        <td>${shop.name || 'N/A'}</td>
                        <td>${shop.description || 'N/A'}</td>
                        <td>${shop.createdAt || 'N/A'}</td>
                        <td>${shop.owner?.email || 'N/A'}</td>
                        <td>
                            <button class="btn btn-warning btn-sm" data-bs-toggle="modal" data-bs-target="#editModal" onclick="editShop(${shop.shopId})">Sửa</button>
                            <button class="btn btn-danger btn-sm" onclick="deleteShop(${shop.shopId})">Xóa</button>
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
function deleteShop(shopId) {
    fetch(`http://localhost:9090/admin/api/shop/delete/${shopId}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
        },
    })
        .then(response => {
            if (response.ok) {

                console.log('Shop deleted successfully');
                location.reload();
            } else {
                console.error('Failed to delete shop');
            }
        })
        .catch(error => {
            console.error('Lỗi khi xóa shop:', error);
        });
}

// Hàm gọi khi nhấn nút "Edit"
function editShop(shopId) {

    const shop = shops.find(s => s.shopId === shopId)

    if (shop) {
        document.getElementById('shopId').value = shop.shopId;
        document.getElementById('shopname').value = shop.name;
        document.getElementById('shopdescription').value = shop.description;
        document.getElementById('shopdate').value = shop.createdAt;
    }
}

document.getElementById('editForm').addEventListener('submit', function(event) {
    event.preventDefault();
    const shopId = document.getElementById('shopId').value;
    const updatedShop = {
        name: document.getElementById('shopname').value,
        description: document.getElementById('shopdescription').value,
        createdAt: document.getElementById('shopdate').value,
    };


    fetch(`http://localhost:9090/admin/api/shop/edit/${shopId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(updatedShop)
    })
        .then(response => response.json())
        .then(updatedData => {
            console.log(updatedData);
            $('#editModal').modal('hide');
            location.reload()
        })
        .catch(error => {
            console.error('Lỗi khi cập nhật shop:', error);
        });
});
