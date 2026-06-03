package in.java.oes2026.exam.subject.entity;

import java.util.List;

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

    @OneToMany(mappedBy = "subject")
    @JsonIgnoreProperties("subject")
    private List<ExamEntity> exams;
}