package in.java.oes2026.exam.result.repository;


import java.util.List;

import org.springframework.data.jpa.repository
        .JpaRepository;

import in.java.oes2026.exam.result.entity.StudentResult;

public interface
StudentResultRepository extends JpaRepository <StudentResult, Long>
{

    List<StudentResult>
    findByRegistrationNo(
            String registrationNo);
}