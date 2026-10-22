package com.acme.contracts.web;

import com.acme.contracts.model.FinalizeRequest;
import com.acme.contracts.service.FinalizeService;
import com.acme.contracts.service.LegalHoldService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contracts/{id}")
public class FinalizeController {

    public record ReleaseRequest(@NotBlank String note) {
    }

    private final FinalizeService finalizer;
    private final LegalHoldService holds;

    public FinalizeController(FinalizeService finalizer, LegalHoldService holds) {
        this.finalizer = finalizer;
        this.holds = holds;
    }

    @PostMapping("/finalize")
    public Map<String, Integer> finalizeContract(@PathVariable long id, @Valid @RequestBody FinalizeRequest request)
            throws IOException, InterruptedException {
        return Map.of("exitCode", finalizer.finalizeContract(id, request.watermark()));
    }

    @PostMapping("/legal-hold-release")
    public Map<String, Integer> releaseHold(@PathVariable long id, @Valid @RequestBody ReleaseRequest request)
            throws IOException, InterruptedException {
        return Map.of("exitCode", holds.release(id, request.note()));
    }
}
