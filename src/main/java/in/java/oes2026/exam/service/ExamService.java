package in.java.oes2026.exam.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import in.java.oes2026.exam.entity.ExamEntity;
import in.java.oes2026.exam.repository.ExamRepository;
import in.java.oes2026.exam.subject.entity.SubjectEntity;
import in.java.oes2026.exam.subject.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;
    private final SubjectRepository subjectRepository;

    // ================= CREATE =================
    public ExamEntity createExam(ExamEntity exam) {

        if (exam.getSubject() == null || exam.getSubject().getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Subject ID is required"
            );
        }

        Long subjectId = exam.getSubject().getId();

        SubjectEntity subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Subject not found with id: " + subjectId
                ));

        exam.setSubject(subject);
        exam.setActive(true);

        return examRepository.save(exam);
    }

    // ================= UPDATE =================
    public ExamEntity updateExam(Long examId, ExamEntity updatedExam) {

        ExamEntity existing = examRepository.findById(examId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Exam not found with id: " + examId
                ));

        existing.setExamTitle(updatedExam.getExamTitle());
        existing.setDurationInMinutes(updatedExam.getDurationInMinutes());
        existing.setExamDate(updatedExam.getExamDate());
        existing.setActive(updatedExam.getActive());

        if (updatedExam.getSubject() != null && updatedExam.getSubject().getId() != null) {

            Long subjectId = updatedExam.getSubject().getId();

            SubjectEntity subject = subjectRepository.findById(subjectId)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Subject not found with id: " + subjectId
                    ));

            existing.setSubject(subject);
        }

        return examRepository.save(existing);
    }

    // ================= DELETE =================
    public void deleteExam(Long examId) {

        ExamEntity existing = examRepository.findById(examId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Exam not found"
                ));

        examRepository.delete(existing);
    }

    // ================= GET ALL =================
    public List<ExamEntity> getAllExams() {
        return examRepository.findAll();
    }

    // ================= ACTIVE =================
    public List<ExamEntity> getActiveExams() {
        return examRepository.findAll()
                .stream()
                .filter(e -> Boolean.TRUE.equals(e.getActive()))
                .toList();
    }

    // ================= UPCOMING =================
    public List<ExamEntity> getUpcomingExams() {

        LocalDateTime now = LocalDateTime.now();

        return examRepository.findAll()
                .stream()
                .filter(e ->
                        Boolean.TRUE.equals(e.getActive())
                                && e.getExamDate() != null
                                && e.getExamDate().isAfter(now)
                )
                .toList();
    }

    // ================= SINGLE =================
    public ExamEntity getById(Long id) {
        return examRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Exam not found"
                ));
    }
}