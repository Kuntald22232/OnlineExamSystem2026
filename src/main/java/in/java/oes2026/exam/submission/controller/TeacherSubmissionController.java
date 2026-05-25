package in.java.oes2026.exam.submission.controller;

import in.java.oes2026.exam.entity.ExamEntity;
import in.java.oes2026.exam.repository.ExamRepository;
import in.java.oes2026.exam.submission.entity.SubmissionEntity;
import in.java.oes2026.exam.submission.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/examiner")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TeacherSubmissionController {

    private final SubmissionRepository submissionRepository;
    private final ExamRepository examRepository;

    @GetMapping("/submissions/exam/{examId}")
    public List<SubmissionEntity> getSubmissionsByExam(
            @PathVariable("examId") Long examId
    ) {

        ExamEntity exam = examRepository
                .findById(examId)
                .orElseThrow(() ->
                        new RuntimeException("Exam not found"));

        return submissionRepository.findByExam(exam);
    }

    @GetMapping("/submissions")
    public List<SubmissionEntity> getAllSubmissions() {

        return submissionRepository
                .findByCheckedByTeacherFalse();
    }
}