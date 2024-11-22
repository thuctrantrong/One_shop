package vn.iotstar.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
@Table(name = "PaymentMethods")
public class PaymentMethods {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer paymentMethodId;

	@ManyToOne
	@JoinColumn(name = "userId", nullable = false)
	private Users user;

	@Column(nullable = false, length = 50)
	private String methodName;

	@Column(length = 16)
	private String cardNumber;

	@Column
	private LocalDate expiryDate;

	@Column(nullable = false, columnDefinition = "DATETIME DEFAULT GETDATE()")
	private LocalDateTime createdAt;

}
