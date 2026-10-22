package com.acme.contracts.service;

import com.acme.contracts.model.WorkflowState;
import com.acme.contracts.repo.CheckpointRepository;
import com.acme.contracts.support.NotFoundException;
import com.acme.contracts.support.StateCodec;
import java.io.IOException;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class WorkflowService {

    private final ContractService contracts;
    private final CheckpointRepository checkpoints;
    private final StateCodec codec;

    public WorkflowService(ContractService contracts, CheckpointRepository checkpoints, StateCodec codec) {
        this.contracts = contracts;
        this.checkpoints = checkpoints;
        this.codec = codec;
    }

    public int checkpoint(long contractId, byte[] blob) {
        contracts.require(contractId);
        checkpoints.save(contractId, blob);
        return blob.length;
    }

    public Map<String, Object> resume(long contractId) throws IOException, ClassNotFoundException {
        byte[] blob = checkpoints.load(contractId).orElseThrow(() -> new NotFoundException("checkpoint", contractId));
        return summarize(codec.restore(blob));
    }

    public Map<String, Object> restoreDraft(long contractId) throws IOException, ClassNotFoundException {
        byte[] blob = checkpoints.load(contractId).orElseThrow(() -> new NotFoundException("checkpoint", contractId));
        return summarize(codec.restoreDraft(blob));
    }

    private Map<String, Object> summarize(WorkflowState state) {
        return Map.of("contractId", state.getContractId(), "stage", state.getStage().name(),
                "assignee", String.valueOf(state.getAssignee()), "steps", state.getHistory().size());
    }
}
