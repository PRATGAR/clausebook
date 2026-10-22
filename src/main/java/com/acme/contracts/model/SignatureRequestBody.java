package com.acme.contracts.model;

import jakarta.validation.constraints.NotNull;

public record SignatureRequestBody(@NotNull Long signerId) {
}
