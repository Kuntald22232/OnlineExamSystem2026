package in.java.oes2026.exam.submission.repository;

import in.java.oes2026.exam.entity.ExamEntity;
import in.java.oes2026.exam.submission.entity.SubmissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepository
        extends JpaRepository<SubmissionEntity, Long> {

    List<SubmissionEntity> findByCheckedByTeacherFalse();

    List<SubmissionEntity> findByExam(ExamEntity exam);
}