package br.com.fintech.investments.repository;

import br.com.fintech.investments.enums.InvestmentType;
import br.com.fintech.investments.model.Investment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InvestmentRepository extends JpaRepository<Investment, UUID> {
    List<Investment> findByType(InvestmentType type);
}