package com.example.swagger.services;

import com.example.swagger.models.Student;
import com.example.swagger.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class StudentService implements IStudentService{
    private final StudentRepository studentRepository;
    @Override
    public Student addStudent(Student student) {
        Student student1 = Student.builder()
                .ten(student.getTen())
                .thanhPho(student.getThanhPho())
                .ngaySinh(student.getNgaySinh())
                .xepLoai(student.getXepLoai())
                .build();
        return studentRepository.save(student1);
    }

    @Override
    public Student updateStudent(Long id, Student student) {
        Student student1 = getStudentById(id);
        student1.setTen(student.getTen());
        student1.setThanhPho(student.getThanhPho());
        student1.setNgaySinh(student.getNgaySinh());
        student1.setXepLoai(student.getXepLoai());
        return studentRepository.save(student1);
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}
