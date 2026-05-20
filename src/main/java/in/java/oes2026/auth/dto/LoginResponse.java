package in.java.oes2026.auth.dto;

//import lombok.AllArgsConstructor;
import lombok.*;

@Data
@AllArgsConstructor
public class LoginResponse {

    private String message;
    private String role;
    private String redirectTo;
}