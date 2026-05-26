package in.java.oes2026.auth.controller;

import in.java.oes2026.auth.dto.LoginRequest;
import in.java.oes2026.auth.dto.LoginResponse;
import in.java.oes2026.auth.dto.RegisterRequest;
import in.java.oes2026.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/test")
    public String test() {
        return "<h1>Backend Working</h1>";
    }

    @PostMapping("/register")
    public Map<String, String> register(
            @Valid @RequestBody RegisterRequest request
    ) {

        // 🔥 DEBUG LOG
        System.out.println("========== REGISTER CONTROLLER ==========");
        System.out.println("FULL NAME: " + request.getFullName());
        System.out.println("EMAIL: " + request.getEmail());
        System.out.println("ROLE RECEIVED: " + request.getRole());

        String msg = authService.register(request);

        System.out.println("REGISTER SUCCESS: " + msg);

        return Map.of("message", msg);
    }

    @PostMapping("/login")
    public LoginResponse login(
            @Valid @RequestBody LoginRequest request
    ) {
        return authService.login(request);
    }
}