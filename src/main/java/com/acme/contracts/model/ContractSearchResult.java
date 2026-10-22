package com.acme.contracts.model;

import java.util.List;

public record ContractSearchResult(List<Contract> titleMatches) {
}
