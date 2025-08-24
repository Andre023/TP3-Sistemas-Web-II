// src/main/java/br/com/fintech/investments/converter/InvestmentConverter.java
package br.com.fintech.investments.converter;

import br.com.fintech.investments.domain.InvestmentDomain;
import br.com.fintech.investments.dtos.CreateInvestmentDTO;
import br.com.fintech.investments.dtos.InvestmentPerformanceDTO;
import br.com.fintech.investments.model.InvestmentModel;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class InvestmentConverter {

    // DTO -> Domain
    public InvestmentDomain toDomain(CreateInvestmentDTO dto) {
        return InvestmentDomain.builder()
                .type(dto.type())
                .symbol(dto.symbol())
                .quantity(dto.quantity())
                .purchasePrice(dto.purchasePrice())
                .currentMarketPrice(dto.purchasePrice()) // Valor inicial de mercado
                .purchaseDate(dto.purchaseDate())
                .build();
    }

    // Domain -> Model
    public InvestmentModel toModel(InvestmentDomain domain) {
        return InvestmentModel.builder()
                .id(domain.getId())
                .type(domain.getType())
                .symbol(domain.getSymbol())
                .quantity(domain.getQuantity())
                .purchasePrice(domain.getPurchasePrice())
                .currentMarketPrice(domain.getCurrentMarketPrice())
                .purchaseDate(domain.getPurchaseDate())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }

    // Model -> Domain
    public InvestmentDomain toDomain(InvestmentModel model) {
        return InvestmentDomain.builder()
                .id(model.getId())
                .type(model.getType())
                .symbol(model.getSymbol())
                .quantity(model.getQuantity())
                .purchasePrice(model.getPurchasePrice())
                .currentMarketPrice(model.getCurrentMarketPrice())
                .purchaseDate(model.getPurchaseDate())
                .createdAt(model.getCreatedAt())
                .updatedAt(model.getUpdatedAt())
                .build();
    }

    // Domain -> DTO (Performance)
    public InvestmentPerformanceDTO toPerformanceDTO(InvestmentDomain domain) {
        BigDecimal quantity = new BigDecimal(domain.getQuantity());
        BigDecimal totalInvested = domain.getPurchasePrice().multiply(quantity);

        BigDecimal currentMarketPrice = domain.getCurrentMarketPrice() != null ? domain.getCurrentMarketPrice() : BigDecimal.ZERO;
        BigDecimal currentTotalValue = currentMarketPrice.multiply(quantity);
        BigDecimal profitOrLoss = currentTotalValue.subtract(totalInvested);

        return new InvestmentPerformanceDTO(
                domain.getId(),
                domain.getType(),
                domain.getSymbol(),
                domain.getQuantity(),
                domain.getPurchasePrice(),
                domain.getCurrentMarketPrice(),
                domain.getPurchaseDate(),
                totalInvested,
                currentTotalValue,
                profitOrLoss
        );
    }
}