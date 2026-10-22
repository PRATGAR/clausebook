package com.acme.contracts.service;

import com.acme.contracts.model.Contract;
import com.acme.contracts.model.Template;
import com.acme.contracts.repo.TemplateRepository;
import com.acme.contracts.support.TemplateRenderer;
import java.io.IOException;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class PdfService {

    private final ContractService contracts;
    private final TemplateRepository templates;
    private final TemplateRenderer templateRenderer;

    public PdfService(ContractService contracts, TemplateRepository templates, TemplateRenderer templateRenderer) {
        this.contracts = contracts;
        this.templates = templates;
        this.templateRenderer = templateRenderer;
    }

    public String render(long contractId) throws IOException {
        Contract contract = contracts.require(contractId);
        String body = contract.templateId() == null
                ? ""
                : templates.find(contract.templateId()).map(Template::body).orElse("");
        Map<String, Object> variables = Map.of(
                "title", contract.title(), "counterparty", contract.counterparty(), "body", body);
        return templateRenderer.renderContractPage(variables);
    }
}
