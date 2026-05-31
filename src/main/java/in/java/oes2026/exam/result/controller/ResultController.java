package in.java.oes2026.exam.result.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import in.java.oes2026.exam.result.entity.StudentResult;
import in.java.oes2026.exam.result.service.StudentResultService;

@RestController
@RequestMapping("/api/results")
@CrossOrigin(origins = "*")
public class ResultController {

    private final StudentResultService service;

    public ResultController(StudentResultService service) {
        this.service = service;
    }

    // CREATE RESULT
    @PostMapping
    public StudentResult addResult(
            @RequestBody StudentResult result) {

        return service.saveResult(result);
    }

    // GET ALL RESULTS
    @GetMapping("/all")
    public List<StudentResult> getAllResults() {

        return service.getAllResults();
    }

    // GET RESULT BY REGISTRATION NUMBER
    @GetMapping("/registration/{registrationNo}")
    public List<StudentResult> getResult(
            @PathVariable String registrationNo) {

        return service.getResult(registrationNo);
    }

    // UPDATE RESULT BY REGISTRATION NUMBER
    @PutMapping("/registration/{registrationNo}")
    public StudentResult updateResult(
            @PathVariable String registrationNo,
            @RequestBody StudentResult updatedResult) {

        return service.updateResultByRegistrationNo(
                registrationNo,
                updatedResult
        );
    }

    // DELETE RESULT BY ID
    @DeleteMapping("/delete/{id}")
    public String deleteResult(
            @PathVariable Long id) {

        service.deleteResult(id);

        return "Result deleted successfully";
    }
}