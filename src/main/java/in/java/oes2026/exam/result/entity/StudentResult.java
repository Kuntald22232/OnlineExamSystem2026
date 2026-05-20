package in.java.oes2026.exam.result.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "student_results")
public class StudentResult {

    @Id
    @GeneratedValue(strategy =
            GenerationType.IDENTITY)
    private Long id;

    @Column(name =
            "registration_no")
    private String registrationNo;

    private String subject;
    private int marks;
    private String grade;

    public StudentResult() {}

    public Long getId() {
        return id;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(
            String registrationNo) {

        this.registrationNo =
                registrationNo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(
            String subject) {

        this.subject = subject;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(
            String grade) {

        this.grade = grade;
    }
}