package com.acme.contracts.model;

import jakarta.validation.constraints.NotBlank;

public record FinalizeRequest(@NotBlank String watermark) {
}
