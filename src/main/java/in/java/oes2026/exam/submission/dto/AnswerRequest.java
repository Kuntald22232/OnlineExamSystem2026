package in.java.oes2026.exam.submission.dto;

import lombok.Data;

@Data
public class AnswerRequest {

    private Long questionId;
    private String selectedAnswer;
}