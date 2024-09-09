package com.example.swagger.services;

import com.example.swagger.models.Student;

import java.util.List;

public interface IStudentService {
    Student addStudent(Student student);
    Student updateStudent(Long id, Student student);
    Student getStudentById(Long id);
    List<Student> getAllStudents();
    void deleteStudent(Long id);
}
