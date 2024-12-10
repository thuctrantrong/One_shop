package vn.iotstar.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
@Table(name = "Cart")
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cartId;

	@ManyToOne
	private User user;

	@ManyToOne
	private Product product;

	private int quantity;

	@Transient
	private Double totalPrice;

	@Transient
	private Double totalOrderPrice;

	private LocalDateTime createdAt;

}
