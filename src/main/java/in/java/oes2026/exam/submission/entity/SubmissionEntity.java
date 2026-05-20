package in.java.oes2026.exam.submission.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import in.java.oes2026.exam.entity.ExamEntity;
import in.java.oes2026.exam.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "submissions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties({
            "password",
            "submissions",
            "hibernateLazyInitializer",
            "handler"
    })
    private UserEntity student;

    @ManyToOne
    @JsonIgnoreProperties({
            "questions",
            "hibernateLazyInitializer",
            "handler"
    })
    private ExamEntity exam;

    @Column(columnDefinition = "LONGTEXT")
    private String answersJson;

    private LocalDateTime submittedAt;

    private Boolean checkedByTeacher;
}