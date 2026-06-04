package in.java.oes2026.exam.submission.controller;

import in.java.oes2026.exam.entity.ExamEntity;
import in.java.oes2026.exam.question.entity.QuestionEntity;
import in.java.oes2026.exam.question.repository.QuestionRepository;
import in.java.oes2026.exam.repository.ExamRepository;
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
    private final AnswerRepository answerRepository;

    // ================= GET QUESTIONS BY EXAM =================
    @GetMapping("/{examId}")
    public List<QuestionEntity> getQuestionsByExam(@PathVariable Long examId) {
        return questionRepository.findByExam_Id(examId);
    }

    // ================= SUBMIT EXAM =================
    @PostMapping("/submit")
    public String submitExam(@RequestBody ExamSubmitRequest request) {

        // 1. Get logged-in student
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        UserEntity student = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // 2. Get exam
        ExamEntity exam = examRepository.findById(request.getExamId())
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        // 🔥 ADD HERE (IMPORTANT FIX)
        if (exam.getSubject() == null) {
            throw new RuntimeException("Exam subject is missing");
        }

        if (exam.getSubject().getId() == null) {
            throw new RuntimeException("Invalid exam-subject mapping");
        }
        // 3. Create submission
        SubmissionEntity submission = new SubmissionEntity();
        submission.setStudent(student);
        submission.setExam(exam);
        submission.setSubject(exam.getSubject());
        submission.setSubmittedAt(LocalDateTime.now());
        submission.setCheckedByTeacher(false);

        submission = submissionRepository.save(submission);

        // 4. Save answers
        for (AnswerRequest a : request.getAnswers()) {

            AnswerEntity ans = new AnswerEntity();
            ans.setSubmission(submission);
            ans.setQuestionId(a.getQuestionId());
            ans.setSelectedAnswer(a.getSelectedAnswer());
            ans.setWrittenAnswer(a.getWrittenAnswer()); // safe if exists

            answerRepository.save(ans);
        }

        return "Exam submitted successfully";
    }
}