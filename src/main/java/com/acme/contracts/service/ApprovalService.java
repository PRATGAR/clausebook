package com.acme.contracts.service;

import com.acme.contracts.model.ApprovalStatus;
import com.acme.contracts.repo.ApprovalRepository;
import org.springframework.stereotype.Service;

@Service
public class ApprovalService {

    private final ContractService contracts;
    private final ApprovalRepository approvals;

    public ApprovalService(ContractService contracts, ApprovalRepository approvals) {
        this.contracts = contracts;
        this.approvals = approvals;
    }

    public ApprovalStatus fastTrack(long contractId) {
        contracts.require(contractId);
        approvals.setStatus(contractId, ApprovalStatus.APPROVED);
        return ApprovalStatus.APPROVED;
    }
}
