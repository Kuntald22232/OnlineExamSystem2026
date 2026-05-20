package in.java.oes2026.exam.repository;

import in.java.oes2026.exam.entity.ExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository
        extends JpaRepository<ExamEntity, Long> {
}