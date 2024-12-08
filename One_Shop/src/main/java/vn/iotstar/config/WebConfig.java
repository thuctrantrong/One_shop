package vn.iotstar.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Cấu hình để Spring Boot có thể phục vụ tệp từ thư mục "uploads"
        registry.addResourceHandler("/uploads/**")  // Đường dẫn yêu cầu tệp
                .addResourceLocations("file:uploads/");  // Đường dẫn nơi tệp được lưu trữ, file:/ để chỉ định thư mục gốc
    }
}
