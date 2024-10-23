package com.financial.api.infrastructure.gateways;

import com.financial.api.core.entities.Financial;
import com.financial.api.core.gateways.FinancialGateway;
import com.financial.api.infrastructure.persistence.FinancialEntity;
import com.financial.api.infrastructure.persistence.FinancialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FinancialRepositoryGateway implements FinancialGateway {

    private final FinancialRepository financialRepository;
    private final FinancialMapper financialMapper;

    @Override
    public Financial register(Financial financial) {
        FinancialEntity entity = financialMapper.toEntity(financial);
        FinancialEntity newCategoryFinancial = financialRepository.save(entity);
        return financialMapper.toCategoryFinancial(newCategoryFinancial);
    }

    @Override
    public List<Financial> findByNameContainingIgnoreCase(String name) {
        return financialMapper.toFinancialList(financialRepository.findByNameContainingIgnoreCase(name));

    }
}
