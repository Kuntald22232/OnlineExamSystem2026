package in.java.oes2026.exam.question.controller;

import in.java.oes2026.exam.question.entity.QuestionEntity;
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

    @PostMapping
    public QuestionEntity addQuestion(@RequestBody QuestionEntity question) {
        return questionService.addQuestion(question);
    }

    @GetMapping("/exam/{examId}")
    public List<QuestionEntity> getQuestionsByExam(
            @PathVariable("examId") Long examId
    ) {
        return questionService.getQuestionsByExam(examId);
    }

    @PutMapping("/{id}")
    public QuestionEntity updateQuestion(
            @PathVariable("id") Long id,
            @RequestBody QuestionEntity updatedQuestion
    ) {
        return questionService.updateQuestion(id, updatedQuestion);
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(
            @PathVariable("id") Long id
    ) {
        questionService.deleteQuestion(id);
        return "Deleted successfully: " + id;
    }
}