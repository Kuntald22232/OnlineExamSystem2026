package in.java.oes2026.exam.submission.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.java.oes2026.exam.entity.ExamEntity;
import in.java.oes2026.exam.repository.ExamRepository;
import in.java.oes2026.exam.question.entity.QuestionEntity;
import in.java.oes2026.exam.question.repository.QuestionRepository;
import in.java.oes2026.exam.submission.answer.entity.AnswerEntity;
import in.java.oes2026.exam.submission.answer.repository.AnswerRepository;
import in.java.oes2026.exam.submission.dto.AnswerRequest;
import in.java.oes2026.exam.submission.dto.ExamSubmitRequest;
import in.java.oes2026.exam.submission.entity.SubmissionEntity;
import in.java.oes2026.exam.submission.repository.SubmissionRepository;
import in.java.oes2026.exam.user.entity.UserEntity;
import in.java.oes2026.exam.user.repository.ExamUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private final AnswerRepository answerRepository;
    
    @GetMapping("/exam/{examId}")
    public List<QuestionEntity> getQuestionsByExam(
            @PathVariable Long examId
    ) {
        return questionRepository.findByExam_Id(examId);
    }

    @PostMapping("/submit")
    public String submitExam(@RequestBody ExamSubmitRequest request) throws Exception {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        UserEntity student = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        ExamEntity exam = examRepository.findById(request.getExamId())
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        // ✅ CREATE SUBMISSION FIRST
        SubmissionEntity submission = new SubmissionEntity();

        submission.setStudent(student);
        submission.setExam(exam);

        // ✅ Subject save
        submission.setSubject(exam.getSubject());

        submission.setSubmittedAt(LocalDateTime.now());
        submission.setCheckedByTeacher(false);

        submissionRepository.save(submission);

        // ✅ SAVE ANSWERS
        for (AnswerRequest a : request.getAnswers()) {
            AnswerEntity ans = new AnswerEntity();
            ans.setSubmission(submission);
            ans.setQuestionId(a.getQuestionId());
            ans.setSelectedAnswer(a.getSelectedAnswer());

            answerRepository.save(ans);
        }

        return "Exam submitted successfully";
    }
}