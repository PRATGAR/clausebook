package com.acme.contracts.web;

import com.acme.contracts.model.DirectoryEntry;
import com.acme.contracts.model.NewSigner;
import com.acme.contracts.model.Signer;
import com.acme.contracts.service.SignerService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/signers")
public class SignerController {

    private final SignerService signers;

    public SignerController(SignerService signers) {
        this.signers = signers;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Signer create(@Valid @RequestBody NewSigner request) {
        return signers.create(request);
    }

    @PostMapping(value = "/import", consumes = MediaType.APPLICATION_XML_VALUE)
    public Map<String, Integer> importRoster(@RequestBody String rosterXml) throws Exception {
        return Map.of("imported", signers.importRoster(rosterXml));
    }

    @GetMapping("/{id}/directory")
    public List<DirectoryEntry> directory(@PathVariable long id) throws Exception {
        return signers.directoryEntry(id);
    }
}
