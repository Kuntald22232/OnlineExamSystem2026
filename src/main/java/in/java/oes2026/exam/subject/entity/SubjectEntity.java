package in.java.oes2026.exam.subject.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import in.java.oes2026.exam.entity.ExamEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "subjects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subjectName;

    @ManyToOne
    @JoinColumn(name = "exam_id")
    @JsonIgnoreProperties({
            "questions",
            "submissions",
            "hibernateLazyInitializer",
            "handler"
    })
    private ExamEntity exam;
}