package com.acme.contracts.web;

import com.acme.contracts.service.PdfService;
import java.io.IOException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contracts/{id}")
public class PdfController {

    private final PdfService pdfs;

    public PdfController(PdfService pdfs) {
        this.pdfs = pdfs;
    }

    @PostMapping("/render-pdf")
    public ResponseEntity<String> render(@PathVariable long id) throws IOException {
        return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(pdfs.render(id));
    }
}
