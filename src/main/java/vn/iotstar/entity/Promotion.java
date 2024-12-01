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
@Table(name = "promotions")
public class Promotion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PromotionID")
	private Integer promotionId;

	@Column(name = "Name", length = 100)
	private String name;

	@Column(name = "DiscountType", length = 15)
	private String discountType;

	@Column(name = "DiscountValue")
	private Double discountValue;

	@Column(name = "StartDate")
	private LocalDate startDate;

	@Column(name = "EndDate")
	private LocalDate endDate;

	@ManyToOne
	@JoinColumn(name = "ShopID", referencedColumnName = "ShopID")
	private Shop shop;

	@Column(name = "CreatedAt")
	private LocalDate createdAt;
}
