package com.example.complaint_system.Company;

import com.example.complaint_system.Complaint.Complaint;
import com.example.complaint_system.Customer.Customer;
import com.example.complaint_system.employee.Employee;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "company",schema = "public")
public class Company {  // add image ............................................readmeeeeeeeeeeeeeeeeeeeeeeee

    private String name;
    private String type_company;
    private String locations;
    private Integer ratting_status;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //..............................................Emp//company
    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id",referencedColumnName = "social_number")
    private Employee employee;

    //............................................customer//company
//    @JsonBackReference
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "customer_id",referencedColumnName = "social_number")
//    private Customer customer;

    //........................................company//complaints
//    @JsonManagedReference
//    @OneToMany(mappedBy = "company",cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<Complaint>complaintList=new ArrayList<Complaint>();
////
//    @JsonManagedReference
//    @OneToMany(mappedBy = "company",cascade = CascadeType.ALL,orphanRemoval = true)
//    @JoinColumn(name = "complaint_id",referencedColumnName = "id_of_complaint")
//    private List<Complaint> complaints= new ArrayList<Complaint>();

    @JsonManagedReference
    @OneToMany(mappedBy = "company",cascade = CascadeType.ALL)
    private List<Complaint> complaints= new ArrayList<Complaint>();

}
