package in.java.oes2026.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AIService {

    private final ChatClient chatClient;

    public AIService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public String askAI(String message) {

        try {
            return chatClient
                    .prompt(message)
                    .call()
                    .content();
        }

        catch (Exception e) {

            return "AI service is temporarily unavailable. Please try again later.";
        }
    }
}