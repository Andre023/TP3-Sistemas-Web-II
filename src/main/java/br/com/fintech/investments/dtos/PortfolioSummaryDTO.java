package br.com.fintech.investments.dtos;

import java.math.BigDecimal;
import java.util.Map;

public record PortfolioSummaryDTO(
        BigDecimal totalInvested,
        Map<String, BigDecimal> totalByType,
        long assetCount
) {}