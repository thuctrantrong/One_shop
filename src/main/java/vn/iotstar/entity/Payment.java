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
@Table(name = "payments")
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PaymentID")
	private Integer paymentId;

	@ManyToOne
	@JoinColumn(name = "OrderID", referencedColumnName = "OrderID")
	private Order order;

	@ManyToOne
	@JoinColumn(name = "PaymentMethodID", referencedColumnName = "PaymentMethodID")
	private PaymentMethod paymentMethod;

	@Column(name = "Amount")
	private Double amount;

	@Column(name = "PaymentStatus")
	private String paymentStatus;

	@Column(name = "PaymentDate")
	private LocalDate paymentDate;
}
