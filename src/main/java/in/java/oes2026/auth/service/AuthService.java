package in.java.oes2026.auth.service;

import in.java.oes2026.auth.dto.LoginRequest;
import in.java.oes2026.auth.dto.LoginResponse;
import in.java.oes2026.auth.dto.RegisterRequest;
import in.java.oes2026.user.entity.User;
import in.java.oes2026.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public String register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            return "Email already exists!";
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(user);

        return "User Registered Successfully!";
    }

    public LoginResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow();

        String redirectTo = switch (user.getRole()) {
            case STUDENT -> "/student/dashboard";
            case EXAMINER -> "/teacher/dashboard";
            case ADMIN -> "/admin/dashboard";
        };

        return new LoginResponse(
                "Login Successful!",
                user.getRole().name(),
                redirectTo
        );
    }
}