package vn.iotstar.entity;

import java.time.LocalDate;

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
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "UserID")
	private Integer userId;

	@Column(name = "Username", length = 50)
	private String username;

	@Column(name = "PasswordHash", length = 255)
	private String passwordHash;

	@Column(name = "Email", length = 100)
	private String email;

	@Column(name = "FullName", length = 100)
	private String fullName;

	@Column(name = "PhoneNumber", length = 15)
	private String phoneNumber;

	@Column(name = "Address", columnDefinition = "TEXT") // Changed to TEXT
	private String address;

	@Column(name = "Role", length = 50)
	private String role;

	@Column(name = "CreatedAt")
	private LocalDate createdAt;
}