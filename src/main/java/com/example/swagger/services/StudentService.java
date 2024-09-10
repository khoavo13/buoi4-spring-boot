package com.example.swagger.services;

import com.example.swagger.models.Student;
import com.example.swagger.repositories.StudentRepository;
import com.example.swagger.responses.StudentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Page<Student> getStudents(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    @Override
    public List<Student> findByTen(String ten) {
        return studentRepository.findByTenContainingIgnoreCase(ten);
    }

    @Override
    public List<Student> findByThanhPho(String name) {
        return studentRepository.findByThanhPho(name);
    }

    @Override
    public List<Student> findByThanhPhoAndTen(String name) {
        return studentRepository.findByThanhPhoAndTen(name);
    }
}
