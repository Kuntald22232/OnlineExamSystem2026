package in.java.oes2026.exam.submission.answer.repository;

import in.java.oes2026.exam.submission.answer.entity.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {
}