package com.financial.api.core.usecases;

import com.financial.api.core.entities.Financial;

public interface CreateFinancialUseCase {
    Financial execute(Financial financial);
}
