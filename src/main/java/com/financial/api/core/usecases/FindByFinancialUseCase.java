package com.financial.api.core.usecases;

import com.financial.api.core.entities.Financial;

public interface FindByFinancialUseCase {
    Financial execute(Long id);
}
