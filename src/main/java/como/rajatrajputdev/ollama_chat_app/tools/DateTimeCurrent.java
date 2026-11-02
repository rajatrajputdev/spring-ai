package como.rajatrajputdev.ollama_chat_app.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class DateTimeCurrent {

    @Tool(description = "Get the current date and time")
    public String getDateTime() {
        System.out.println("Datetime tool called");
        return java.time.LocalDateTime.now().toString();
    }
}