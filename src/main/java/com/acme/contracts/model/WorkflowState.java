package com.acme.contracts.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;

public class WorkflowState implements Serializable {

    private static final long serialVersionUID = 1L;

    private long contractId;
    private ApprovalStatus stage = ApprovalStatus.PENDING;
    private String assignee;
    private final ArrayList<String> history = new ArrayList<>();
    private Instant updatedAt = Instant.now();

    public long getContractId() {
        return contractId;
    }

    public void setContractId(long contractId) {
        this.contractId = contractId;
    }

    public ApprovalStatus getStage() {
        return stage;
    }

    public void setStage(ApprovalStatus stage) {
        this.stage = stage;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public ArrayList<String> getHistory() {
        return history;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
