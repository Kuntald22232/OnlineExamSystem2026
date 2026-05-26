package in.java.oes2026.exam.user.repository;

import in.java.oes2026.common.enums.*;
import in.java.oes2026.exam.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamUserRepository
        extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    Boolean existsByEmail(String email);

    List<UserEntity> findByRole(Role role);
}