package io.matheusvictor.llama;

import lombok.extern.log4j.Log4j2;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileProcessingService {

    Logger logger = LoggerFactory.getLogger(FileProcessingService.class);
    public String processFile(MultipartFile file) {
        String text;

        try (final PDDocument document = PDDocument.load(file.getInputStream())) {
            final PDFTextStripper pdfStripper = new PDFTextStripper();
            text = pdfStripper.getText(document);
        } catch (final Exception ex) {
            text = "Error parsing PDF";
        }
        logger.info(text);

        return text;
    }
}
