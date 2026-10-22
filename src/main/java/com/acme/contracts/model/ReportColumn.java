package com.acme.contracts.model;

public enum ReportColumn {
    TITLE("title"),
    COUNTERPARTY("counterparty"),
    STATUS("status"),
    CREATED("id");

    private final String column;

    ReportColumn(String column) {
        this.column = column;
    }

    public String column() {
        return column;
    }
}
