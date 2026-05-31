package in.java.oes2026.exam.result.service;


import java.util.List;

import org.springframework.stereotype.Service;

import in.java.oes2026.exam.result.entity.StudentResult;
import in.java.oes2026.exam.result.repository.StudentResultRepository;


@Service
public class StudentResultService {

    private final
    StudentResultRepository repository;

    public StudentResultService(
            StudentResultRepository repository) {

        this.repository =
                repository;
    }

    public StudentResult
    saveResult(
            StudentResult result) {

        return repository.save(result);
    }

    public List<StudentResult>
    getResult(
            String registrationNo) {

        return repository
                .findByRegistrationNo(
                        registrationNo);
    }
    public List<StudentResult> getAllResults() {
        return repository.findAll();
    }
    public StudentResult updateResultByRegistrationNo(String regNo, StudentResult updated) {

        List<StudentResult> list = repository.findByRegistrationNo(regNo);

        if (list.isEmpty()) {
            throw new RuntimeException("Result not found");
        }

        StudentResult existing = list.get(0);

        existing.setSubject(updated.getSubject());
        existing.setMarks(updated.getMarks());
        existing.setGrade(updated.getGrade());

        return repository.save(existing);
    }
    public void deleteResult(Long id) {

        StudentResult result =
                repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Result not found"));

        repository.delete(result);
    }
}