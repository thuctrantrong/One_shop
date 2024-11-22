package vn.iotstar.entity;

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
@Table(name = "Payments")
public class Payments {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer paymentId;

	@ManyToOne
	@JoinColumn(name = "orderId", nullable = false)
	private Orders order;

	@ManyToOne
	@JoinColumn(name = "paymentMethodId")
	private PaymentMethods paymentMethod;

	@Column(nullable = false)
	private Double amount;

	@Column(nullable = false, length = 15)
	private String paymentStatus;

	@Column(nullable = false, columnDefinition = "DATETIME DEFAULT GETDATE()")
	private LocalDateTime createdAt;

}
