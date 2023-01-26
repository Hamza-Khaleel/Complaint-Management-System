package com.example.complaint_system.Customer;

import com.example.complaint_system.Company.Company;
import com.example.complaint_system.Company.CompanyRepository;
import com.example.complaint_system.Complaint.Complaint;
import com.example.complaint_system.Complaint.ComplaintRepository;
import com.example.complaint_system.employee.EmployeeRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Service
//@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, MailSenderAutoConfiguration.class})
public class CustomerService {

    @Autowired
    private  CustomerRepository customerRepository;
    private final CompanyRepository companyRepository;
    private final ComplaintRepository complaintRepository;

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    JavaMailSender mailSender;


    @Autowired
    public CustomerService(CustomerRepository customerRepository,
                           CompanyRepository companyRepository,
                           ComplaintRepository complaintRepository) {
        this.customerRepository = customerRepository;
        this.companyRepository = companyRepository;
        this.complaintRepository = complaintRepository;
    }

    public List<Company> getAllCompanies(){
        try {
            return companyRepository.findAll();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    public Integer getRate(String idCo) {
        try {
            int temp=Integer.parseInt(idCo);
            Optional<Company> companyOptional=Optional.ofNullable(this.companyRepository.findAllById(temp));
            if(companyOptional.isPresent()){
                return companyOptional.get().getRatting_status();
            }
            else return -1;

        }
        catch (Exception e){
            return -1;
        }
    }

//    public void makeComplaintToCompany(Complaint complaint){
//        Optional<Company>optionalCompany=companyRepository.findById(idCompany);
//        if(optionalCompany.isPresent()){
//            //take the data from "Complaint" and put it inside a new row in complaint table
//            //optionalCompany.get().getComplaintList().get(idCompany).setId_of_complaint(complaint.getId_of_complaint());
//
//        }

//    }

    //make an auto incremenet for id_of_complaint
    public int idCounter=3;
    public void makeCompliantToCompany(Complaint complaint) {
        try {
            idCounter++;
            complaint.setId_of_complaint(idCounter);
            complaintRepository.save(complaint);
        }
        catch (Exception e){
            return;

        }
    }


    // auto increment problem ..........................????????????????????????????????
    public String SignUpCus(Customer customer) {
        try {
            customerRepository.save(customer);

            // Generate JWT
            String jwt = Jwts.builder()
                    .setSubject(customer.getEmail())
                    .signWith(SignatureAlgorithm.HS256, "secretkey")
                    .compact();

            return jwt;
        } catch (Exception e) {
            return "failed";
        }
    }

    // edit the information of the customer
    public void editInfo(Customer customer) {
        try {
            customerRepository.save(customer);
        }
        catch (Exception e){
            return;
        }

    }

    public Company searchForCompany(String name) {
        try {
          return companyRepository.findByName(name);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    public Customer signIn(LogIn body) {
//        try {
            Optional<Customer> customerOptional = Optional.ofNullable(this.customerRepository.findAllByEmailAndPassword(body.getEmail(), body.getPassword()));
            if (customerOptional.isPresent()) {
                // Generate JWT
//                String jwt = Jwts.builder()
//                        .setSubject(customerOptional.get().getEmail())
//                        .signWith(SignatureAlgorithm.HS256, "secretkey")
//                        .compact();
//
//                return jwt;
                return customerRepository.findAllByEmailAndPassword(body.email, body.password);
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

    public List<Complaint> getAllComplaints(Integer id) {
        try {
            Optional<Customer>customerOptional=customerRepository.findById(id);
            if (customerOptional.isPresent()){

                return complaintRepository.findAllByCustomerId(id);

            }
            else return new ArrayList<>();
        }
        catch (Exception e){
            System.out.println(e.getMessage());

        }
        return null;
    }



    public Integer OTP_OperationPlayer(String email) throws MessagingException, UnsupportedEncodingException {
        Integer OTP = ThreadLocalRandom.current().nextInt(1000, 9999 + 1);
        Customer player = customerRepository.findAllByEmail(email);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("hamza.sa.khalil@gmail.com", "CMS Support");
        helper.setTo(email);
        String subject = "Here's your One Time Password (OTP) - Expire in 5 minutes!";
        String content = "<p>Hello Customer " + player.getUsername() + "</p>"
                + "<p>For security reason, you're required to use the following "
                + "One Time Password to login:</p>"
                + "<p><b>" + OTP.toString() + "</b></p>"
                + "<br>"
                + "<p>Note: this OTP is set to expire in 5 minutes.</p>";
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);

        return OTP;
    }


//    public void changePasswordThroughEmail(String email,String password) {
//        try {
//            Optional<Customer>customerOptional= Optional.ofNullable(customerRepository.findAllByEmail(email));
//            if (customerOptional.isPresent()){
//
//                customerRepository.updatePassword(email,password);
//            }
//        }
//        catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//    }

    public String ResetPassword(String NewPass, String Email) {
        try {
            Optional<Customer> customerOptional;
            customerOptional= Optional.ofNullable(this.customerRepository.findAllByEmail(Email));
            customerOptional.get().setPassword(NewPass);
            this.customerRepository.save(customerOptional.get());
            return "Success";
        } catch (Exception e) {
            return "Failed";
        }
    }


}
