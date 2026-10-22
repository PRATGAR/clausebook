package com.acme.contracts.repo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SignatureRequestRepository {

    private final JdbcTemplate jdbc;

    public SignatureRequestRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public long insert(long contractId, long signerId, String pinHash) {
        return Inserts.insert(jdbc,
                "INSERT INTO signature_requests (contract_id, signer_id, pin_hash, status) VALUES (?, ?, ?, 'SENT')",
                contractId, signerId, pinHash);
    }
}
