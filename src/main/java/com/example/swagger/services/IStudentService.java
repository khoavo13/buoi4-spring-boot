package com.example.swagger.services;

import com.example.swagger.models.Student;
import com.example.swagger.responses.StudentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IStudentService {
    Student addStudent(Student student);
    Student updateStudent(Long id, Student student);
    Student getStudentById(Long id);
    List<Student> getAllStudents();
    void deleteStudent(Long id);
    public Page<Student> getStudents(Pageable pageable);
    List<Student> findByTen(String ten);
    List<Student> findByThanhPho(String name);
    List<Student> findByThanhPhoAndTen(String name);
}
