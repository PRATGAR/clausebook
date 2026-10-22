package com.acme.contracts.model;

import jakarta.validation.constraints.NotBlank;

public record NewContract(@NotBlank String title, @NotBlank String counterparty,
                          @NotBlank String storageLabel, String webhookUrl, Long templateId) {
}
