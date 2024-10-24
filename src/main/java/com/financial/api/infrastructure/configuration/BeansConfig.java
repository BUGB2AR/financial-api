package com.financial.api.infrastructure.configuration;

import com.financial.api.core.gateways.FinancialGateway;
import com.financial.api.core.usecases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Bean
    public CreateFinancialUseCase createFinancialUseCase(FinancialGateway financialGateway) {
        return new CreateFinancialUseCaseImpl(financialGateway);
    }

    @Bean
    public FindBynameContainingUseCase findBynameContainingUseCase(FinancialGateway financialGateway) {
        return new FindNameContainingIgnoreUseCaseImpl(financialGateway);
    }

    @Bean
    FindByFinancialUseCase findByFinancialUseCase(FinancialGateway financialGateway) {
        return new FindByFinancialUseCaseImpl(financialGateway);
    }
}
