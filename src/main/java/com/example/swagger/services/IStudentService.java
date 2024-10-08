package com.example.swagger.services;

import com.example.swagger.dtos.StudentDTO;
import com.example.swagger.dtos.StudentImageDTO;
import com.example.swagger.models.Student;
import com.example.swagger.models.StudentImage;
import com.example.swagger.models.XepLoai;
import com.example.swagger.responses.StudentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IStudentService {
    Student addStudent(StudentDTO studentDTO);
    Student updateStudent(Long id, StudentDTO studentDTO);
    Student getStudentById(Long id);
    List<Student> getAllStudents();
    void deleteStudent(Long id);
    Page<StudentResponse> getAllStudents(PageRequest pageRequest);
    List<Student> findByTen(String ten);
    List<Student> findByThanhPho(String name);
    List<Student> findByThanhPhoAndTen(String name);
    List<Student> findByYearOfBirthBetween(int startYear, int endYear);
    List<Student> findByXepLoai(XepLoai name);
    Page<?> search(String ten, XepLoai xepLoai, int startYear, int endYear, PageRequest pageRequest);
    StudentImage saveStudentImage(Long studentId, StudentImageDTO studentImageDTO);
    List<StudentImage> getStudentImages(Long studentId);
    void deleteImage(Long id);
    StudentImage getImageById(Long id);
}
