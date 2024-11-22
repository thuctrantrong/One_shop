package vn.iotstar.entity;

import java.time.LocalDateTime;
import java.util.Locale.Category;

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
public class Products {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer productId;

	@Column(nullable = false)
	private String name;

	@Column(columnDefinition = "NVARCHAR(MAX)")
	private String description;

	@Column(nullable = false)
	private Double price;

	@Column(nullable = false)
	private Integer stock;

	@ManyToOne
	@JoinColumn(name = "categoryId")
	private Category category;

	@Column
	private String imageUrl;

	@Column(nullable = false, columnDefinition = "DATETIME DEFAULT GETDATE()")
	private LocalDateTime createdAt;

}
