package in.java.oes2026.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeacherController {

    @GetMapping("/api/examiner/dashboard")
    public String teacherDashboard() {
        return "Welcome Teacher Dashboard";
    }
}