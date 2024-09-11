package com.example.swagger.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "students")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Student extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Ten khong duoc trong")
    @Size(min = 2, max = 50, message = "Ten phai co tu 2 den 50 ky tu")
    private String ten;

    @NotBlank(message = "Thanh pho khong duoc trong")
    private String thanhPho;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Past(message = "Phai la 1 ngay trong qua khu")
    private LocalDate ngaySinh;

    @NotNull(message = "Xep loai khong duoc de trong")
    @Enumerated(EnumType.STRING)
    private XepLoai xepLoai;
}
