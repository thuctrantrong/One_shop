package vn.iotstar.entity;

import java.time.LocalDate;
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
@Table(name = "Promotions")
public class Promotions {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer promotionId;

	@Column(nullable = false, length = 100)
	private String name;

	@Column(nullable = false, length = 15)
	private String discountType; // Percentage or Flat

	@Column(nullable = false)
	private Double discountValue;

	@Column(nullable = false)
	private LocalDate startDate;

	@Column(nullable = false)
	private LocalDate endDate;

	@ManyToOne
	@JoinColumn(name = "userId")
	private Users createdBy;

	@Column(nullable = false, columnDefinition = "DATETIME DEFAULT GETDATE()")
	private LocalDateTime createdAt;

}
