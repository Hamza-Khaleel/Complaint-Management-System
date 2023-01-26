package com.example.complaint_system.employee;

import com.example.complaint_system.Company.Company;
import com.example.complaint_system.Company.CompanyRepository;
import com.example.complaint_system.Complaint.Complaint;
import com.example.complaint_system.Complaint.ComplaintRepository;
import com.example.complaint_system.Customer.Customer;
import com.example.complaint_system.Customer.CustomerRepository;
import com.example.complaint_system.Customer.LogIn;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.SneakyThrows;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ComplaintRepository complaintRepository;
    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;


    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, ComplaintRepository complaintRepository, CompanyRepository companyRepository,
                           CustomerRepository customerRepository) {
        this.employeeRepository = employeeRepository;
        this.complaintRepository = complaintRepository;
        this.companyRepository = companyRepository;
        this.customerRepository = customerRepository;
    }

    // ......................................................................Done.
    public List<Company> getAllCompanies(Integer id){
        try {
//            List<Company> temp=new ArrayList<Company>(Integer.parseInt("No Company assigned"));
            Optional<Employee> optionalEmployee = employeeRepository.findById(id);
            if (optionalEmployee.isPresent()) {
                return optionalEmployee.get().getCompanies();
//                return optionalEmployee.get().getCompanies();
            } else {
                System.out.println("1111111111111111111111111111111111111111111111111111111");
                return new ArrayList<>();
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }


    public List<Complaint> getComplaints(Integer id){
        try {
            Optional<Company> companyOptional=companyRepository.findById(id);

//            Optional<Employee>optionalEmployee=employeeRepository.findById(id);
            if (companyOptional.isPresent()){
                System.out.println("company exisit.");

                return companyOptional.get().getComplaints();
            }
            else {
                                System.out.println("company didnt exisit.");

                return new ArrayList<>();
            }

//            Optional<Company> optionalCompany = companyRepository.findById(id);
////        List<Complaint> complaints_1=complaintRepository.findAllByEmployee(employeeOptional.get());
//            if (optionalCompany.isPresent()) {
//                return optionalCompany.get().getComplaints();
//            } else {
//                System.out.println("1111111111111111111111111111111111111111111111111111111");
//                return new ArrayList<>();
//            }
        }
        catch (Exception e){
                      System.out.println("expeption................................");

            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    public Object getCompany(String idE,String idC){
        try {


            Integer empid = Integer.parseInt(idE);
            Integer compid = Integer.parseInt(idC);
            Optional<Employee> employee = employeeRepository.findById(empid);
            Optional<Company> company = companyRepository.findById(compid);
            if (employee.isPresent() && company.isPresent()) {
                return employee.get().getCompanies().get(compid);
            } else return null;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    // auto increment problem....................................????????????????????????????
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

    public void addCompany(Company company) {
        try {
            companyRepository.save(company);
        }
        catch (Exception e){
            return;
        }
    }

    public void deleteComplaint(Integer id) {
        try {
            Optional<Complaint> optionalComplaint=complaintRepository.findById(id);
            if(optionalComplaint.isPresent()){
                complaintRepository.findById(id).get().setCompany_id(0);
                complaintRepository.deleteById(id);
            }
            else System.out.println("complaint does not exist");
        }
        catch (Exception e){
            System.out.println("there is an exception : "+e.getMessage());
            return;
        }
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

    public void deleteCompany(Integer idC){
        companyRepository.deleteById(idC);
    }

    public List<Company> getCompType(String type) {
        try {
         //   return complaintRepository.findAllByType(type);
            return  companyRepository.findAllByType(type);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return new ArrayList<>();
    }

    public Optional<Employee> getEmpById(Integer social) {
        try {
            Optional<Employee>employeeOptional=employeeRepository.findById(social);
            if (employeeOptional.isPresent()){
                return employeeRepository.findById(social);

            }

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Optional<Company> getCompById(Integer id) {
        try {
            Optional<Company>companyOptional=companyRepository.findById(id);
            if (companyOptional.isPresent()){
                return companyRepository.findById(id);
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void editStatusOfComplaint(Complaint complaint) {
        try {
            complaintRepository.save(complaint);

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Optional<Complaint> getComplaintById(Integer id) {
        try {
            Optional<Complaint>complaintOptional=complaintRepository.findById(id);
            if (complaintOptional.isPresent()){
                return complaintRepository.findById(id);
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Optional<Customer> getCustomerById(Integer id) {
        try {
            Optional<Customer>customerOptional=customerRepository.findById(id);
            if (customerOptional.isPresent()){
                return customerRepository.findById(id);
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Complaint> getAllComplaintByType(String type) {
        return complaintRepository.findAllByComplaintType(type);
    }

    public double numComplaintsByType(String type) {
        double temp1=complaintRepository.findAllByComplaintType(type).stream().count();
         double temp2= complaintRepository.findAll().stream().count();
         return (temp1/temp2)*100;
    }

    @Autowired
    JavaMailSender mailSender;
    @SneakyThrows
    public String OTP_OperationStatus(String email, String status, String name)  {
//        String OTP = status;
//        System.out.println(email);
        Customer player = customerRepository.findAllByEmail(email);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("hamza.sa.khalil@gmail.com", "جمعية حماية المستهلك");
        helper.setTo(email);
        String subject = "تحديثا عن شكواك";
        String content = "<p>Hello: " + player.getUsername() +"</p>"+
                "<p>Your complaint about " + name + " has been received and it has been: " + status+"</p>"
                + "<br>"
                + "<p>Note: we will notify you when any progress is made, you can check the CMS App to see the status of your complaint .</p>";

//        "<p>Dear " + player.getUsername() + "</p>"
//                + "<p>From CMS staff about some complaint you did "
//                + "One Time Password to login:</p>"
//                + "<p><b>" + OTP.toString() + "</b></p>"
//                + "<br>"
//                + "<p>Note: this OTP is set to expire in 5 minutes.</p>";
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);

        return "success";

    }


//    public String resetPassword(String email, Integer social,String newPassword) {
//        try{
////            Optional<Employee>optionalEmployee= Optional.ofNullable(employeeRepository.findAllByEmail(email));
//            Optional<Employee>employee=employeeRepository.findById(social);
//            if (employee.isPresent()){
//                employeeRepository.setPassword(newPassword);
//                return "The password is updated";
//            }
//            else return "the employee doesn't exist";
//
//        }
//        catch (Exception e){
//            return e.getMessage();
//        }
//
//    }


//    public List<Company> getCompanies(Integer id) {
//    Optional<Employee> optionalEmployee=employeeRepository.findById(id);
//    List<Company> companies=optionalEmployee.get().getCompanies();
//    return companies;
//    }

//     return all companies for specific employee


    // make a controller.............................................................................continu here
//    public Company SearchCompany(Integer id,String name){
//        Optional<Employee> employeeOptional_1= employeeRepository.findById(id);
//        List<Company>temp =employeeOptional_1.get().getCompanies();
//        for (int i=0;i< temp.size();i++){
//            if(temp.get(i).getName().equals(name)){
//                return temp.get(i);
//            }
//
//        }
//        return null;
//    }






//
//    public List<Company> get_All_company_of_that_type(String typeCompany) {
//        return employeeRepository
//    }
}
