package com.financial.api.core.usecases;

import com.financial.api.core.entities.Financial;
import com.financial.api.core.gateways.FinancialGateway;

public class FindByFinancialUseCaseImpl implements FindByFinancialUseCase {

    private final FinancialGateway gateway;

    public FindByFinancialUseCaseImpl(FinancialGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public Financial execute(Long id) {
        return gateway.getFinancialById(id);
    }
}
