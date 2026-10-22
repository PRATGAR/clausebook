package com.acme.contracts.web;

import com.acme.contracts.model.Contract;
import com.acme.contracts.model.ContractSearchResult;
import com.acme.contracts.model.NewContract;
import com.acme.contracts.service.ContractService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {

    private final ContractService contracts;

    public ContractController(ContractService contracts) {
        this.contracts = contracts;
    }

    @PostMapping
    public Contract create(@Valid @RequestBody NewContract request) {
        return contracts.create(request);
    }

    @GetMapping("/search")
    public ContractSearchResult search(@RequestParam(required = false) String q) {
        return contracts.search(q);
    }
}
