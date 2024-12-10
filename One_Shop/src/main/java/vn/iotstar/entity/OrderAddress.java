package vn.iotstar.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
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

    private String pincode;
}
