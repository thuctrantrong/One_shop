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

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ProductID")
	private Integer productId;

	@Column(name = "Name", length = 100)
	private String name;

	@Column(name = "Description", columnDefinition = "TEXT")
	private String description;

	@Column(name = "Price")
	private Double price;

	@Column(name = "Stock")
	private Integer stock;

	@ManyToOne
	@JoinColumn(name = "CategoryID", referencedColumnName = "CategoryID")
	private Category category;

	@Column(name = "ImageURL", length = 255)
	private String imageUrl;

	@ManyToOne
	@JoinColumn(name = "ShopID", referencedColumnName = "ShopID")
	private Shop shop;

	@Column(name = "CreatedAt")
	private LocalDate createdAt;

}
