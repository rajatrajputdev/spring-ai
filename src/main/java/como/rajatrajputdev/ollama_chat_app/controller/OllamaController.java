package como.rajatrajputdev.ollama_chat_app.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class OllamaController {

    private final ChatClient chatClient;

    public OllamaController(ChatClient.Builder builder) {
        this.chatClient = builder
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    @PostMapping("/chat")
    public ResponseEntity<?> getAnswer(
            @RequestBody Map<String, String> request) {

        String message = request.get("prompt");

        if (message == null || message.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Prompt cannot be empty"));
        }

        String response = chatClient.prompt()
                .user(message)
                .call()
                .content();

        return ResponseEntity.ok(
                Map.of("content", response)
        );
    }
}