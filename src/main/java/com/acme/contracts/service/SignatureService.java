package com.acme.contracts.service;

import com.acme.contracts.model.Contract;
import com.acme.contracts.model.Signer;
import com.acme.contracts.repo.SignatureRequestRepository;
import com.acme.contracts.support.Dispatcher;
import com.acme.contracts.support.PinHasher;
import com.acme.contracts.support.ProviderClient;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class SignatureService {

    private final SecureRandom random = new SecureRandom();
    private final ContractService contracts;
    private final SignerService signers;
    private final SignatureRequestRepository requests;
    private final PinHasher pinHasher;
    private final Dispatcher dispatcher;
    private final ProviderClient provider;

    public SignatureService(ContractService contracts, SignerService signers, SignatureRequestRepository requests,
                            PinHasher pinHasher, Dispatcher dispatcher, ProviderClient provider) {
        this.contracts = contracts;
        this.signers = signers;
        this.requests = requests;
        this.pinHasher = pinHasher;
        this.dispatcher = dispatcher;
        this.provider = provider;
    }

    public Map<String, Object> sendForSignature(long contractId, long signerId)
            throws NoSuchAlgorithmException, IOException, InterruptedException {
        Contract contract = contracts.require(contractId);
        Signer signer = signers.require(signerId);
        String pin = String.format("%04d", random.nextInt(10000));
        long requestId = requests.insert(contract.id(), signer.id(), pinHasher.hash(pin));
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("requestId", requestId);
        if (contract.webhookUrl() != null) {
            Map<String, Object> event = Map.of("event", "signature.requested", "contractId", contract.id(),
                    "requestId", requestId, "signer", signer.email());
            result.put("webhookStatus", dispatcher.post(contract.webhookUrl(), event));
        }
        result.put("providerStatus", provider.registerEnvelope(contract.id(), signer.id()));
        return result;
    }
}
