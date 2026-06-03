package in.java.oes2026.exam.submission.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import in.java.oes2026.exam.entity.ExamEntity;
import in.java.oes2026.exam.user.entity.UserEntity;
import in.java.oes2026.exam.subject.entity.SubjectEntity;
import in.java.oes2026.exam.submission.answer.entity.AnswerEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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
 
    @Column(columnDefinition = "LONGTEXT")
    private String answersJson;
    
    @ManyToOne
    private ExamEntity exam;

    @ManyToOne
    private SubjectEntity subject;

    @OneToMany(mappedBy = "submission")
    private List<AnswerEntity> answers;

    private LocalDateTime submittedAt;

    private Boolean checkedByTeacher;
}