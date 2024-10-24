package com.financial.api.infrastructure.controllers;

import com.financial.api.core.entities.Financial;
import com.financial.api.core.entities.FinancialItem;
import com.financial.api.infrastructure.dtos.FinancialDTO;
import com.financial.api.infrastructure.dtos.FinancialItemDTO;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FinancialDtoMapper {

    public FinancialDTO toDTO(Financial financial) {
        return new FinancialDTO(
                financial.getId(),
                financial.getName(),
                toItemDTOList(financial.getItens())
        );
    }

    public Financial toDomain(FinancialDTO financialDTO) {
        return new Financial(
                financialDTO.id(),
                financialDTO.description(),
                toItemDomainList(financialDTO.itens()));
    }

    public List<Financial> toDomainList(List<FinancialDTO> financialDTOList) {
        return financialDTOList.
                stream().
                map(this::toDomain).
                collect(Collectors.toList());
    }

    public List<FinancialDTO> toDTOList(List<Financial> financialList) {
        return financialList.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<FinancialItemDTO> toItemDTOList(List<FinancialItem> items) {
        return items.stream()
                .map(item -> new FinancialItemDTO(
                        item.getDescription(),
                        item.getTypeFinancial(),
                        item.getSourceDestination()))
                .collect(Collectors.toList());
    }

    public List<FinancialItem> toItemDomainList(List<FinancialItemDTO> itemDTOs) {
        return itemDTOs.stream()
                .map(dto -> new FinancialItem(
                        dto.description(),
                        dto.typeFinancial(),
                        dto.sourceDestination()))
                .collect(Collectors.toList());
    }
}