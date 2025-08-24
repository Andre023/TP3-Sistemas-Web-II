package br.com.fintech.investments.dtos;

import br.com.fintech.investments.enums.InvestmentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

public record InvestmentRequestDTO(
        @NotNull InvestmentType type,
        @NotBlank String symbol,
        @NotNull @Positive Integer quantity,
        @NotNull @Positive BigDecimal purchasePrice,
        @NotNull LocalDate purchaseDate
) {}