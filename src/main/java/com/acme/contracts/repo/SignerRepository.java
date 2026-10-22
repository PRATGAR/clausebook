package com.acme.contracts.repo;

import com.acme.contracts.model.Signer;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class SignerRepository {

    private static final RowMapper<Signer> MAPPER = (rs, i) ->
            new Signer(rs.getLong("id"), rs.getString("display_name"), rs.getString("email"),
                    rs.getLong("employee_id"));

    private final JdbcTemplate jdbc;

    public SignerRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public long insert(String displayName, String email) {
        return Inserts.insert(jdbc, "INSERT INTO signers (display_name, email) VALUES (?, ?)", displayName, email);
    }

    public Optional<Signer> find(long id) {
        List<Signer> rows = jdbc.query(
                "SELECT id, display_name, email, employee_id FROM signers WHERE id = ?", MAPPER, id);
        return rows.stream().findFirst();
    }
}
