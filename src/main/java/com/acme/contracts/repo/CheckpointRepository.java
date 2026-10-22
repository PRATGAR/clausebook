package com.acme.contracts.repo;

import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CheckpointRepository {

    private final JdbcTemplate jdbc;

    public CheckpointRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void save(long contractId, byte[] blob) {
        jdbc.update("MERGE INTO checkpoints (contract_id, state_blob) KEY (contract_id) VALUES (?, ?)",
                contractId, blob);
    }

    public Optional<byte[]> load(long contractId) {
        List<byte[]> rows = jdbc.query("SELECT state_blob FROM checkpoints WHERE contract_id = ?",
                (rs, i) -> rs.getBytes("state_blob"), contractId);
        return rows.stream().findFirst();
    }
}
