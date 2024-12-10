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
@Table(name = "Products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ProductID")
	private int productId;
	@Column(columnDefinition = "NVARCHAR(255)")
	private String name;

	@Column(columnDefinition = "NVARCHAR(MAX)")
	private String description;

	private Double price;

	private int stock;

	@ManyToOne
	@JoinColumn(name = "CategoryId")
	private Category category;
	@Column(columnDefinition = "NVARCHAR(255)")
	private String imageUrl;


	private LocalDateTime createdAt;

	private int condition;


	private int soldQuantity;

}
