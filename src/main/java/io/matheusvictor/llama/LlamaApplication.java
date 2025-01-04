package io.matheusvictor.llama;

import io.matheusvictor.llama.functions.WeatherConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(WeatherConfigProperties.class)
@SpringBootApplication
public class LlamaApplication {

    public static void main(String[] args) {
        SpringApplication.run(LlamaApplication.class, args);
    }

}
