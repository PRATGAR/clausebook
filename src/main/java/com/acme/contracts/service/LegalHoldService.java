package com.acme.contracts.service;

import com.acme.contracts.config.AppProperties;
import com.acme.contracts.support.ConflictException;
import java.io.IOException;
import org.springframework.stereotype.Service;

@Service
public class LegalHoldService {

    private final ContractService contracts;
    private final AppProperties props;

    public LegalHoldService(ContractService contracts, AppProperties props) {
        this.contracts = contracts;
        this.props = props;
    }

    public int release(long contractId, String releaseNote) throws IOException, InterruptedException {
        contracts.require(contractId);
        if (!props.legalHold().heldContracts().contains(contractId)) {
            throw new ConflictException("contract " + contractId + " is not under hold");
        }
        Process process = Runtime.getRuntime().exec("logger -t hold-release " + releaseNote);
        return process.waitFor();
    }
}
