package in.java.oes2026.exam.submission.answer.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import in.java.oes2026.exam.submission.entity.SubmissionEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "answers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long questionId;

    private String selectedAnswer;

    private String writtenAnswer;

    @ManyToOne
    @JoinColumn(name = "submission_id")
    @JsonIgnoreProperties({
            "answers",
            "student",
            "exam",
            "hibernateLazyInitializer",
            "handler"
    })
    private SubmissionEntity submission;
}