package in.java.oes2026.exam.result.controller;

//package com.exam.result.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import in.java.oes2026.exam.result.entity.StudentResult;
import in.java.oes2026.exam.result.service.StudentResultService;

//import com.exam.result.entity .StudentResult;

//import com.exam.result.service.StudentResultService;

@RestController
@RequestMapping("/api/results")
@CrossOrigin(
origins = "*"
)
public class ResultController {

    private final
    StudentResultService service;

    public ResultController(
            StudentResultService service) {

        this.service =
                service;
    }

    @GetMapping("/all")
    public List<StudentResult> getAllResults() {
        return service.getAllResults();
    }
    @PostMapping
    public StudentResult
    addResult(
      @RequestBody
      StudentResult result) {

        return service
                .saveResult(result);
    }

    @GetMapping("/{registrationNo}")
    public List<StudentResult> getResult(
            @PathVariable("registrationNo")
            String registrationNo) {

        return service.getResult(registrationNo);
    }
}