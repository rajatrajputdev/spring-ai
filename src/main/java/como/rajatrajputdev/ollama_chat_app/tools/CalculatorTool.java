package como.rajatrajputdev.ollama_chat_app.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class CalculatorTool {

    @Tool(description = "Add two numbers and return the result")
    public int add(int a, int b) {
        System.out.println("Calculator tool called!");
        return a + b;
    }
}