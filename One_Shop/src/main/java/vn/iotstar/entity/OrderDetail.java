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
@Table(name = "OrderDetails")
public class OrderDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderDetailId;

	@ManyToOne
	@JoinColumn(name = "OrderId", nullable = false)
	private Order order;

	@ManyToOne
	@JoinColumn(name = "ProductId", nullable = false)
	private Product product;

	@Column(nullable = false)
	private int quantity;

	@Column(nullable = false)
	private Double price;

}
