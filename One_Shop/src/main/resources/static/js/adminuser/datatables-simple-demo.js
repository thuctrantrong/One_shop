let users = [];

window.addEventListener('DOMContentLoaded', event => {
    fetchEmployees();  // Gọi hàm fetch để lấy dữ liệu từ API

    function fetchEmployees() {
        fetch('http://localhost:9090/admin/api/employees')  // URL API của bạn
            .then(response => response.json())
            .then(data => {
                console.log(data);  // Kiểm tra dữ liệu từ API
                users = data;
                const tableBody = document.getElementById('employee-table-body');
                tableBody.innerHTML = '';  // Clear bảng trước khi thêm dữ liệu mới

                // Kiểm tra xem data có phải là mảng và có dữ liệu không
                if (Array.isArray(data) && data.length > 0) {
                    // Duyệt qua từng employee trong mảng data
                    data.forEach((employee, index) => {
                        const row = document.createElement('tr');
                        row.innerHTML = `
                        <td>${index + 1}</td>
                        <td>${employee.username || 'N/A'}</td>
                        <td>${employee.email || 'N/A'}</td>
                        <td>${employee.fullName || 'N/A'}</td>
                        <td>${employee.phoneNumber || 'N/A'}</td>
                        <td>${employee.address || 'N/A'}</td>
                        <td>${employee.createdAt || 'N/A'}</td>
                        <td>${employee.role || 'N/A'}</td>
                        <td>
                            <button class="btn btn-warning btn-sm" data-bs-toggle="modal" data-bs-target="#editModal" onclick="editEmployee(${employee.userId})">Sửa</button>
                            <button class="btn btn-danger btn-sm" onclick="deleteEmployee(${employee.userId})">Xóa</button>
                        </td>
                        `;
                        tableBody.appendChild(row);  // Thêm dòng vào bảng
                    });
                    new simpleDatatables.DataTable("#datatablesSimple");
                } else {
                    // Nếu không có dữ liệu, hiển thị thông báo
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
function deleteEmployee(userId) {
    fetch(`http://localhost:9090/admin/api/employees/delete/${userId}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
        },
    })
        .then(response => {
            if (response.ok) {
                console.log('Employee deleted successfully');
                location.reload();  // Làm mới trang sau khi xóa
            } else {
                console.error('Failed to delete employee');
            }
        })
        .catch(error => {
            console.error('Lỗi khi xóa employee:', error);
        });
}

// Hàm gọi khi nhấn nút "Sửa" để chỉnh sửa thông tin nhân viên
function editEmployee(userId) {
    // Lấy thông tin nhân viên từ API
    const user = users.find(s => s.userId === userId)

    if (user) {
        document.getElementById('userId').value = user.userId;
        document.getElementById('username').value = user.username;
        document.getElementById('userfullname').value = user.fullName;
        document.getElementById('userphoneNumber').value = user.phoneNumber;
        document.getElementById('useraddress').value = user.address;
        document.getElementById('userdate').value = user.createdAt;
        document.getElementById('userrole').value = user.role;
    }
}

document.getElementById('editForm').addEventListener('submit', function(event) {
    event.preventDefault();
    const userId = document.getElementById('userId').value;
    const updatedEmployee = {
        username: document.getElementById('username').value,
        fullName: document.getElementById('userfullname').value,
        phoneNumber: document.getElementById('userphoneNumber').value,
        address: document.getElementById('useraddress').value,
        createdAt: document.getElementById('userdate').value,
        role: document.getElementById('userrole').value
    };

    // Gửi yêu cầu PUT để cập nhật nhân viên
    fetch(`http://localhost:9090/admin/api/employees/edit/${userId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(updatedEmployee)
    })
        .then(response => response.json())
        .then(updatedData => {
            console.log(updatedData);
            $('#editModal').modal('hide');  // Ẩn modal
            location.reload();  // Làm mới trang
        })
        .catch(error => {
            console.error('Lỗi khi cập nhật employee:', error);
        });
});
