package como.rajatrajputdev.ollama_chat_app.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ollama")
@CrossOrigin("*")
public class OllamaController {

    private final ChatClient chatClient;

    public OllamaController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/{message}")
    public ResponseEntity<?> getAnswer(@PathVariable String message){
        String response = chatClient.prompt(message).call().content();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
