// src/main/java/br/com/fintech/investments/repositories/InvestmentRepository.java
package br.com.fintech.investments.repositories;

import br.com.fintech.investments.enums.InvestmentType;
import br.com.fintech.investments.model.InvestmentModel; // <-- MUDANÇA AQUI
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InvestmentRepository extends JpaRepository<InvestmentModel, UUID> { // <-- MUDANÇA AQUI
    List<InvestmentModel> findByType(InvestmentType type); // <-- MUDANÇA AQUI
}