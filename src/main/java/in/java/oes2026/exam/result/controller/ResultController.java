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

    // CREATE
    @PostMapping
    public StudentResult addResult(@RequestBody StudentResult result) {
        return service.saveResult(result);
    }

    // GET ALL
    @GetMapping("/all")
    public List<StudentResult> getAllResults() {
        return service.getAllResults();
    }

    // GET BY REG NO
    @GetMapping("/registration/{registrationNo}")
    public List<StudentResult> getResult(@PathVariable String registrationNo) {
        return service.getResult(registrationNo);
    }

    // UPDATE (SAFE)
    @PutMapping("/registration/{registrationNo}")
    public StudentResult updateResult(
            @PathVariable String registrationNo,
            @RequestBody StudentResult updatedResult
    ) {
        return service.updateResultByRegistrationNo(registrationNo, updatedResult);
    }

    // DELETE (IMPORTANT FIX)
    @DeleteMapping("/registration/{registrationNo}")
    public String deleteResult(@PathVariable Long id) {
        service.deleteResult(id);
        return "Result deleted successfully";
    }
}