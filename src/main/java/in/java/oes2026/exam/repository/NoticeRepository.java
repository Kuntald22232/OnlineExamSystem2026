package in.java.oes2026.exam.repository;

import in.java.oes2026.exam.entity.notice.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository
        extends JpaRepository<Notice, Long> {
}