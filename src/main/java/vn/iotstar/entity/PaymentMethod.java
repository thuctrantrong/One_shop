package vn.iotstar.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "paymentmethods")
public class PaymentMethod {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PaymentMethodID")
	private Integer paymentMethodId;

	@ManyToOne
	@JoinColumn(name = "UserID", referencedColumnName = "UserID")
	private User user;

	@Column(name = "MethodName")
	private String methodName;

	@Column(name = "CardNumber", length = 16)
	private String cardNumber;

	@Column(name = "ExpiryDate")
	private LocalDate expiryDate;

	@Column(name = "CreatedAt")
	private LocalDate createdAt;
}
