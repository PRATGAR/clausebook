package com.acme.contracts.web;

import com.acme.contracts.model.Clause;
import com.acme.contracts.model.NewClause;
import com.acme.contracts.service.ClauseService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clauses")
public class ClauseController {

    private final ClauseService clauses;

    public ClauseController(ClauseService clauses) {
        this.clauses = clauses;
    }

    @PostMapping
    public Clause create(@Valid @RequestBody NewClause request) {
        return clauses.create(request);
    }

    @GetMapping("/{id}/rendered")
    public Map<String, String> rendered(@PathVariable long id, @RequestParam Map<String, String> params) {
        Map<String, Object> variables = new HashMap<>(params);
        return Map.of("text", clauses.render(id, variables));
    }
}
