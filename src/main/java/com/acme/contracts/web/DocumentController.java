package com.acme.contracts.web;

import com.acme.contracts.model.ExhibitKey;
import com.acme.contracts.model.ExportRequest;
import com.acme.contracts.service.AttachmentService;
import com.acme.contracts.service.ExportService;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contracts/{id}")
public class DocumentController {

    private final AttachmentService attachments;
    private final ExportService exports;

    public DocumentController(AttachmentService attachments, ExportService exports) {
        this.attachments = attachments;
        this.exports = exports;
    }

    @GetMapping("/exhibits/{key}")
    public ResponseEntity<byte[]> exhibit(@PathVariable long id, @PathVariable ExhibitKey key) throws IOException {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(attachments.exhibit(id, key));
    }

    @PostMapping("/export")
    public Map<String, String> export(@PathVariable long id, @Valid @RequestBody ExportRequest request)
            throws IOException {
        return Map.of("exportedTo", exports.exportSigned(id, request.folder()).toString());
    }
}
