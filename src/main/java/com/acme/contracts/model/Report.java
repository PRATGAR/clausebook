package com.acme.contracts.model;

public record Report(long id, String name, String filterExpr, ReportColumn sortBy) {
}
