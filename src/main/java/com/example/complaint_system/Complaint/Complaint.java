package com.example.complaint_system.Complaint;

import com.example.complaint_system.Company.Company;
import com.example.complaint_system.Customer.Customer;
import com.example.complaint_system.employee.Employee;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
//import jakarta.persistence.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "complaint",schema = "public")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id_of_complaint")
public class Complaint {  // add image ..................................................read meeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
    private String type_company;
    private String description;
    private String location;
    private Integer company_id;
    private Integer customer_id;
    private String companyName;
    private String status;
    private String type_complaint;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_of_complaint;
    //.....................................employee//complaint
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id",referencedColumnName = "social_number")
    private Employee Employee;

    //...................................customer//complaint
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "customer_id",referencedColumnName = "social_number")
//    private Customer customer;
//........................................company//complaint
//    @JsonBackReference
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "company_id",referencedColumnName = "id")
//    private Company company;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id",referencedColumnName = "id",insertable = false,updatable = false)
    private Company company;

}
