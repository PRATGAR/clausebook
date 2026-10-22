package com.acme.contracts.web;

import com.acme.contracts.service.VersionService;
import com.acme.contracts.service.VersionService.SavedVersion;
import com.acme.contracts.service.VersionService.VersionBody;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contracts/{id}/versions")
public class VersionController {

    private final VersionService versions;

    public VersionController(VersionService versions) {
        this.versions = versions;
    }

    @PostMapping
    public SavedVersion save(@PathVariable long id, @RequestBody String content) throws IOException {
        return versions.save(id, content);
    }

    @GetMapping("/{number}")
    public ResponseEntity<String> read(@PathVariable long id, @PathVariable int number)
            throws IOException, NoSuchAlgorithmException {
        VersionBody body = versions.read(id, number);
        return ResponseEntity.ok().eTag("\"" + body.etag() + "\"").body(body.content());
    }
}
