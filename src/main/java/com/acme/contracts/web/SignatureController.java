package com.acme.contracts.web;

import com.acme.contracts.model.SignatureRequestBody;
import com.acme.contracts.service.SignatureService;
import jakarta.validation.Valid;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contracts/{id}")
public class SignatureController {

    private final SignatureService signatures;

    public SignatureController(SignatureService signatures) {
        this.signatures = signatures;
    }

    @PostMapping("/send-for-signature")
    public Map<String, Object> send(@PathVariable long id, @Valid @RequestBody SignatureRequestBody request)
            throws NoSuchAlgorithmException, IOException, InterruptedException {
        return signatures.sendForSignature(id, request.signerId());
    }
}
