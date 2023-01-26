package com.example.complaint_system.Company;

import com.example.complaint_system.Complaint.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface CompanyRepository extends JpaRepository<Company,Integer> {
    public Company findAllById(int id);


    @Query("select s from Company s where s.name=?1")
    Company findByName(String name);

    @Query(value = "select u from Company u where u.type_company=?1")
    List<Company> findAllByType(String type);
}
