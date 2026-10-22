package com.acme.contracts.service;

import com.acme.contracts.model.ExhibitKey;
import com.acme.contracts.repo.AttachmentStore;
import java.io.IOException;
import org.springframework.stereotype.Service;

@Service
public class AttachmentService {

    private final ContractService contracts;
    private final AttachmentStore store;

    public AttachmentService(ContractService contracts, AttachmentStore store) {
        this.contracts = contracts;
        this.store = store;
    }

    public byte[] exhibit(long contractId, ExhibitKey key) throws IOException {
        contracts.require(contractId);
        return store.readExhibit(key);
    }
}
