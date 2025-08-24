// src/main/java/br/com/fintech/investments/dtos/UpdateMarketValueDTO.java
package br.com.fintech.investments.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record UpdateMarketValueDTO(
        @NotNull @Positive BigDecimal currentMarketPrice
) {}