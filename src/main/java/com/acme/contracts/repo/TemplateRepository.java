package com.acme.contracts.repo;

import com.acme.contracts.model.Template;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class TemplateRepository {

    private static final RowMapper<Template> MAPPER = (rs, i) ->
            new Template(rs.getLong("id"), rs.getString("name"), rs.getString("body"), rs.getString("category"));

    private final JdbcTemplate jdbc;

    public TemplateRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public long insert(String name, String body, String category) {
        return Inserts.insert(jdbc, "INSERT INTO templates (name, body, category) VALUES (?, ?, ?)",
                name, body, category);
    }

    public Optional<Template> find(long id) {
        List<Template> rows = jdbc.query("SELECT id, name, body, category FROM templates WHERE id = ?", MAPPER, id);
        return rows.stream().findFirst();
    }
}
