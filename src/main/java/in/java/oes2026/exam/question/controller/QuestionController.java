package in.java.oes2026.exam.question.controller;

import in.java.oes2026.exam.question.dto.QuestionRequest;
import in.java.oes2026.exam.question.entity.QuestionEntity;
import in.java.oes2026.exam.question.repository.QuestionRepository;
import in.java.oes2026.exam.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionRepository questionRepository;
    @PostMapping
    public QuestionEntity addQuestion(@RequestBody QuestionRequest request) {
        return questionService.addQuestion(request);
    }

    @GetMapping("/{examId}")
    public List<QuestionEntity> getQuestionsByExam(@PathVariable Long examId) {

        System.out.println("Fetching questions for examId: " + examId);

        return questionRepository.findByExam_Id(examId);
    }

    @PutMapping("/{id}")
    public QuestionEntity updateQuestion(
            @PathVariable("id") Long id,
            @RequestBody QuestionRequest request
    ) {
        return questionService.updateQuestion(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable("id") Long id) {
        questionService.deleteQuestion(id);
        return "Deleted successfully: " + id;
    }
}