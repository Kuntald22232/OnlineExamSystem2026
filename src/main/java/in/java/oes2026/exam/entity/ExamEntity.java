package in.java.oes2026.exam.entity;

import in.java.oes2026.exam.subject.entity.SubjectEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "exams")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String examTitle;

    private Integer durationInMinutes;

    // Exam start date & time
    private LocalDateTime examDate;

    private Boolean active;

    // ✅ Subject Relation
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private SubjectEntity subject;
}