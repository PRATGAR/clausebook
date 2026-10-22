package com.acme.contracts.service;

import com.acme.contracts.model.Contract;
import com.acme.contracts.model.ContractSearchResult;
import com.acme.contracts.model.NewContract;
import com.acme.contracts.repo.ApprovalRepository;
import com.acme.contracts.repo.ContractRepository;
import com.acme.contracts.support.NotFoundException;
import com.acme.contracts.support.WebhookPolicy;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ContractService {

    private final ContractRepository contracts;
    private final ApprovalRepository approvals;
    private final WebhookPolicy webhookPolicy;

    public ContractService(ContractRepository contracts, ApprovalRepository approvals,
                           WebhookPolicy webhookPolicy) {
        this.contracts = contracts;
        this.approvals = approvals;
        this.webhookPolicy = webhookPolicy;
    }

    public Contract create(NewContract request) {
        String webhook = request.webhookUrl();
        if (webhook != null && !webhookPolicy.accepts(webhook)) {
            throw new IllegalArgumentException("webhook host is not permitted");
        }
        long id = contracts.insert(request.title(), request.counterparty(), request.storageLabel(),
                webhook, request.templateId());
        approvals.open(id);
        return require(id);
    }

    public Contract require(long id) {
        return contracts.find(id).orElseThrow(() -> new NotFoundException("contract", id));
    }

    public ContractSearchResult search(String term) {
        List<Contract> byTitle = term == null ? List.of() : contracts.titleContains(term);
        return new ContractSearchResult(byTitle);
    }
}
