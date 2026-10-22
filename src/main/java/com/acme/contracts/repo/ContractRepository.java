package com.acme.contracts.repo;

import com.acme.contracts.model.Contract;
import com.acme.contracts.model.ContractStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ContractRepository {

    private static final String COLUMNS =
            "id, title, counterparty, storage_label, webhook_url, status, template_id";

    private static final RowMapper<Contract> MAPPER = (rs, i) -> new Contract(
            rs.getLong("id"), rs.getString("title"), rs.getString("counterparty"),
            rs.getString("storage_label"), rs.getString("webhook_url"),
            ContractStatus.valueOf(rs.getString("status")), (Long) rs.getObject("template_id"));

    private final JdbcTemplate jdbc;

    public ContractRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public long insert(String title, String counterparty, String storageLabel, String webhookUrl, Long templateId) {
        return Inserts.insert(jdbc,
                "INSERT INTO contracts (title, counterparty, storage_label, webhook_url, status, template_id) "
                        + "VALUES (?, ?, ?, ?, 'DRAFT', ?)",
                title, counterparty, storageLabel, webhookUrl, templateId);
    }

    public Optional<Contract> find(long id) {
        List<Contract> rows = jdbc.query("SELECT " + COLUMNS + " FROM contracts WHERE id = ?", MAPPER, id);
        return rows.stream().findFirst();
    }

    public List<Contract> titleContains(String term) {
        String clause = TextMatch.contains("title", term);
        return jdbc.query("SELECT " + COLUMNS + " FROM contracts WHERE " + clause, MAPPER);
    }

    public List<Contract> orderedBy(String column) {
        return jdbc.query("SELECT " + COLUMNS + " FROM contracts ORDER BY " + column + " LIMIT 10", MAPPER);
    }
}
