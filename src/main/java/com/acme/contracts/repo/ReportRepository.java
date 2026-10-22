package com.acme.contracts.repo;

import com.acme.contracts.model.Report;
import com.acme.contracts.model.ReportColumn;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ReportRepository {

    private final JdbcTemplate jdbc;

    public ReportRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public long insert(String name, String filterExpr, ReportColumn sortBy) {
        return Inserts.insert(jdbc, "INSERT INTO reports (name, filter_expr, sort_by) VALUES (?, ?, ?)",
                name, filterExpr, sortBy.name());
    }

    public Optional<Report> find(long id) {
        List<Report> rows = jdbc.query("SELECT id, name, filter_expr, sort_by FROM reports WHERE id = ?",
                (rs, i) -> new Report(rs.getLong("id"), rs.getString("name"), rs.getString("filter_expr"),
                        ReportColumn.valueOf(rs.getString("sort_by"))), id);
        return rows.stream().findFirst();
    }

    public List<Long> idsMatching(String filterExpr) {
        return jdbc.queryForList("SELECT id FROM contracts WHERE " + filterExpr, Long.class);
    }
}
