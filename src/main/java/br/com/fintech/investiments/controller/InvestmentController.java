package br.com.fintech.investments.controller;

import br.com.fintech.investments.dto.InvestmentRequestDTO;
import br.com.fintech.investments.dto.PortfolioSummaryDTO;
import br.com.fintech.investments.enums.InvestmentType;
import br.com.fintech.investments.model.Investment;
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
    public ResponseEntity<Investment> createInvestment(@Valid @RequestBody InvestmentRequestDTO requestDTO) {
        return ResponseEntity.ok(investmentService.createInvestment(requestDTO));
    }

    @GetMapping
    public ResponseEntity<List<Investment>> getAllInvestments(@RequestParam(required = false) InvestmentType type) {
        return ResponseEntity.ok(investmentService.getAllInvestments(type));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Investment> updateInvestment(@PathVariable UUID id, @Valid @RequestBody InvestmentRequestDTO requestDTO) {
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
}