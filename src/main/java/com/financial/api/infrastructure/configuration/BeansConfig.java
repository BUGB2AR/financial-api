package com.financial.api.infrastructure.configuration;

import com.financial.api.core.gateways.FinancialGateway;
import com.financial.api.core.usecases.CreateFinancialUseCase;
import com.financial.api.core.usecases.CreateFinancialUseCaseImpl;
import com.financial.api.core.usecases.FindBynameContainingUseCase;
import com.financial.api.core.usecases.FindNameContainingIgnoreUseCaseImpl;
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
}
