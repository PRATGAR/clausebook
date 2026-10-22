package com.acme.contracts.service;

import com.acme.contracts.model.Clause;
import com.acme.contracts.model.NewClause;
import com.acme.contracts.repo.ClauseRepository;
import com.acme.contracts.support.ClauseRenderer;
import com.acme.contracts.support.NotFoundException;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class ClauseService {

    private final ClauseRepository clauses;
    private final ClauseRenderer renderer;

    public ClauseService(ClauseRepository clauses, ClauseRenderer renderer) {
        this.clauses = clauses;
        this.renderer = renderer;
    }

    public Clause create(NewClause request) {
        long id = clauses.insert(request.title(), request.text());
        return new Clause(id, request.title(), request.text());
    }

    public String render(long id, Map<String, Object> variables) {
        Clause clause = clauses.find(id).orElseThrow(() -> new NotFoundException("clause", id));
        return renderer.render(clause, variables);
    }
}
