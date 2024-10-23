package com.financial.api.infrastructure.dtos;

import com.financial.api.core.enums.TypeFinancial;

public record FinancialItemDTO(String description, TypeFinancial typeFinancial, String sourceDestination) {
}
