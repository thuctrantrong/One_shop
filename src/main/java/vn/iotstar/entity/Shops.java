package vn.iotstar.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Shops")
public class Shops {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer shopId;

	@OneToOne
	@JoinColumn(name = "ownerId", nullable = false)
	private Users owner;

	@Column(nullable = false)
	private String name;

	@Column(columnDefinition = "NVARCHAR(MAX)")
	private String description;

	@Column(nullable = false, columnDefinition = "DATETIME DEFAULT GETDATE()")
	private LocalDateTime createdAt;

}
