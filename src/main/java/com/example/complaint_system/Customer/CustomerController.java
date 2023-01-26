package com.example.complaint_system.Customer;

import com.example.complaint_system.Company.Company;
import com.example.complaint_system.Complaint.Complaint;
import com.example.complaint_system.employee.Employee;
import com.example.complaint_system.employee.EmployeeRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path = "api/compailntsystem/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;


    @Autowired
    public CustomerController(CustomerService customerService,
                              CustomerRepository customerRepository,
                              EmployeeRepository employeeRepository) {
        this.customerService = customerService;
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
    }
    // work .................................................done
    // make the customer see all companies
    @GetMapping(path = "/getallcompanies")
    public List<Company> ViewAllCompanies(){
        return customerService.getAllCompanies();
    }

    // work.................................................done
    //make the customer see the rate of the company
    @GetMapping("/getRate")
    public Integer getRatting(@RequestParam String idCo){
        return customerService.getRate(idCo);
    }
    // work.............................................done.
    // make the customer make a complaint
    @PostMapping("/makeComplaint")
    public void makeComplaint(@RequestBody Complaint complaint){
      customerService.makeCompliantToCompany(complaint);
    }

    // add image
    @PostMapping("/uploadFile")
    public ResponseEntity<FileUploadResponse> uploadFile(
            @RequestParam("file") MultipartFile multipartFile)
            throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        long size = multipartFile.getSize();
        String filecode = FileUploadUtil.saveFile(fileName, multipartFile);
        FileUploadResponse response = new FileUploadResponse();
        response.setFileName(fileName);
        response.setSize(size);
        response.setDownloadUri("/Pictures" + filecode);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @PostMapping(path = "/registerCustomer")
//    public String SignUp(@RequestBody Customer customer )
//    {
//        return customerService.SignUpCus(customer);
//    }

//    @PostMapping("/signIn")
//    public String signIn(@RequestBody LogIn body){
//        return customerService.signIn(body);
//    }

    @PostMapping(path = "/registerCustomer")
    public String SignUp(@RequestBody Customer customer )
    {
        return customerService.SignUpCus(customer);
    }

    @PostMapping("/signIn")
    public Customer signIn(@RequestBody LogIn body){
       Customer result = customerService.signIn(body);
       return result;
//        if (result.equals("Success")) {
//            Customer customer = this.customerRepository.findAllByEmailAndPassword(body.getEmail(), body.getPassword());
//            String token = createJWT(customer.getSocial_number().toString(), customer.getEmail(), "customer");
//            return ResponseEntity.ok().header("Authorization", "Bearer " + token).build();
//        } else {
//            return ResponseEntity.badRequest().body(result);
//        }
    }


    //
    private String createJWT(String id, String email, String subject) {
        // Set expiration time for JWT
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        long expMillis = nowMillis + TimeUnit.MINUTES.toMillis(30);
        Date exp = new Date(expMillis);

        // Create JWT and return as string
        return Jwts.builder()
                .setId(id)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(exp)
                .claim("email", email)
                .signWith(SignatureAlgorithm.HS256, "secret")
                .compact();
    }
    //






    // work ......................................................... done.
    //edit profile
    @PutMapping("/editProfile")
    public void editInfo(@RequestBody Customer customer){
        customerService.editInfo(customer);
    }


    @GetMapping("SearchForCompany")
    public Company searchForCompany(@RequestParam String name){
        return customerService.searchForCompany(name);
    }


    @GetMapping("/getAllComplaint")
    public List<Complaint> getAllCompliant(@RequestParam Integer id){
        return customerService.getAllComplaints(id);
    }


    @PostMapping(path = "/otp")
    public Integer OtpPlayer(@RequestBody StringBody email)
    {
        try {
            return this.customerService.OTP_OperationPlayer(email.bodystring);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return -1;
        }
    }

//    @PutMapping("/changePassword")
//    public void changePass(@RequestParam String email, @RequestParam String Pass){
//        customerService.changePasswordThroughEmail(email,Pass);
//    }

    @PostMapping(path = "/resetPassword")
    public String ResetPasswordPlayer (@RequestBody LogIn NewPassBody)
    {
        return this.customerService.ResetPassword(NewPassBody.getPassword(),NewPassBody.getEmail());
    }

    // provide location

}
