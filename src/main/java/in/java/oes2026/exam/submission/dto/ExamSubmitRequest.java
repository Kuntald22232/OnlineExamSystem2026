package in.java.oes2026.exam.submission.dto;

import lombok.Data;

import java.util.List;

@Data
public class ExamSubmitRequest {

    private Long studentId;
    private Long examId;
    private List<AnswerRequest> answers;
}