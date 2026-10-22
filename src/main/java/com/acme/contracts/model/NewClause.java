package com.acme.contracts.model;

import com.acme.contracts.support.PlainClauseText;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NewClause(@NotBlank @Size(max = 200) String title,
                        @NotBlank @Size(max = 4000) @PlainClauseText String text) {
}
