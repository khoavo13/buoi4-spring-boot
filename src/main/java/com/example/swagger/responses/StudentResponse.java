package com.example.swagger.responses;

import com.example.swagger.models.Student;
import com.example.swagger.models.XepLoai;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse extends BaseResponse{
    private Long id;
    private String ten;
    private String thanhPho;
    private LocalDate ngaySinh;
    private XepLoai xepLoai;
    public static StudentResponse fromStudent(Student category){
        StudentResponse studentResponse = StudentResponse.builder()
                .id(category.getId())
                .ten(category.getTen())
                .thanhPho(category.getThanhPho())
                .ngaySinh(category.getNgaySinh())
                .xepLoai(category.getXepLoai())
                .build();
        studentResponse.setCreatedAt(category.getCreatedAt());
        studentResponse.setUpdatedAt(category.getUpdatedAt());

        return studentResponse;

    }
}
