package com.acme.contracts.web;

import com.acme.contracts.model.ApprovalStatus;
import com.acme.contracts.service.ApprovalService;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/approvals")
public class ApprovalController {

    private final ApprovalService approvals;

    public ApprovalController(ApprovalService approvals) {
        this.approvals = approvals;
    }

    @PostMapping("/{contractId}/fast-track")
    public Map<String, ApprovalStatus> fastTrack(@PathVariable long contractId) {
        return Map.of("status", approvals.fastTrack(contractId));
    }
}
