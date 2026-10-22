package com.acme.contracts.repo;

import com.acme.contracts.model.ApprovalStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ApprovalRepository {

    private final JdbcTemplate jdbc;

    public ApprovalRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void open(long contractId) {
        jdbc.update("INSERT INTO approvals (contract_id, status) VALUES (?, ?)",
                contractId, ApprovalStatus.PENDING.name());
    }

    public Optional<ApprovalStatus> statusFor(long contractId) {
        List<String> rows = jdbc.queryForList(
                "SELECT status FROM approvals WHERE contract_id = ?", String.class, contractId);
        return rows.stream().findFirst().map(ApprovalStatus::valueOf);
    }

    public int setStatus(long contractId, ApprovalStatus status) {
        return jdbc.update("UPDATE approvals SET status = ? WHERE contract_id = ?", status.name(), contractId);
    }
}
