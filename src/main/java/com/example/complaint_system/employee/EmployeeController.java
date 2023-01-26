package com.example.complaint_system.employee;

import com.example.complaint_system.Company.Company;
import com.example.complaint_system.Company.CompanyRepository;
import com.example.complaint_system.Complaint.Complaint;
import com.example.complaint_system.Customer.Customer;
import com.example.complaint_system.Customer.LogIn;
import com.example.complaint_system.Customer.StringBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path = "api/complaintsystem/employee/")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeController(EmployeeService employeeService,
                              CompanyRepository companyRepository,
                              EmployeeRepository employeeRepository) {
        this.employeeService = employeeService;
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
    }
 // work ...................................................................................................done.
    @GetMapping(path = "getallcompany")
    public List<Company> ViewAllCompanies(@RequestParam Integer id){
        return employeeService.getAllCompanies(id);
    }
   // .......................................................................................................


    @GetMapping(path = "getallcomplaint")   // return all complaints to that company
    public List<Complaint> ViewAllComplaints(@RequestParam Integer id){
        return employeeService.getComplaints(id);
    }

    // upload pictures to server

    @GetMapping("getCompByType")
    public List<Company> getAllCompanyByType(@RequestParam String type){
        return employeeService.getCompType(type);
    }

    // add new company
    // works ......................................... done. but when you add it do not fill id , keep it empty.
    @PostMapping("addCompany")
    public void addNewCompany(@RequestBody Company company){
        employeeService.addCompany(company);
    }

    @PutMapping("editCompany")
    public void editCompany(@RequestBody Company company){ employeeService.editCompany(company);}

    @DeleteMapping("deleteCompany")
    public void deleteCompany(@RequestParam Integer idC){
        employeeService.deleteCompany(idC);
    }

//    public List<Complaint>ReturnAllComplaints(@PathVariable Integer id ){
//
//
//    }

    //download file
    @GetMapping("/downloadFile/{fileCode}")
    public ResponseEntity<?> downloadFile(@PathVariable("fileCode") String fileCode) {
        FileDownloadUtil downloadUtil = new FileDownloadUtil();

        Resource resource = null;
        try {
            resource = downloadUtil.getFileAsResource(fileCode);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }

        if (resource == null) {
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }

        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }

    @PostMapping(path = "/registerEmployee")
    public String SignUp(@RequestBody Employee employee )
    {
        return this.employeeService.SignUpEmp(employee);
    }

    @PostMapping("/signIn")
    public Employee signIn(@RequestBody LogIn body){
        return employeeService.signIn(body);
    }

    //work .....................................................done.
    @DeleteMapping ("/deleteComplaint")
    public void deleteComplaint(@RequestParam Integer id){
        employeeService.deleteComplaint(id);
    }

    @GetMapping("/getEmployeeById")
    public Optional<Employee> getEmpById(@RequestParam Integer Social){
        return  employeeService.getEmpById(Social);
    }

//    @PutMapping("/resetPassword")
//    public String resetPassword(@RequestParam String email,@RequestParam String oldPassword,@RequestParam String newPassword){
//        return employeeService.resetPassword(email,oldPassword,newPassword);
//    }
    @GetMapping("/getCompanyById")
    public Optional<Company> getComById(@RequestParam Integer id){
        return employeeService.getCompById(id);
    }
//    @PutMapping("/editStatusOfComplaint")
//    public void editStatus(@RequestParam Integer idc,@RequestParam String status){
//        employeeService.editStatusOfComplaint(idc,status);
//    }

    @PutMapping("/editStatusOfComplaint")
    public void editStatus(@RequestBody Complaint complaint){
        employeeService.editStatusOfComplaint(complaint);
    }

    @GetMapping("/getComplaintById")
    public Optional<Complaint> getComplaint(@RequestParam Integer id){
        return employeeService.getComplaintById(id);
    }

    @GetMapping("/getCustomerById")
    public Optional<Customer> getCustomerById(@RequestParam Integer id){
        return employeeService.getCustomerById(id);
    }

    @GetMapping("/getAllComplaintsByType")
    public List<Complaint> getAllComplaintByType(@RequestParam String type){
        return employeeService.getAllComplaintByType(type);
    }

    @GetMapping("/getNubmerOfComplaintsByType")
    public double numComplaintsByType(@RequestParam String type){
        return employeeService.numComplaintsByType(type);
    }

//    @RequestParam String email, @RequestParam String status,@RequestParam String name
    @PostMapping(path = "/notifyCustomer")
    public String notifyCustomer(@RequestBody Notify message)
    {
        try {
            return this.employeeService.OTP_OperationStatus(message.email,message.status,message.name);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return "error";
        }
    }
}
