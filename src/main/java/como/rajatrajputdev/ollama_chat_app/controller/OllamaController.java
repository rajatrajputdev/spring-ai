package como.rajatrajputdev.ollama_chat_app.controller;

import como.rajatrajputdev.ollama_chat_app.tools.CalculatorTool;
import como.rajatrajputdev.ollama_chat_app.tools.DateTimeCurrent;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class OllamaController {
    private final CalculatorTool calculatorTool;
    private final ChatClient chatClient;
    private final DateTimeCurrent dateTimeCurrent;
    public OllamaController(
            CalculatorTool calculatorTool, ChatClient.Builder builder,
            ChatMemory chatMemory, DateTimeCurrent dateTimeCurrent) {
        this.calculatorTool = calculatorTool;
        this.dateTimeCurrent = dateTimeCurrent;

        this.chatClient = builder
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(),
                        MessageChatMemoryAdvisor.builder(chatMemory).build()
                )
                .build();
    }

    @PostMapping("/chat")
    public ResponseEntity<?> getAnswer(
            @RequestBody Map<String, String> request) {

        String message = request.get("prompt");
        String conversationId = request.get("conversationId");

        if (message == null || message.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Prompt cannot be empty"));
        }

        if (conversationId == null || conversationId.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Conversation ID cannot be empty"));
        }

        String response = chatClient.prompt()
                .user(message)
                .advisors(a -> a.param(
                        ChatMemory.CONVERSATION_ID,
                        conversationId
                ))
                .tools(calculatorTool, dateTimeCurrent)
                .call()
                .content();

        return ResponseEntity.ok(
                Map.of("content", response)
        );
    }

}