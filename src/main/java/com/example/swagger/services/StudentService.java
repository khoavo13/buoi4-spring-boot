package com.example.swagger.services;

import com.example.swagger.dtos.StudentDTO;
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
    public Student addStudent(StudentDTO studentDTO) {
        Student student1 = Student.builder()
                .ten(studentDTO.getTen())
                .thanhPho(studentDTO.getThanhPho())
                .ngaySinh(studentDTO.getNgaySinh())
                .xepLoai(studentDTO.getXepLoai())
                .build();
        return studentRepository.save(student1);
    }

    @Override
    public Student updateStudent(Long id, StudentDTO studentDTO) {
        Student student1 = getStudentById(id);
        student1.setTen(studentDTO.getTen());
        student1.setThanhPho(studentDTO.getThanhPho());
        student1.setNgaySinh(studentDTO.getNgaySinh());
        student1.setXepLoai(studentDTO.getXepLoai());
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
    public Page<StudentResponse> getAllStudents(PageRequest pageRequest) {
        return studentRepository.findAll(pageRequest).map(StudentResponse::fromStudent);
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
