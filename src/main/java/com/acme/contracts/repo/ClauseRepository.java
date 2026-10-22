package com.acme.contracts.repo;

import com.acme.contracts.model.Clause;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ClauseRepository {

    private static final RowMapper<Clause> MAPPER = (rs, i) ->
            new Clause(rs.getLong("id"), rs.getString("title"), rs.getString("text"));

    private final JdbcTemplate jdbc;

    public ClauseRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public long insert(String title, String text) {
        return Inserts.insert(jdbc, "INSERT INTO clauses (title, text) VALUES (?, ?)", title, text);
    }

    public Optional<Clause> find(long id) {
        List<Clause> rows = jdbc.query("SELECT id, title, text FROM clauses WHERE id = ?", MAPPER, id);
        return rows.stream().findFirst();
    }
}
