package com.example.complaint_system.employee;

import com.example.complaint_system.Company.Company;
import com.example.complaint_system.Complaint.Complaint;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@Table(name = "employee",schema = "public")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "social_number")

public class Employee {
    private String username;
    private String email;
    private String password;
    private Integer phone;
    private String address;
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer social_number;

   // one to manny relationship.................................................................. Employee // Company
    @JsonManagedReference
    @OneToMany(mappedBy = "employee",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Company> companies= new ArrayList<Company>();

    //one to manny relationship.................................................................. Employee // Complaint
    @OneToMany(mappedBy = "Employee",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Complaint> complaints= new ArrayList<Complaint>();


}
