package com.example.complaint_system.Admin;

import com.example.complaint_system.Company.Company;
import com.example.complaint_system.Company.CompanyRepository;
import com.example.complaint_system.Complaint.Complaint;
import com.example.complaint_system.Complaint.ComplaintRepository;
import com.example.complaint_system.Customer.Customer;
import com.example.complaint_system.Customer.CustomerRepository;
import com.example.complaint_system.Customer.LogIn;
import com.example.complaint_system.employee.Employee;
import com.example.complaint_system.employee.EmployeeRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServices {
    @Autowired
    private  EmployeeRepository employeeRepository;
    private final ComplaintRepository complaintRepository;
    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public AdminServices(EmployeeRepository employeeRepository, ComplaintRepository complaintRepository, CompanyRepository companyRepository, CustomerRepository customerRepository) {
        this.employeeRepository = employeeRepository;
        this.complaintRepository = complaintRepository;
        this.companyRepository = companyRepository;
        this.customerRepository = customerRepository;
    }


    public void addCompany(Company company) {
        try {
            companyRepository.save(company);
        }
        catch (Exception e){
            return;
        }
    }


    public void deleteCompany(Integer idC){
        companyRepository.deleteById(idC);
    }

    public void editCompany(Company company) {
        try {
            companyRepository.save(company);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return;
        }
    }

    public List<Company> getAllComp() {
        return companyRepository.findAll();
    }





    public String SignUpEmp(Employee employee) {
        try {
            this.employeeRepository.save(employee);

            // Generate JWT
            String jwt = Jwts.builder()
                    .setSubject(employee.getEmail())
                    .signWith(SignatureAlgorithm.HS256, "secretkey")
                    .compact();

            return jwt;
        } catch (Exception e) {
            return "failed";
        }
    }

    public void deleteEmployee(Integer ide) {
        try {
            Optional<Employee>employeeOptional=employeeRepository.findById(ide);
            if (employeeOptional.isPresent()){
                employeeRepository.deleteById(ide);

            }

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public void editEmp(Employee employee) {
        try {
            employeeRepository.save(employee);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return;
        }
    }

    public List<Employee> getAllEmp() {
        return employeeRepository.findAll();
    }



    public void deleteCus(Integer idc) {
        try {
            Optional<Customer>customerOptional=customerRepository.findById(idc);
            if (customerOptional.isPresent()){
                customerRepository.deleteById(idc);
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public List<Customer> getAllCus() {
        return customerRepository.findAll();
    }



    public void deleteCom(Integer idc) {
        complaintRepository.deleteById(idc);
    }
    public List<Complaint> getAllCom() {
        return complaintRepository.findAll();
    }


    public Employee signIn(LogIn body) {
//        try {
        Optional<Employee> employeeOptional = Optional.ofNullable(this.employeeRepository.findAllByEmailAndPassword(body.getEmail(), body.getPassword()));
        if (employeeOptional.isPresent()) {
            // Generate JWT
//                String jwt = Jwts.builder()
//                        .setSubject(customerOptional.get().getEmail())
//                        .signWith(SignatureAlgorithm.HS256, "secretkey")
//                        .compact();
//
//                return jwt;
            return employeeRepository.findAllByEmailAndPassword(body.email, body.password);
        } else {
            Optional<Customer> optionalCustomer = Optional.ofNullable(this.customerRepository.findAllByEmail(body.getEmail()));
            if (optionalCustomer.isPresent()) {
//                    return "Wrong Password";
            }
//                return "No User entered data";
        }


//        } catch (Exception e) {
////            return "Failed";
//        }
        return null;
    }

    public Long getNumOfEmp() {
        try {
            return employeeRepository.count();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Long getNumOfCompanies() {
        try {
            return companyRepository.count();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Long getNumOfCustomers() {
        try {
            return customerRepository.count();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Long getNumOfComplaints() {
        try {
            return complaintRepository.count();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }


}
