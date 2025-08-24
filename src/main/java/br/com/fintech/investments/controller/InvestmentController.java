package br.com.fintech.investments.controller;

import br.com.fintech.investments.dtos.*;
import br.com.fintech.investments.enums.InvestmentType;
import br.com.fintech.investments.service.InvestmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/investments")
@RequiredArgsConstructor
public class InvestmentController {

    private final InvestmentService investmentService;

    @PostMapping
    public ResponseEntity<InvestmentPerformanceDTO> createInvestment(@Valid @RequestBody CreateInvestmentDTO requestDTO) {
        return ResponseEntity.ok(investmentService.createInvestment(requestDTO));
    }

    @GetMapping
    public ResponseEntity<List<InvestmentPerformanceDTO>> getAllInvestments(@RequestParam(required = false) InvestmentType type) {
        return ResponseEntity.ok(investmentService.getAllInvestments(type));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvestmentPerformanceDTO> updateInvestment(@PathVariable UUID id, @Valid @RequestBody UpdateInvestmentDTO requestDTO) {
        return ResponseEntity.ok(investmentService.updateInvestment(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvestment(@PathVariable UUID id) {
        investmentService.deleteInvestment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/summary")
    public ResponseEntity<PortfolioSummaryDTO> getPortfolioSummary() {
        return ResponseEntity.ok(investmentService.getPortfolioSummary());
    }

    @PatchMapping("/{id}/market-value")
    public ResponseEntity<InvestmentPerformanceDTO> updateMarketValue(@PathVariable UUID id, @Valid @RequestBody UpdateMarketValueDTO requestDTO) {
        return ResponseEntity.ok(investmentService.updateMarketValue(id, requestDTO));
    }
}