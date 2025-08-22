package br.com.fintech.investments.service;

import br.com.fintech.investments.dto.InvestmentRequestDTO;
import br.com.fintech.investments.dto.PortfolioSummaryDTO;
import br.com.fintech.investments.enums.InvestmentType;
import br.com.fintech.investments.model.Investment;
import br.com.fintech.investments.repository.InvestmentRepository;
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

    public Investment createInvestment(InvestmentRequestDTO requestDTO) {
        Investment investment = new Investment();
        investment.setType(requestDTO.type());
        investment.setSymbol(requestDTO.symbol());
        investment.setQuantity(requestDTO.quantity());
        investment.setPurchasePrice(requestDTO.purchasePrice());
        investment.setPurchaseDate(requestDTO.purchaseDate());
        return investmentRepository.save(investment);
    }

    public List<Investment> getAllInvestments(InvestmentType type) {
        if (type != null) {
            return investmentRepository.findByType(type);
        }
        return investmentRepository.findAll();
    }

    public Investment updateInvestment(UUID id, InvestmentRequestDTO requestDTO) {
        Investment investment = investmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Investment not found with id: " + id));

        investment.setType(requestDTO.type());
        investment.setSymbol(requestDTO.symbol());
        investment.setQuantity(requestDTO.quantity());
        investment.setPurchasePrice(requestDTO.purchasePrice());
        investment.setPurchaseDate(requestDTO.purchaseDate());

        return investmentRepository.save(investment);
    }

    public void deleteInvestment(UUID id) {
        if (!investmentRepository.existsById(id)) {
            throw new EntityNotFoundException("Investment not found with id: " + id);
        }
        investmentRepository.deleteById(id);
    }

    public PortfolioSummaryDTO getPortfolioSummary() {
        List<Investment> investments = investmentRepository.findAll();

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