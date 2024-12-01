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
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "OrderID")
	private Integer orderId;

	@ManyToOne
	@JoinColumn(name = "UserID", referencedColumnName = "UserID")
	private User user;

	@Column(name = "TotalAmount")
	private Double totalAmount;

	@Column(name = "OrderStatus", length = 50)
	private String orderStatus;

	@Column(name = "CreatedAt")
	private LocalDate createdAt;

	@Column(name = "UpdatedAt")
	private LocalDate updatedAt;
}
