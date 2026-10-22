package com.acme.contracts.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ExportRequest(@NotBlank @Pattern(regexp = "[\\w./-]{1,120}") String folder) {
}
