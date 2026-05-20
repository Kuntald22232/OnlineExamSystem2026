package in.java.oes2026.exam.service;

import org.springframework.stereotype.Service;

import in.java.oes2026.exam.entity.ExamEntity;
import in.java.oes2026.exam.repository.ExamRepository;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;


@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;

    // CREATE
    public ExamEntity createExam(ExamEntity exam) {
        exam.setActive(true);
        return examRepository.save(exam);
    }

    // ALL
    public List<ExamEntity> getAllExams() {
        return examRepository.findAll();
    }

    // ACTIVE
    public List<ExamEntity> getActiveExams() {
        return examRepository.findAll()
                .stream()
                .filter(e -> Boolean.TRUE.equals(e.getActive()))
                .toList();
    }

    // UPCOMING (future exams)
    public List<ExamEntity> getUpcomingExams() {

        LocalDateTime now = LocalDateTime.now();

        return examRepository.findAll()
                .stream()
                .filter(e ->
                        Boolean.TRUE.equals(e.getActive()) &&
                        e.getExamDate() != null &&
                        e.getExamDate().isAfter(now)
                )
                .toList();
    }

    // AVAILABLE (current + past started exams)
 // AVAILABLE
    /*public List<ExamEntity> getAvailableExams() {

        LocalDateTime now = LocalDateTime.now();

        return examRepository.findAll()
                .stream()
                .filter(e ->
                        Boolean.TRUE.equals(e.getActive()) &&
                        e.getExamDate() != null &&
                        (
                            e.getExamDate().isBefore(now) ||
                            e.getExamDate().isEqual(now)
                        )
                )
                .toList();
    }*/
    public List<ExamEntity> getAvailableExams() {
        return examRepository.findAll();
    }
    // SINGLE
    public ExamEntity getById(Long id) {
        return examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found"));
    }
}