package com.example.swagger.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/layout")
public class LayoutController {
    @GetMapping("/layout1")
    public String index1() {
        return "Layout Page 1";
    }

    @GetMapping("/layout2")
    public String index2() {
        return "Layout Page 2";
    }
}
