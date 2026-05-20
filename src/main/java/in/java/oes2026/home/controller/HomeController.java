package in.java.oes2026.home.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "<h1>Online Examination System Running 🚀</h1>";
    }
}
