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

    // ================= CREATE =================
    @PostMapping
    public ExamEntity createExam(@RequestBody ExamEntity exam) {
        return examService.createExam(exam);
    }

    // ================= UPDATE =================
    @PutMapping("/{examId}")
    public ExamEntity updateExam(
            @PathVariable("examId") Long examId,
            @RequestBody ExamEntity updatedExam
    ) {
        return examService.updateExam(examId, updatedExam);
    }

    // ================= DELETE =================
    @DeleteMapping("/{examId}")
    public String deleteExam(
            @PathVariable("examId") Long examId
    ) {
        examService.deleteExam(examId);
        return "Exam deleted successfully: " + examId;
    }

    // ================= GET ALL =================
    @GetMapping
    public List<ExamEntity> getAllExams() {
        return examService.getAllExams();
    }

    // ================= GET BY ID =================
    @GetMapping("/{examId}")
    public ExamEntity getExamById(
            @PathVariable("examId") Long examId
    ) {
        return examService.getById(examId);
    }

    // ================= ACTIVE =================
    @GetMapping("/active")
    public List<ExamEntity> getActiveExams() {
        return examService.getActiveExams();
    }

    // ================= AVAILABLE =================
    @GetMapping("/student/available")
    public List<ExamEntity> getAvailableExams() {
        return examService.getAvailableExams();
    }

    // ================= UPCOMING =================
    @GetMapping("/student/upcoming-exams")
    public List<ExamEntity> getStudentUpcomingExams() {
        return examService.getUpcomingExams();
    }
}