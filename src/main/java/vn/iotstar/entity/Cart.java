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
@Table(name = "cart")
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CartID")
	private Integer cartId;

	@ManyToOne
	@JoinColumn(name = "UserID", referencedColumnName = "UserID")
	private User user;

	@ManyToOne
	@JoinColumn(name = "ProductID", referencedColumnName = "ProductID")
	private Product product;

	@Column(name = "Quantity")
	private Integer quantity;

	@Column(name = "CreatedAt")
	private LocalDate createdAt;
}
