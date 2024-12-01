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
@Table(name = "shops")
public class Shop {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ShopID")
	private Integer shopId;

	@ManyToOne
	@JoinColumn(name = "OwnerID", referencedColumnName = "UserID")
	private User owner;

	@Column(name = "Name", length = 100)
	private String name;

	@Column(name = "Description", columnDefinition = "TEXT")
	private String description;

	@Column(name = "CreatedAt")
	private LocalDate createdAt;
}
