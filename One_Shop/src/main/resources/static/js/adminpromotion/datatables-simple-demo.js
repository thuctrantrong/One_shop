let promotions = [];
window.addEventListener('DOMContentLoaded', event => {
    fetchpromotion();  // Gọi hàm fetch để lấy dữ liệu từ API

    function fetchpromotion() {
        fetch('http://localhost:9090/admin/api/promotion')
            .then(response => response.json())
            .then(data => {
                console.log(data);
                promotions = data;
                const tableBody = document.getElementById('employee-table-body');
                tableBody.innerHTML = '';

                if (Array.isArray(data) && data.length > 0) {
                    data.forEach((promotion, index) => {
                        const row = document.createElement('tr');
                        row.innerHTML = `
                        <td>${index + 1}</td>
                        <td>${promotion.name || 'N/A'}</td>
                        <td>${promotion.discountValue || 'N/A'}</td>
                        <td>${promotion.startDate || 'N/A'}</td>
                        <td>${promotion.endDate || 'N/A'}</td>
                        <td>${promotion.createdAt || 'N/A'}</td>
                        <td>
                            <button class="btn btn-warning btn-sm" data-bs-toggle="modal" data-bs-target="#editModal" onclick="editpromotion(${promotion.promotionId})">Sửa</button>
                            <button class="btn btn-danger btn-sm" onclick="deletepromotion(${promotion.promotionId})">Xóa</button>
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
function deletepromotion(promotionId) {
    fetch(`http://localhost:9090/admin/api/promotion/delete/${promotionId}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
        },
    })
        .then(response => {
            if (response.ok) {
                console.log('promotion deleted successfully');
                location.reload();  // Làm mới trang sau khi xóa
            } else {
                console.error('Failed to delete promotion');
            }
        })
        .catch(error => {
            console.error('Lỗi khi xóa promotion:', error);
        });
}
// Hàm gọi khi nhấn nút "Edit"
function editpromotion(promotionId) {

    const promotion = promotions.find(c => c.promotionId === promotionId)
    if (promotion) {
        document.getElementById('promotionId').value = promotion.promotionId;
        document.getElementById('promotionname').value = promotion.name;
        document.getElementById('promotiondiscountValue').value = promotion.discountValue;
        document.getElementById('promotionstartDate').value = promotion.startDate;
        document.getElementById('promotionendDate').value = promotion.endDate;
        const createdAt = new Date(promotion.createdAt.replace(' ', 'T'));  // Thay đổi dấu cách thành dấu 'T'
        const formattedDate = createdAt.toISOString().slice(0, 16);
        document.getElementById('promotioncreatedAt').value = formattedDate;

    }
}

document.getElementById('editForm').addEventListener('submit', function(event) {
    event.preventDefault();
    const promotionId  = document.getElementById('promotionId').value;
    const updatedShop = {
        name: document.getElementById('promotionname').value,
        discountValue: document.getElementById('promotiondiscountValue').value,
        startDate: document.getElementById('promotionstartDate').value,
        endDate: document.getElementById('promotionendDate').value,
        createdAt: document.getElementById('promotioncreatedAt').value,
    };


    fetch(`http://localhost:9090/admin/api/promotion/edit/${promotionId}`, {
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
