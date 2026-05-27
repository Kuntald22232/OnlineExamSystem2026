package in.java.oes2026.auth.controller;

import in.java.oes2026.auth.dto.LoginRequest;
import in.java.oes2026.auth.dto.LoginResponse;
import in.java.oes2026.auth.dto.RegisterRequest;
import in.java.oes2026.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/test")
    public String test() {
        return "Backend Working";
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody RegisterRequest request
    ) {

        try {

            System.out.println("========== REGISTER ==========");
            System.out.println("FULL NAME: " + request.getFullName());
            System.out.println("EMAIL: " + request.getEmail());
            System.out.println("ROLE: " + request.getRole());

            String msg =
                    authService.register(request);

            return ResponseEntity.ok(
                    Map.of(
                            "message",
                            msg
                    )
            );

        } catch (Exception e) {

            System.out.println(
                    "❌ REGISTER ERROR"
            );

            e.printStackTrace();

            return ResponseEntity
                    .badRequest()
                    .body(
                            Map.of(
                                    "error",
                                    e.getMessage()
                            )
                    );
        }
    }

    @PostMapping("/login")
    public LoginResponse login(
            @Valid @RequestBody LoginRequest request
    ) {
        return authService.login(request);
    }
}