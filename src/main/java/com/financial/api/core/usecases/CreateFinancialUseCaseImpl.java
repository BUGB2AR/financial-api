package com.financial.api.core.usecases;

import com.financial.api.core.entities.Financial;
import com.financial.api.core.gateways.FinancialGateway;

public class CreateFinancialUseCaseImpl implements CreateFinancialUseCase {

    private final FinancialGateway financialGateway;

    public CreateFinancialUseCaseImpl(FinancialGateway financialGateway) {
        this.financialGateway = financialGateway;
    }

    @Override
    public Financial execute(Financial financial) {
        return financialGateway.register(financial);
    }
}
