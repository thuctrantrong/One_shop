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
public class OrderDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderDetailId;

	@ManyToOne
	@JoinColumn(name = "orderId", nullable = false)
	private Orders order;

	@ManyToOne
	@JoinColumn(name = "productId", nullable = false)
	private Products product;

	@Column(nullable = false)
	private Integer quantity;

	@Column(nullable = false)
	private Double price;

}
