package com.acme.contracts.service;

import com.acme.contracts.model.Contract;
import com.acme.contracts.model.NewReport;
import com.acme.contracts.model.Report;
import com.acme.contracts.model.ReportResult;
import com.acme.contracts.repo.ContractRepository;
import com.acme.contracts.repo.ReportRepository;
import com.acme.contracts.support.NotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    private final ReportRepository reports;
    private final ContractRepository contracts;

    public ReportService(ReportRepository reports, ContractRepository contracts) {
        this.reports = reports;
        this.contracts = contracts;
    }

    public Report create(NewReport request) {
        long id = reports.insert(request.name(), request.filterExpr(), request.sortBy());
        return new Report(id, request.name(), request.filterExpr(), request.sortBy());
    }

    public ReportResult run(long id) {
        Report report = reports.find(id).orElseThrow(() -> new NotFoundException("report", id));
        List<Long> matching = reports.idsMatching(report.filterExpr());
        List<Contract> leading = contracts.orderedBy(report.sortBy().column());
        return new ReportResult(matching, leading);
    }
}
