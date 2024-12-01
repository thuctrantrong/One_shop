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
@Table(name = "reviews")
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ReviewID")
	private Integer reviewId;

	@ManyToOne
	@JoinColumn(name = "ProductID", referencedColumnName = "ProductID")
	private Product product;

	@ManyToOne
	@JoinColumn(name = "UserID", referencedColumnName = "UserID")
	private User user;

	@Column(name = "Rating")
	private Integer rating;

	@Column(name = "Comment", columnDefinition = "TEXT")
	private String comment;

	@Column(name = "MediaURL", length = 255)
	private String mediaUrl;

	@Column(name = "CreatedAt")
	private LocalDate createdAt;
}