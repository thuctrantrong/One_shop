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
@Table(name = "Reviews")
public class Reviews {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer reviewId;

	@ManyToOne
	@JoinColumn(name = "productId", nullable = false)
	private Products product;

	@ManyToOne
	@JoinColumn(name = "userId", nullable = false)
	private Users user;

	@Column(nullable = false)
	private Integer rating;

	@Column(columnDefinition = "NVARCHAR(MAX)")
	private String comment;

	@Column(length = 255)
	private String mediaUrl;

	@Column(nullable = false, columnDefinition = "DATETIME DEFAULT GETDATE()")
	private LocalDateTime createdAt;

}
