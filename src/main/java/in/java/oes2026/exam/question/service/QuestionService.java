package in.java.oes2026.exam.question.service;

import in.java.oes2026.exam.entity.ExamEntity;
import in.java.oes2026.exam.question.dto.QuestionRequest;
import in.java.oes2026.exam.question.entity.QuestionEntity;
import in.java.oes2026.exam.question.repository.QuestionRepository;
import in.java.oes2026.exam.repository.ExamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final ExamRepository examRepository;

    public QuestionEntity addQuestion(QuestionRequest request) {

        ExamEntity exam = examRepository.findById(request.getExamId())
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        QuestionEntity question = new QuestionEntity();

        question.setQuestionText(request.getQuestionText());
        question.setOptionA(request.getOptionA());
        question.setOptionB(request.getOptionB());
        question.setOptionC(request.getOptionC());
        question.setOptionD(request.getOptionD());
        question.setCorrectAnswer(request.getCorrectAnswer());
        question.setExam(exam);

        return questionRepository.save(question);
    }

    public List<QuestionEntity> getQuestionsByExam(Long examId) {
        return questionRepository.findByExam_Id(examId);
    }

    public QuestionEntity updateQuestion(Long id, QuestionRequest request) {

        QuestionEntity existing = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        existing.setQuestionText(request.getQuestionText());
        existing.setOptionA(request.getOptionA());
        existing.setOptionB(request.getOptionB());
        existing.setOptionC(request.getOptionC());
        existing.setOptionD(request.getOptionD());
        existing.setCorrectAnswer(request.getCorrectAnswer());

        if (request.getExamId() != null) {
            ExamEntity exam = examRepository.findById(request.getExamId())
                    .orElseThrow(() -> new RuntimeException("Exam not found"));
            existing.setExam(exam);
        }

        return questionRepository.save(existing);
    }
    public void deleteQuestion(Long id) {
        if (!questionRepository.existsById(id)) {
            throw new RuntimeException("Question not found");
        }
        questionRepository.deleteById(id);
    }
}