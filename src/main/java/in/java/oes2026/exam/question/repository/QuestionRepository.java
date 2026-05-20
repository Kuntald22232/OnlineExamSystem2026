package in.java.oes2026.exam.question.repository;

import in.java.oes2026.exam.question.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository
        extends JpaRepository<QuestionEntity, Long> {

	List<QuestionEntity> findByExam_Id(Long examId);
}