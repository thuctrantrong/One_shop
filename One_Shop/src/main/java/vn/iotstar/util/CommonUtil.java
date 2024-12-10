package vn.iotstar.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import vn.iotstar.entity.ProductOrder;
import vn.iotstar.entity.User;
import vn.iotstar.service.UserService;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.List;

@Component
public class CommonUtil {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private UserService userService;

	public Boolean sendMail(String url, String reciepentEmail) throws UnsupportedEncodingException, MessagingException {

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("nghia7hktvn@gmail.com", "Shooping Cart");
		helper.setTo(reciepentEmail);

		String content = "<p>Hello,</p>" + "<p>You have requested to reset your password.</p>"
				+ "<p>Click the link below to change your password:</p>" + "<p><a href=\"" + url
				+ "\">Change my password</a></p>";
		helper.setSubject("Password Reset");
		helper.setText(content, true);
		mailSender.send(message);
		return true;
	}

	public static String generateUrl(HttpServletRequest request) {

		// http://localhost:8080/forgot-password
		String siteUrl = request.getRequestURL().toString();

		return siteUrl.replace(request.getServletPath(), "");
	}

	String msg = null;

	public User getLoggedInUserDetails(Principal p) {
		String email = p.getName();
		User userDtls = userService.getUserByEmail(email);

		return userDtls;
	}
	public Boolean sendMailForProductOrder(List<ProductOrder> orders, String status) throws Exception {

		StringBuilder productDetails = new StringBuilder();
		double totalOrderPrice = 0.0;

		for (ProductOrder order : orders) {
			double totalPrice = order.getPrice() * order.getQuantity();
			totalOrderPrice += totalPrice;

			productDetails.append("<p><b>Product Name:</b> ").append(order.getProduct().getName()).append("</p>")
					.append("<p><b>Category:</b> ").append(order.getProduct().getCategory()).append("</p>")
					.append("<p><b>Quantity:</b> ").append(order.getQuantity()).append("</p>")
					.append("<p><b>Total Price:</b> ").append(String.format("%.2f", totalPrice)).append("</p>")
					.append("<hr>");
		}

		String msg = "<p>Hello [[name]],</p>"
				+ "<p>Thank you for your order. Your order status is <b>[[orderStatus]]</b>.</p>"
				+ "<p><b>Product Details:</b></p>" + productDetails.toString() + "<p><b>Total Order Price:</b> "
				+ String.format("%.2f", totalOrderPrice) + "</p>" + "<p><b>Payment Type:</b> [[paymentType]]</p>";

		// Gửi email
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		// Thông tin người gửi và người nhận
		helper.setFrom("nghia7hktvn@gmail.com", "One-Shop Cart");
		helper.setTo(orders.get(0).getOrderAddress().getEmail()); // Giả sử tất cả các đơn hàng có cùng địa chỉ email

		// Thay thế các placeholder trong email
		msg = msg.replace("[[name]]", orders.get(0).getOrderAddress().getFirstName());
		msg = msg.replace("[[orderStatus]]", status);
		msg = msg.replace("[[paymentType]]", orders.get(0).getPaymentType());

		helper.setSubject("Product Order Status");
		helper.setText(msg, true);

		// Gửi email
		mailSender.send(message);
		return true;
	}

}
