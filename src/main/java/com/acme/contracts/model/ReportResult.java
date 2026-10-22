package com.acme.contracts.model;

import java.util.List;

public record ReportResult(List<Long> matchingIds, List<Contract> leading) {
}
