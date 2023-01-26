package com.example.complaint_system.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    public Employee findAllByEmailAndPassword(String email, String password);

    public Employee findAllByEmail(String email);

//    @Query(value = "SELECT u from Employee where email=?")
//    void setPassword(String newPassword);
}
