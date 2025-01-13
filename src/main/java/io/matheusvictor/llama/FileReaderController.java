package io.matheusvictor.llama;

import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

@RestController
@Log4j2
public class FileReaderController {

    private final ChatClient chatClient;
    private final FileProcessingService fileProcessingService;

    Logger logger = LoggerFactory.getLogger(FileReaderController.class);


    public FileReaderController(ChatClient.Builder chatClient, FileProcessingService fileProcessingService) {
        this.chatClient = chatClient
                .defaultSystem("Pretend you are a software engineer professor")
                .build();
        this.fileProcessingService = fileProcessingService;
    }


    @PostMapping("/upload")
    public Mono<String> uploadAsync(@RequestParam("file") MultipartFile file) {
        return Mono.fromCallable(() -> {
            String fileText = fileProcessingService.processFile(file);
            StringBuilder fileToRead = new StringBuilder("tell me what is missing in this text");
            fileToRead.append("\n");
            fileToRead.append(fileText);
            String response =  chatClient.prompt()
                    .user(fileToRead.toString())
                    .call()
                    .content();
            logger.info(response);
            return response;
        }).timeout(Duration.ofSeconds(200)).doOnError(error -> logger.info("Error processing file: ", error));
    }
}
