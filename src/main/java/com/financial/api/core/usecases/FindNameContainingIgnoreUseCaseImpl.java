package com.financial.api.core.usecases;

import com.financial.api.core.entities.Financial;
import com.financial.api.core.gateways.FinancialGateway;
import java.util.List;

public class FindNameContainingIgnoreUseCaseImpl implements FindBynameContainingUseCase {

    private final FinancialGateway gateway;

    public FindNameContainingIgnoreUseCaseImpl(FinancialGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public List<Financial> execute(String financialName) {
        return gateway.findByNameContainingIgnoreCase(financialName);
    }

}
