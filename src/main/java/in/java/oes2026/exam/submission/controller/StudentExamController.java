package in.java.oes2026.exam.submission.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.java.oes2026.exam.entity.ExamEntity;
import in.java.oes2026.exam.repository.ExamRepository;
import in.java.oes2026.exam.question.entity.QuestionEntity;
import in.java.oes2026.exam.question.repository.QuestionRepository;
import in.java.oes2026.exam.submission.dto.ExamSubmitRequest;
import in.java.oes2026.exam.submission.entity.SubmissionEntity;
import in.java.oes2026.exam.submission.repository.SubmissionRepository;
import in.java.oes2026.exam.user.entity.UserEntity;
import in.java.oes2026.exam.user.repository.ExamUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/student/exam")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StudentExamController {

    private final QuestionRepository questionRepository;
    private final SubmissionRepository submissionRepository;
    private final ExamUserRepository userRepository;
    private final ExamRepository examRepository;
    private final ObjectMapper objectMapper;

    @GetMapping("/exam/{examId}")
    public List<QuestionEntity> getQuestionsByExam(
            @PathVariable("examId") Long examId
    ) {
        return questionRepository.findByExam_Id(examId);
    }

    @PostMapping("/submit")
    public String submitExam(
            @RequestBody ExamSubmitRequest request
    ) throws Exception {

        UserEntity student = userRepository
                .findById(request.getStudentId())
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        ExamEntity exam = examRepository
                .findById(request.getExamId())
                .orElseThrow(() ->
                        new RuntimeException("Exam not found"));

        String answers = objectMapper
                .writeValueAsString(request.getAnswers());

        SubmissionEntity submission =
                SubmissionEntity.builder()
                        .student(student)
                        .exam(exam)
                        .answersJson(answers)
                        .submittedAt(LocalDateTime.now())
                        .checkedByTeacher(false)
                        .build();

        submissionRepository.save(submission);

        return "Exam submitted successfully";
    }
}