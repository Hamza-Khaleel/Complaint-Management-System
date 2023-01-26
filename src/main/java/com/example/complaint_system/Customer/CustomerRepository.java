package com.example.complaint_system.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    public Customer findAllByEmailAndPassword(String email,String password);

    public Customer findAllByEmail(String email);

//    @Query("UPDATE Customer SET password =?1 WHERE email=?1")
//    void updatePassword(String email,String password);
}
