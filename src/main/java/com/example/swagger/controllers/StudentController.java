package com.example.swagger.controllers;

import com.example.swagger.dtos.StudentDTO;
import com.example.swagger.dtos.StudentImageDTO;
import com.example.swagger.exceptions.ResourceNotFoundException;
import com.example.swagger.models.Student;
import com.example.swagger.models.StudentImage;
import com.example.swagger.models.XepLoai;
import com.example.swagger.responses.ApiResponse;
import com.example.swagger.responses.StudentListResponse;
import com.example.swagger.responses.StudentResponse;
import com.example.swagger.services.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController {

    private final StudentService studentService;

    @PostMapping("")
    public ResponseEntity<ApiResponse>  addStudent(@Valid @RequestBody StudentDTO studentDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
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
    @GetMapping("/search4")
    public ResponseEntity<ApiResponse> search4(@RequestParam int startYear, @RequestParam int endYear){
        ApiResponse apiResponse = ApiResponse.builder()
                .data(studentService.findByYearOfBirthBetween(startYear, endYear))
                .message("Search successfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("/searchXepLoai")
    public ResponseEntity<ApiResponse> searchXepLoai(@RequestParam XepLoai name){
        ApiResponse apiResponse = ApiResponse.builder()
                .data(studentService.findByXepLoai(name))
                .message("Search successfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> search(@RequestParam String ten,
                                              @RequestParam XepLoai xepLoai,
                                              @RequestParam int startYear,
                                              @RequestParam int endYear,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "5") int size){
//        ApiResponse apiResponse = ApiResponse.builder()
//                .data(studentService.search(ten, xepLoai, startYear, endYear))
//                .message("Search successfully")
//                .status(HttpStatus.OK.value())
//                .build();
//        return ResponseEntity.ok().body(apiResponse);

        PageRequest pageRequest =  PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<StudentResponse> studentPage = studentService.search(ten, xepLoai, startYear, endYear, pageRequest);
        int totalPages = studentPage.getTotalPages();
        List<StudentResponse> studentList = studentPage.getContent();
        StudentListResponse studentListResponse = StudentListResponse.builder()
                .students(studentList)
                .totalPages(totalPages)
                .build();
        ApiResponse apiResponse = ApiResponse.builder()
                .data(studentListResponse)
                .message("Show students search successfully")
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

    @GetMapping("/getAllImage/{id}")
    public ResponseEntity<ApiResponse> getAllImages(@PathVariable Long id){
        ApiResponse apiResponse = ApiResponse.builder()
                .data(studentService.getStudentImages(id))
                .message("Get image of " + id + " successfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @PostMapping("/uploads/{id}")
    public ResponseEntity<ApiResponse>  uploads(@PathVariable Long id, @ModelAttribute("files") List<MultipartFile> files) throws IOException {
        List<StudentImage> studentImages = new ArrayList<>();
        int count = 0;
        for (MultipartFile file : files) {
            if (file != null) {
                if (file.getSize() == 0){
                    count++;
                    continue;
                }
            }
            String fileName = storeFile(file);
            StudentImageDTO studentImageDTO = StudentImageDTO.builder()
                    .imageUrl(fileName)
                    .build();
            StudentImage studentImage = studentService.saveStudentImage(id, studentImageDTO);
            if (studentImage.getStudent() == null) {
                throw new ResourceNotFoundException("Student khong tim thay voi id: " + id);
            }
            studentImages.add(studentImage);
        }


        ApiResponse apiResponse = ApiResponse.builder()
                .data(studentImages)
                .message("Upload images successfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/images/{imageName}")
    public ResponseEntity<?> viewImage(@PathVariable String imageName){
        try {
            Path imagePath = Paths.get("uploads/" + imageName);
            UrlResource resource = new UrlResource(imagePath.toUri());

            if (resource.exists()){
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            }
            else {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(new UrlResource(Paths.get("uploads/notfound.jpeg").toUri()));
            }
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    private String storeFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueFileName = UUID.randomUUID().toString() +  "_" + fileName;
        Path uploadDir = Paths.get("uploads");
        if (!Files.exists(uploadDir)) {
            Files.createDirectory(uploadDir);
        }
        Path destination = Paths.get(uploadDir.toString(), uniqueFileName);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
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
                .data(id)
                .message("Deleted successfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @DeleteMapping("/image/{imageId}")
    public ResponseEntity<ApiResponse> deleleImage(@PathVariable Long imageId){
        StudentImage studentImage = studentService.getImageById(imageId);
        if (studentImage == null) {
            throw new ResourceNotFoundException("Image khong tim thay voi id: " + imageId);
        }
        studentService.deleteImage(imageId);

        ApiResponse apiResponse = ApiResponse.builder()
                .data(imageId)
                .message("Deleted image successfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }
}
