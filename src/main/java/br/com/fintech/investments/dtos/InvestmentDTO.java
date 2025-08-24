// src/main/java/br/com/fintech/investments/dto/InvestmentDTO.java
package br.com.fintech.investments.dtos;

import br.com.fintech.investments.enums.InvestmentType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record InvestmentDTO(
        UUID id,
        InvestmentType type,
        String symbol,
        Integer quantity,
        BigDecimal purchasePrice,
        LocalDate purchaseDate
) {}