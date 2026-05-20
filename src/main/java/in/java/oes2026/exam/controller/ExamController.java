package in.java.oes2026.exam.controller;

import in.java.oes2026.exam.entity.ExamEntity;
import in.java.oes2026.exam.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exams")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ExamController {

    private final ExamService examService;

    // =========================
    // CREATE EXAM (TEACHER)
    // =========================
    @PostMapping
    public ExamEntity createExam(@RequestBody ExamEntity exam) {
        return examService.createExam(exam);
    }

    // =========================
    // GET ALL EXAMS (ADMIN)
    // =========================
    @GetMapping
    public List<ExamEntity> getAllExams() {
        return examService.getAllExams();
    }

    // =========================
    // GET ACTIVE EXAMS
    // =========================
    @GetMapping("/active")
    public List<ExamEntity> getActiveExams() {
        return examService.getActiveExams();
    }

    @GetMapping("/student/available")
    public List<ExamEntity> getAvailableExams() {
        return examService.getAvailableExams();
    }
    // =========================
    // STUDENT UPCOMING EXAMS
    // =========================
    @GetMapping("/student/upcoming-exams")
    public List<ExamEntity> getStudentUpcomingExams() {
        return examService.getUpcomingExams();
    }

    // =========================
    // GET SINGLE EXAM
    // =========================
    @GetMapping("/{id}")
    public ExamEntity getExamById(@PathVariable Long id) {
        return examService.getById(id);
    }
}