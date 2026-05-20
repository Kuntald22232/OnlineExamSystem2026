package in.java.oes2026.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    @GetMapping("/api/student/dashboard")
    public String studentDashboard() {
        return "Welcome Student Dashboard";
    }
}