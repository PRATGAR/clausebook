package com.acme.contracts.service;

import com.acme.contracts.config.AppProperties;
import com.acme.contracts.model.ApprovalStatus;
import com.acme.contracts.repo.ApprovalRepository;
import com.acme.contracts.support.ConflictException;
import com.acme.contracts.support.PdfRenderer;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FinalizeService {

    private final ContractService contracts;
    private final ApprovalRepository approvals;
    private final PdfRenderer renderer;
    private final AppProperties props;

    public FinalizeService(ContractService contracts, ApprovalRepository approvals, PdfRenderer renderer,
                           AppProperties props) {
        this.contracts = contracts;
        this.approvals = approvals;
        this.renderer = renderer;
        this.props = props;
    }

    public int finalizeContract(long contractId, String watermark) throws IOException, InterruptedException {
        contracts.require(contractId);
        ApprovalStatus status = approvals.statusFor(contractId).orElse(ApprovalStatus.PENDING);
        if (status != ApprovalStatus.APPROVED) {
            throw new ConflictException("contract " + contractId + " has not been approved");
        }
        Path input = props.dirs().signedRoot().resolve(contractId + ".pdf");
        Path output = props.dirs().signedRoot().resolve(contractId + "-final.pdf");
        Path stamp = props.dirs().workDir().resolve(contractId + ".stamp");
        String script = "printf '%s' '" + watermark + "' > " + stamp
                + " && pdftk " + input + " stamp " + stamp + " output " + output;
        return renderer.run(List.of("sh", "-c", script));
    }
}
