package in.java.oes2026.exam.question.service;

import in.java.oes2026.exam.entity.ExamEntity;
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

    public QuestionEntity addQuestion(QuestionEntity question) {

        if (question.getExam() == null || question.getExam().getId() == null) {
            throw new RuntimeException("Exam ID is required");
        }

        ExamEntity exam = examRepository.findById(question.getExam().getId())
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        question.setExam(exam);

        return questionRepository.save(question);
    }

    public List<QuestionEntity> getQuestionsByExam(Long examId) {
        return questionRepository.findByExam_Id(examId);
    }

    public QuestionEntity updateQuestion(Long id, QuestionEntity updatedQuestion) {

        QuestionEntity existing = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        existing.setQuestionText(updatedQuestion.getQuestionText());
        existing.setOptionA(updatedQuestion.getOptionA());
        existing.setOptionB(updatedQuestion.getOptionB());
        existing.setOptionC(updatedQuestion.getOptionC());
        existing.setOptionD(updatedQuestion.getOptionD());
        existing.setCorrectAnswer(updatedQuestion.getCorrectAnswer());

        if (updatedQuestion.getExam() != null && updatedQuestion.getExam().getId() != null) {
            ExamEntity exam = examRepository.findById(updatedQuestion.getExam().getId())
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