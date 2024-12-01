package vn.iotstar.entity;

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
@Table(name = "orderdetails")
public class OrderDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "OrderDetailID")
	private Integer orderDetailId;

	@ManyToOne
	@JoinColumn(name = "OrderID", referencedColumnName = "OrderID")
	private Order order;

	@ManyToOne
	@JoinColumn(name = "ProductID", referencedColumnName = "ProductID")
	private Product product;

	@Column(name = "Quantity")
	private Integer quantity;

	@Column(name = "Price")
	private Double price;
}