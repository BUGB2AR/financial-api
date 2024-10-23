package com.financial.api.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinancialRepository extends JpaRepository<FinancialEntity, Long> {

    List<FinancialEntity> findByNameContainingIgnoreCase(String name);
}
