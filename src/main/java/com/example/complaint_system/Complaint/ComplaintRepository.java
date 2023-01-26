package com.example.complaint_system.Complaint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint,Integer> {
    @Query(value ="SELECT s from Complaint s where s.customer_id=?1")
    List<Complaint> findAllByCustomerId(Integer id);

    @Query("SELECT s from Complaint s where s.type_complaint=?1")
    List<Complaint> findAllByComplaintType(String type);

//    @Query("SELECT type_complaint, COUNT(*)\n" +
//            "FROM Complaint\n" +
//            "GROUP BY type_complaint")
//    Long countByType(String type);
//    @Query("select s from complaint where s.type_company=1?");
//    Complaint findByType(String type);

//    @Query("select s from Complaint where s.type_company=?1")
//    @Query(value = "select s from Complaint s where s.type_company=?1")
//    List<Complaint> findAllByType(String type);

//    List<Complaint> findAllByEmployee(Employee employee);

}
