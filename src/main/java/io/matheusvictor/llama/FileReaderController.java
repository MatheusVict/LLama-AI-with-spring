package io.matheusvictor.llama;

import lombok.extern.log4j.Log4j2;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

@RestController
@Log4j2
public class FileReaderController {

    private final ChatClient chatClient;
    private final FileProcessingService fileProcessingService;

    public FileReaderController(ChatClient.Builder chatClient, FileProcessingService fileProcessingService) {
        this.chatClient = chatClient
                .defaultSystem("Pretend you are a software engineer professor")
                .build();
        this.fileProcessingService = fileProcessingService;
    }


    @PostMapping("/upload")
    public CompletableFuture<String> uploadAsync(@RequestParam("file") MultipartFile file) {
        return CompletableFuture.supplyAsync(() -> {
            String fileText = fileProcessingService.processFile(file);
            return chatClient.prompt()
                    .user("Tell me what is in this text" + fileText)
                    .call()
                    .content();
        });
    }
}
