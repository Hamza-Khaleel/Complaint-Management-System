package com.example.complaint_system.Customer;


import com.example.complaint_system.Company.Company;
import com.example.complaint_system.Complaint.Complaint;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
//import jakarta.persistence.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "customer",schema = "public")
public class Customer {
    private String username;
    private String email;
    private String password;
    private Integer phone;
    private String address;
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer social_number;

    //one to manny relationship.................................................................. Customer // Complaint
//    @JsonManagedReference
//    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<Complaint> complaints= new ArrayList<Complaint>();

    // one to manny relationship.................................................................. Customer // Company
//    @JsonManagedReference
//    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<Company> companies= new ArrayList<Company>();

}
