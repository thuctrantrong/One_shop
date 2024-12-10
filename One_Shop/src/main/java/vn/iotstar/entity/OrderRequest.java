package vn.iotstar.entity;

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
import lombok.ToString;


@ToString
@Data
public class OrderRequest {
	@Column(columnDefinition = "NVARCHAR(255)")
	private String firstName;
	@Column(columnDefinition = "NVARCHAR(255)")
	private String lastName;
	@Column(columnDefinition = "NVARCHAR(255)")
	private String email;
	@Column(columnDefinition = "NVARCHAR(255)")
	private String mobileNo;
	@Column(columnDefinition = "NVARCHAR(255)")
	private String address;
	@Column(columnDefinition = "NVARCHAR(255)")
	private String city;
	@Column(columnDefinition = "NVARCHAR(255)")
	private String state;
	@Column(columnDefinition = "NVARCHAR(255)")
	private String pincode;
	@Column(columnDefinition = "NVARCHAR(255)")
	private String paymentType;

}
