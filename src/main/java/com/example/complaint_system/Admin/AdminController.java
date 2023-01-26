package com.example.complaint_system.Admin;

import com.example.complaint_system.Company.Company;
import com.example.complaint_system.Complaint.Complaint;
import com.example.complaint_system.Customer.Customer;
import com.example.complaint_system.Customer.LogIn;
import com.example.complaint_system.employee.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path = "api/complaintsystem/admin/")
public class AdminController {
    private final AdminServices adminServices;

    @Autowired
    public AdminController(AdminServices adminServices) {
        this.adminServices = adminServices;
    }


    @PostMapping("/signIn")
    public Employee signIn(@RequestBody LogIn body){
        return adminServices.signIn(body);
    }

    //Company
    //add
    @PostMapping("addCompany")
    public void addNewCompany(@RequestBody Company company){
        adminServices.addCompany(company);
    }
    //edit
    @PutMapping("editCompany")
    public void editCompany(@RequestBody Company company){ adminServices.editCompany(company);}
    //delete
    @DeleteMapping("deleteCompany")
    public void deleteCompany(@RequestParam Integer idC){
        adminServices.deleteCompany(idC);
    }
    //get
    @GetMapping("getAllComp")
    public List<Company> getAllCompany(){
        return adminServices.getAllComp();
    }


    //Employee
    //add
    @PostMapping(path = "registerEmployee")
    public String SignUp(@RequestBody Employee employee )
    {
        return this.adminServices.SignUpEmp(employee);
    }
    //edit
    @PutMapping("editEmployee")
    public void editEmp(@RequestBody Employee employee){
        adminServices.editEmp(employee);
    }
    //delete
    @DeleteMapping("deleteEmployee")
    public void  deleteEmp(@RequestParam Integer ide){
        adminServices.deleteEmployee(ide);
    }
    //get all
    @GetMapping("getAllEmployee")
    public List<Employee> getAllEmp(){
        return adminServices.getAllEmp();
    }


    //Customer
    //delete
    @DeleteMapping("deleteCustomer")
    public void  deleteCus(@RequestParam Integer idc){
        adminServices.deleteCus(idc);
    }
    //get
    @GetMapping("getAllCustomer")
    public List<Customer> getAllCus(){
        return adminServices.getAllCus();
    }


    //Complaint
    //delete
    @DeleteMapping("deleteComplaint")
    public void  deleteCom(@RequestParam Integer idc){
        adminServices.deleteCom(idc);
    }
    //get
    @GetMapping("getAllComplaint")
    public List<Complaint> getAllCom(){
        return adminServices.getAllCom();
    }


    @GetMapping("getNumberOfEmployee")
    public Long getNumOfEmp(){
        return adminServices.getNumOfEmp();
    }

    @GetMapping("getNumberOfCompanies")
    public Long getNumOfCompanies(){
        return adminServices.getNumOfCompanies();
    }

    @GetMapping("getNumberOfCustomers")
    public Long getNumOfCustomers(){
        return adminServices.getNumOfCustomers();
    }

    @GetMapping("getNumberOfComplaints")
    public Long getNumOfComplaints(){
        return adminServices.getNumOfComplaints();
    }


}
