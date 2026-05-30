package in.java.oes2026.exam.service;

import org.springframework.stereotype.Service;

import in.java.oes2026.exam.entity.ExamEntity;
import in.java.oes2026.exam.repository.ExamRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;

    // CREATE
    public ExamEntity createExam(ExamEntity exam) {
        exam.setActive(true);
        return examRepository.save(exam);
    }

    // UPDATE 🔥 ADD THIS
    public ExamEntity updateExam(Long examId, ExamEntity updatedExam) {

        ExamEntity existing = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found with id: " + examId));

        existing.setExamTitle(updatedExam.getExamTitle());
        existing.setDurationInMinutes(updatedExam.getDurationInMinutes());
        existing.setExamDate(updatedExam.getExamDate());
        existing.setActive(updatedExam.getActive());

        return examRepository.save(existing);
    }

    // DELETE 🔥 ADD THIS
    public void deleteExam(Long examId) {

        ExamEntity existing = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found with id: " + examId));

        examRepository.delete(existing);
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

    // UPCOMING
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

    // AVAILABLE
    public List<ExamEntity> getAvailableExams() {
        return examRepository.findAll();
    }

    // SINGLE
    public ExamEntity getById(Long id) {
        return examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found"));
    }
}