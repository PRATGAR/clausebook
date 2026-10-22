package com.acme.contracts.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewReport(@NotBlank String name, @NotBlank String filterExpr, @NotNull ReportColumn sortBy) {
}
