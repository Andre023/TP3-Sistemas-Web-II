// src/main/java/br/com/fintech/investments/dto/UpdateInvestmentDTO.java
package br.com.fintech.investments.dtos;

import br.com.fintech.investments.enums.InvestmentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateInvestmentDTO(
    @NotNull InvestmentType type,
    @NotBlank String symbol,
    @NotNull @Positive Integer quantity,
    @NotNull @Positive BigDecimal purchasePrice,
    @NotNull LocalDate purchaseDate
) {}