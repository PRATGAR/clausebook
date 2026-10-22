package com.acme.contracts.config;

import java.nio.file.Path;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public record AppProperties(Dirs dirs, Ldap ldap, Webhook webhook, Provider provider,
                            Signature signature, LegalHold legalHold) {

    public record Dirs(Path storageRoot, Path exhibitRoot, Path signedRoot,
                       Path exportRoot, Path workDir) {
    }

    public record Ldap(String url, String baseDn) {
    }

    public record Webhook(String allowedHost) {
    }

    public record Provider(String baseUrl, String apiKey) {
    }

    public record Signature(String pinDigest) {
    }

    public record LegalHold(List<Long> heldContracts) {
    }
}
