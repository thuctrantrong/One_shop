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
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;

	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false)
	private String passwordHash;

	@Column(nullable = false, unique = true)
	private String email;

	@Column
	private String fullName;

	@Column
	private String phoneNumber;

	@Column(columnDefinition = "NVARCHAR(MAX)")
	private String address;

	@Column(nullable = false)
	private String role = "Customer";

	@Column(nullable = false, columnDefinition = "DATETIME DEFAULT GETDATE()")
	private LocalDateTime createdAt;
}
