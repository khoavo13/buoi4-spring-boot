package com.example.swagger.repositories;

import com.example.swagger.models.Student;
import com.example.swagger.models.XepLoai;
import com.example.swagger.responses.StudentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByTenContainingIgnoreCase(String ten);

    List<Student> findByXepLoai(XepLoai xepLoai);

    @Query("SELECT s FROM Student s WHERE s.thanhPho LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Student> findByThanhPho(String name);

    @Query("SELECT s FROM Student s WHERE s.thanhPho LIKE LOWER(CONCAT('%', :name, '%')) OR s.ten LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Student> findByThanhPhoAndTen(String name);

    @Query("SELECT s FROM Student s WHERE year(s.ngaySinh) BETWEEN :startYear AND :endYear")
    List<Student> findByYearOfBirthBetween(int startYear, int endYear);

    @Query("SELECT s FROM Student s WHERE " +
            "(:xepLoai IS NULL OR s.xepLoai = :xepLoai) AND " +
            "(:ten IS NULL OR s.ten LIKE LOWER(CONCAT('%', :ten, '%'))) AND " +
            "(:startYear IS NULL OR year(s.ngaySinh) >= :startYear) AND" +
            "(:endYear IS NULL OR year(s.ngaySinh) <= :endYear)")
    Page<Student> search(@Param("ten") String ten, @Param("xepLoai") XepLoai xepLoai, @Param("startYear") int startYear, @Param("endYear") int endYear, PageRequest pageRequest);
}
