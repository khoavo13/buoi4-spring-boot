package com.example.swagger.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/admin")
public class AdminController {
    @Operation(summary = "Admin Page 1", description = "list product of admin")
    @GetMapping("/admin1")
    public String index1() {
        return "Admin Page 1";
    }

    @GetMapping("/admin2")
    public String index2() {
        return "Admin Page 2";
    }
}
