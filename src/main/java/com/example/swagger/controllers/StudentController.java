package com.example.swagger.controllers;

import com.example.swagger.dtos.StudentDTO;
import com.example.swagger.exceptions.ResourceNotFoundException;
import com.example.swagger.models.Student;
import com.example.swagger.responses.ApiResponse;
import com.example.swagger.responses.StudentListResponse;
import com.example.swagger.responses.StudentResponse;
import com.example.swagger.services.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController {

    private final StudentService studentService;

    @PostMapping("")
    public ResponseEntity<ApiResponse>  addStudent(@Valid @RequestBody StudentDTO studentDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            ApiResponse apiResponse = ApiResponse.builder()
                    .data(errors)
                    .message("Validation failed")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();
            return ResponseEntity.badRequest().body(apiResponse);
        }

        Student student1 = studentService.addStudent(studentDTO);
        ApiResponse apiResponse = ApiResponse.builder()
                .data(student1)
                .message("Inserted successfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(apiResponse);

    }

    @GetMapping("")
    public ResponseEntity<ApiResponse> getAllStudents() {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(studentService.getAllStudents())
                .message("Success")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }
    @GetMapping("/search1")
    public ResponseEntity<ApiResponse> search1(@RequestParam String name){
        ApiResponse apiResponse = ApiResponse.builder()
                .data(studentService.findByTen(name))
                .message("Search successfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/search2")
    public ResponseEntity<ApiResponse> search2(@RequestParam String name){
        ApiResponse apiResponse = ApiResponse.builder()
                .data(studentService.findByThanhPho(name))
                .message("Search successfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("/search3")
    public ResponseEntity<ApiResponse> search3(@RequestParam String name){
        ApiResponse apiResponse = ApiResponse.builder()
                .data(studentService.findByThanhPhoAndTen(name))
                .message("Search successfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse> getAllStudentsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size){
        PageRequest pageRequest =  PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<StudentResponse> studentPage = studentService.getAllStudents(pageRequest);
        int totalPages = studentPage.getTotalPages();
        List<StudentResponse> studentList = studentPage.getContent();
        StudentListResponse studentListResponse = StudentListResponse.builder()
                .students(studentList)
                .totalPages(totalPages)
                .build();
        ApiResponse apiResponse = ApiResponse.builder()
                .data(studentListResponse)
                .message("Show students successfully")
                .status(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok().body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentDTO studentDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            ApiResponse apiResponse = ApiResponse.builder()
                    .data(errors)
                    .message("Validation failed")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();
            return ResponseEntity.badRequest().body(apiResponse);
        }

        Student student1 = studentService.updateStudent(id, studentDTO);
        if (student1 == null) {
            throw new ResourceNotFoundException("Student khong tim thay voi id: " + id);
        }
        ApiResponse apiResponse = ApiResponse.builder()
                .data(student1)
                .message("Updated successfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteStudent(@PathVariable Long id){
        Student student = studentService.getStudentById(id);
        if (student == null) {
            throw new ResourceNotFoundException("Student khong tim thay voi id: " + id);
        }
        studentService.deleteStudent(id);
        ApiResponse apiResponse = ApiResponse.builder()
                .data(null)
                .message("Deleted successfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }
}
