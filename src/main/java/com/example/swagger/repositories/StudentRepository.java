package com.example.swagger.repositories;

import com.example.swagger.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{
    List<Student> findByTenContainingIgnoreCase(String ten);

    @Query("SELECT s FROM Student s WHERE s.thanhPho LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Student> findByThanhPho(String name);

    @Query("SELECT s FROM Student s WHERE s.thanhPho LIKE LOWER(CONCAT('%', :name, '%')) OR s.ten LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Student> findByThanhPhoAndTen(String name);
}
