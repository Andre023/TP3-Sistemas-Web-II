// src/main/java/br/com/fintech/investments/service/InvestmentService.java
package br.com.fintech.investments.service;

import br.com.fintech.investments.converter.InvestmentConverter;
import br.com.fintech.investments.domain.InvestmentDomain;
import br.com.fintech.investments.dtos.*;
import br.com.fintech.investments.enums.InvestmentType;
import br.com.fintech.investments.model.InvestmentModel;
import br.com.fintech.investments.repositories.InvestmentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvestmentService {

    private final InvestmentRepository investmentRepository;
    private final InvestmentConverter investmentConverter;

    public InvestmentPerformanceDTO createInvestment(CreateInvestmentDTO requestDTO) {
        InvestmentDomain domain = investmentConverter.toDomain(requestDTO);
        InvestmentModel modelToSave = investmentConverter.toModel(domain);
        InvestmentModel savedModel = investmentRepository.save(modelToSave);

        return investmentConverter.toPerformanceDTO(investmentConverter.toDomain(savedModel));
    }
    public List<InvestmentPerformanceDTO> getAllInvestments(InvestmentType type) {
        List<InvestmentModel> models = (type != null) ?
                investmentRepository.findByType(type) :
                investmentRepository.findAll();

        return models.stream()
                .map(investmentConverter::toDomain)
                .map(investmentConverter::toPerformanceDTO)
                .collect(Collectors.toList());
    }

    public InvestmentPerformanceDTO updateInvestment(UUID id, UpdateInvestmentDTO requestDTO) {
        InvestmentModel existingModel = findByIdOrThrow(id);
        existingModel.setType(requestDTO.type());
        existingModel.setSymbol(requestDTO.symbol());
        existingModel.setQuantity(requestDTO.quantity());
        existingModel.setPurchasePrice(requestDTO.purchasePrice());
        existingModel.setPurchaseDate(requestDTO.purchaseDate());
        InvestmentModel updatedModel = investmentRepository.save(existingModel);
        return investmentConverter.toPerformanceDTO(investmentConverter.toDomain(updatedModel));
    }

    public InvestmentPerformanceDTO updateMarketValue(UUID id, UpdateMarketValueDTO requestDTO) {
        InvestmentModel investmentModel = findByIdOrThrow(id);
        investmentModel.setCurrentMarketPrice(requestDTO.currentMarketPrice());
        InvestmentModel updatedModel = investmentRepository.save(investmentModel);
        return investmentConverter.toPerformanceDTO(investmentConverter.toDomain(updatedModel));
    }
    
    public void deleteInvestment(UUID id) {
        if (!investmentRepository.existsById(id)) {
            throw new EntityNotFoundException("Investment not found with id: " + id);
        }
        investmentRepository.deleteById(id);
    }

    private InvestmentModel findByIdOrThrow(UUID id) {
        return investmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Investment not found with id: " + id));
    }
    public PortfolioSummaryDTO getPortfolioSummary() {
        List<InvestmentModel> investments = investmentRepository.findAll();

        BigDecimal totalInvested = investments.stream()
                .map(inv -> inv.getPurchasePrice().multiply(new BigDecimal(inv.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, BigDecimal> totalByType = investments.stream()
                .collect(Collectors.groupingBy(inv -> inv.getType().name(),
                        Collectors.mapping(inv -> inv.getPurchasePrice().multiply(new BigDecimal(inv.getQuantity())),
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));

        long assetCount = investments.size();

        return new PortfolioSummaryDTO(totalInvested, totalByType, assetCount);
    }
}