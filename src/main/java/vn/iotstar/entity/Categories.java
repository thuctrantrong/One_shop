package vn.iotstar.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
@Table(name = "Categories")
public class Categories {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int categoryId;

	@Column(nullable = false)
	private String name;

	@Column(columnDefinition = "NVARCHAR(MAX)")
	private String description;

	@Column(nullable = false, columnDefinition = "DATETIME DEFAULT GETDATE()")
	private LocalDateTime createdAt;
}
