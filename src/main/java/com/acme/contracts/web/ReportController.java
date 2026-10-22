package com.acme.contracts.web;

import com.acme.contracts.model.NewReport;
import com.acme.contracts.model.Report;
import com.acme.contracts.model.ReportResult;
import com.acme.contracts.service.ReportService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reports;

    public ReportController(ReportService reports) {
        this.reports = reports;
    }

    @PostMapping
    public Report create(@Valid @RequestBody NewReport request) {
        return reports.create(request);
    }

    @GetMapping("/{id}/run")
    public ReportResult run(@PathVariable long id) {
        return reports.run(id);
    }
}
