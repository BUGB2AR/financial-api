package com.financial.api.infrastructure.dtos;

import java.util.List;

public record FinancialDTO(Long id, String description, List<FinancialItemDTO> itens) {
}
