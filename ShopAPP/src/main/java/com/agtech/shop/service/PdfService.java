package com.agtech.shop.service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PdfService {

    private final TemplateEngine templateEngine;
    private final ResourceLoader resourceLoader;

    public byte[] generatePdf(Map<String, Object> data) {

        // 1. Crée le contexte Thymeleaf avec les données dynamiques
        Context context = new Context();
        context.setVariables(data);

        // 2. Génère le HTML à partir du template
        String html = templateEngine.process("pdf/invoice", context);

        // 3. Génère le PDF en mémoire
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();

            // Fournit le HTML directement, sans baseDocumentUri
            builder.withHtmlContent(html, null);

            // PDF vers le flux mémoire
            builder.toStream(out);

            // Génération du PDF
            builder.run();

            // Retourne le PDF sous forme de byte[]
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("PDF generation failed", e);
        }
    }

}
