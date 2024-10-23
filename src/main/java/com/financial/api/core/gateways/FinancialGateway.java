package com.financial.api.core.gateways;

import com.financial.api.core.entities.Financial;

import java.util.List;

public interface FinancialGateway {
    Financial register(Financial financial);
    List<Financial> findByNameContainingIgnoreCase(String name);
}
