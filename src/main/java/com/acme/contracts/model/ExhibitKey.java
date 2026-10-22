package com.acme.contracts.model;

public enum ExhibitKey {
    NDA("nda-standard.pdf"),
    DPA("dpa-eu-2024.pdf"),
    SLA("sla-gold.pdf");

    private final String fileName;

    ExhibitKey(String fileName) {
        this.fileName = fileName;
    }

    public String fileName() {
        return fileName;
    }
}
