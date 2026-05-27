package in.java.oes2026.auth.service;

import in.java.oes2026.auth.dto.LoginRequest;
import in.java.oes2026.auth.dto.LoginResponse;
import in.java.oes2026.auth.dto.RegisterRequest;
import in.java.oes2026.common.enums.Role;
import in.java.oes2026.security.JwtService;
import in.java.oes2026.user.entity.User;
import in.java.oes2026.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    // ================= REGISTER =================
    public String register(RegisterRequest request) {

        try {

            System.out.println("========== REGISTER ==========");
            System.out.println("FULL NAME: " + request.getFullName());
            System.out.println("EMAIL: " + request.getEmail());
            System.out.println("ROLE RECEIVED: " + request.getRole());

            // ✅ EMAIL EXISTS CHECK
            if (userRepository.existsByEmail(request.getEmail())) {
                System.out.println("❌ EMAIL ALREADY EXISTS");
                return "Email already exists!";
            }

            // ✅ ROLE PARSE SAFE WAY
            Role role = Role.STUDENT;

            try {

                if (request.getRole() != null
                        && !request.getRole().isBlank()) {

                    role = Role.valueOf(
                            request.getRole()
                                    .trim()
                                    .toUpperCase()
                    );
                }

            } catch (Exception e) {

                System.out.println(
                        "❌ ROLE PARSE ERROR: "
                                + request.getRole()
                );

                throw new RuntimeException(
                        "Invalid role: "
                                + request.getRole()
                );
            }

            if (role == Role.EXAMINER) {
                System.out.println("🔥 EXAMINER DETECTED");
            }

            System.out.println("✅ FINAL ROLE: " + role);

            User user = User.builder()
                    .fullName(request.getFullName())
                    .email(request.getEmail())
                    .password(
                            passwordEncoder.encode(
                                    request.getPassword()
                            )
                    )
                    .role(role)
                    .build();

            System.out.println(
                    "🔥 USER OBJECT: " + user
            );

            System.out.println(
                    "💾 SAVING USER..."
            );

            userRepository.save(user);

            System.out.println(
                    "✅ SAVED IN DATABASE"
            );

            System.out.println(
                    "✅ USER SAVED SUCCESSFULLY"
            );

            return role
                    + " Registered Successfully!";

        } catch (Exception e) {

            System.out.println(
                    "❌ REGISTER FAILED"
            );

            e.printStackTrace();

            throw new RuntimeException(
                    e.getMessage()
            );
        }
    }

    // ================= LOGIN =================
    public LoginResponse login(
            LoginRequest request
    ) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user =
                userRepository
                        .findByEmail(
                                request.getEmail()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );

        String token =
                jwtService.generateToken(
                        user.getEmail()
                );

        String redirectTo =
                switch (user.getRole()) {
                    case STUDENT ->
                            "/student/dashboard";
                    case EXAMINER ->
                            "/examiner/dashboard";
                    case ADMIN ->
                            "/admin/dashboard";
                };

        return new LoginResponse(
                "Login Successful!",
                token,
                user.getRole().name(),
                redirectTo
        );
    }
}