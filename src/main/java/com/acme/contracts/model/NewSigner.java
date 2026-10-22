package com.acme.contracts.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record NewSigner(@NotBlank @Pattern(regexp = "[\\p{L} .'-]{2,80}") String displayName,
                        @NotBlank @Email String email) {
}
