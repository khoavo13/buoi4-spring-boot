package com.example.swagger.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentListResponse {
    private List<StudentResponse> students;
    private int totalPages;
}
