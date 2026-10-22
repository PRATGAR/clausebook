package com.acme.contracts.service;

import com.acme.contracts.model.Contract;
import com.acme.contracts.repo.VersionRepository;
import com.acme.contracts.support.ContentFingerprint;
import com.acme.contracts.support.VersionLayout;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import org.springframework.stereotype.Service;

@Service
public class VersionService {

    public record SavedVersion(int number, String path) {
    }

    public record VersionBody(String content, String etag) {
    }

    private final ContractService contracts;
    private final VersionRepository versions;
    private final VersionLayout layout;
    private final ContentFingerprint fingerprint;

    public VersionService(ContractService contracts, VersionRepository versions, VersionLayout layout,
                          ContentFingerprint fingerprint) {
        this.contracts = contracts;
        this.versions = versions;
        this.layout = layout;
        this.fingerprint = fingerprint;
    }

    public SavedVersion save(long contractId, String content) throws IOException {
        Contract contract = contracts.require(contractId);
        int number = versions.nextNumber(contractId);
        Path dir = layout.versionDir(contract.storageLabel());
        Files.createDirectories(dir);
        Path file = layout.versionFile(contract.storageLabel(), number);
        Files.write(file, content.getBytes(StandardCharsets.UTF_8));
        versions.record(contractId, number, file.toString());
        return new SavedVersion(number, file.toString());
    }

    public VersionBody read(long contractId, int number) throws IOException, NoSuchAlgorithmException {
        Contract contract = contracts.require(contractId);
        Path file = layout.versionFile(contract.storageLabel(), number);
        String content = Files.readString(file);
        return new VersionBody(content, fingerprint.of(content));
    }
}
