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
@Table(name = "Users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;

	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false, length = 255)
	private String passwordHash;

	@Column(nullable = false, unique = true, length = 100)
	private String email;

	@Column(length = 100)
	private String fullName;

	@Column(length = 15)
	private String phoneNumber;

	@Column(columnDefinition = "NVARCHAR(MAX)")
	private String address;

	@Column(nullable = false)
	private String role = "ROLE_USER";

	@Column(nullable = false, columnDefinition = "DATETIME DEFAULT GETDATE()")
	private LocalDateTime createdAt;
}
