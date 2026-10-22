package com.acme.contracts.repo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class VersionRepository {

    private final JdbcTemplate jdbc;

    public VersionRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public int nextNumber(long contractId) {
        Integer next = jdbc.queryForObject(
                "SELECT COALESCE(MAX(version_no), 0) + 1 FROM contract_versions WHERE contract_id = ?",
                Integer.class, contractId);
        return next == null ? 1 : next;
    }

    public void record(long contractId, int versionNo, String filePath) {
        jdbc.update("INSERT INTO contract_versions (contract_id, version_no, file_path) VALUES (?, ?, ?)",
                contractId, versionNo, filePath);
    }
}
