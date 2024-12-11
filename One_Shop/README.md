# 🌐 **XÂY DỰNG WEBSITE BÁN MỸ PHẨM ONESHOP **



Dự án này được xây dựng bởi các thành viên của nhóm 7 bao gồm Trần Trọng Thức, Nguyễn Trọng Nghĩa và Trần Như Thiện
https://github.com/thuctrantrong/One_shop



🚀 Mô Tả Dự Án
Dự án này nhằm xây dựng một website bán hàng trực tuyến cho các mỹ phẩm, với các chức năng nổi bật sau:

- Quản lý tài khoản người dùng: Quản trị viên có thể kiểm soát các tài khoản người dùng và vô hiệu hóa tài khoản có hành vi bất thường.
- Quản lý sản phẩm: Cung cấp giao diện để thêm, xóa, sửa sản phẩm.
- Quản lý danh mục sản phẩm: Tạo, xóa và chỉnh sửa các danh mục sản phẩm.
- Quản lý đơn hàng: Quản trị viên có thể cập nhật trạng thái đơn hàng của khách hàng.
- Tìm kiếm và lọc sản phẩm: Người dùng có thể tìm kiếm và lọc sản phẩm theo nhiều tiêu chí như loại danh mục, tên sản phẩm, v.v.
- Quản lý đơn hàng đã đặt: Người dùng có thể xem chi tiết đơn hàng, trạng thái và hủy nếu cần.
- Quản lý tài khoản cá nhân: Người dùng có thể tạo tài khoản, đăng nhập và thay đổi thông tin cá nhân.




💻 Các Công Nghệ Sử Dụng
- Frontend: HTML, CSS, JavaScript, BootStrap 3, 4, 5, Thymeleaf
- Backend: Java (Spring Boot), Spring Security, Spring Data JPA
- Database: MySQL
- Version Control: Git, GitHub




🧑‍💻 Cấu Trúc Thư Mục Dự Án
One_Shop_final
├── .idea/
├── .mvn/
├── .settings/
├── src/
    └── main/
        ├── java/
        │   └── vn.iotstar/
        │       ├── config/
        │       ├── controller/
        │       ├── entity/
        │       ├── repository/
        │       ├── service/
        │       └── util/
        │           └── OneShopApplication.java
        ├── resources/
            ├── static/
            │   ├── css/
            │   ├── img/
            │   ├── js/
            │   ├── lib/
            │   └── scss/
            ├── about.html
            ├── contact.html
            └── lie.html
        └── templates/
        └── Application.properties     




📦 Cài Đặt Dự Án
Cài Đặt Môi Trường Phát Triển 💻:
- Java JDK 17: Cài đặt từ Amazon Corretto.
- Maven: Cài đặt từ Maven Official Website.
- MySQL: Cài đặt và tạo cơ sở dữ liệu cho ứng dụng.
- IDE: Sử dụng IntelliJ IDEA hoặc Eclipse.



Clone Dự Án và Cài Đặt Dependencies 📦:
git clone https://github.com/thuctrantrong/One_shop
mvn clean install



Cấu Hình Cơ Sở Dữ Liệu 💾:
Tạo cơ sở dữ liệu trong MySQL, ví dụ: shopping_cart_clothes.
Cập nhật thông tin kết nối trong application.properties:
spring.datasource.url=jdbc:mysql://localhost:3306/One_Shop
spring.datasource.username=root
spring.datasource.password=your_password



Chạy Ứng Dụng 🚀:
mvn spring-boot:run



Truy Cập Website 🌐: Mở trình duyệt và truy cập vào địa chỉ: http://localhost:9090




📝 Đánh Giá và Phản Hồi
Mình rất mong nhận được phản hồi từ các bạn về các tính năng của website. Hãy chia sẻ suy nghĩ và góp ý của các bạn!



🙌 Cảm Ơn Bạn!
Cảm ơn bạn đã theo dõi dự án của chúng tôi. Hy vọng bạn sẽ có những trải nghiệm tuyệt vời khi sử dụng sản phẩm.