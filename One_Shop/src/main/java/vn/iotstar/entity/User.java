package vn.iotstar.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
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
	private int userID;
	@Column(columnDefinition = "NVARCHAR(255)")
	private String username;
	@Column(columnDefinition = "NVARCHAR(255)")
	private String passwordHash;
	@Column(columnDefinition = "NVARCHAR(255)")
	private String email;

	@Column(columnDefinition = "nvarchar(50)")
	private String fullName;
	@Column(columnDefinition = "NVARCHAR(255)")
	private String phoneNumber;
	@Column(columnDefinition = "NVARCHAR(255)")
	private String address;

	private String role;

	private LocalDateTime createdAt;

	private String resetToken;

}
