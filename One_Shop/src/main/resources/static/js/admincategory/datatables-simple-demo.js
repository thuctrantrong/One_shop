let categorys = [];
window.addEventListener('DOMContentLoaded', event => {
    fetchcategory();  // Gọi hàm fetch để lấy dữ liệu từ API

    function fetchcategory() {
        fetch('http://localhost:9090/admin/api/category')
            .then(response => response.json())
            .then(data => {
                console.log(data);
                categorys = data;
                const tableBody = document.getElementById('employee-table-body');
                tableBody.innerHTML = '';

                if (Array.isArray(data) && data.length > 0) {
                    data.forEach((category, index) => {
                        const row = document.createElement('tr');
                        row.innerHTML = `
                        <td>${index + 1}</td>
                        <td>${category.name || 'N/A'}</td>
                        <td>${category.description || 'N/A'}</td>
                        <td>${category.createdAt || 'N/A'}</td>
                        <td>
                            <button class="btn btn-warning btn-sm" data-bs-toggle="modal" data-bs-target="#editModal" onclick="editcategory(${category.categoryId})">Sửa</button>
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

// Hàm gọi khi nhấn nút "Edit"
function editcategory(categoryId) {

    const category = categorys.find(c => c.categoryId === categoryId)
    console.log("Đang xóa khuyến mãi với ID: " + categoryId);

    if (category) {
        document.getElementById('categoryId').value = category.categoryId;
        document.getElementById('categoryname').value = category.name;
        document.getElementById('categorydescription').value = category.description;
        document.getElementById('categorycondition').value = category.condition;
        const createdAt = new Date(category.createdAt.replace(' ', 'T'));  // Thay đổi dấu cách thành dấu 'T'
        const formattedDate = createdAt.toISOString().slice(0, 16);
        document.getElementById('categorydate').value = formattedDate;
    }
}

document.getElementById('editForm').addEventListener('submit', function(event) {
    event.preventDefault();
    const categoryId  = document.getElementById('categoryId').value;
    const updatedShop = {
        name: document.getElementById('categoryname').value,
        description: document.getElementById('categorydescription').value,
        condition: document.getElementById('categorycondition').value,
        createdAt: document.getElementById('categorydate').value,
    };


    fetch(`http://localhost:9090/admin/api/category/edit/${categoryId}`, {
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
