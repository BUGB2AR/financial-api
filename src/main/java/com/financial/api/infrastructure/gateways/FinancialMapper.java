package com.financial.api.infrastructure.gateways;

import com.financial.api.core.entities.Financial;
import com.financial.api.core.entities.FinancialItem;
import com.financial.api.infrastructure.persistence.FinancialEntity;
import com.financial.api.infrastructure.persistence.FinancialItemEmbeddable;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FinancialMapper {

    public FinancialEntity toEntity(Financial financial) {
        return new FinancialEntity(
                financial.getId(),
                financial.getName(),
                toEmbeddableList(financial.getItens())
        );
    }

    public Financial toFinancial(FinancialEntity financialEntity) {
        return new Financial(
                financialEntity.getId(),
                financialEntity.getName(),
                toItemList(financialEntity.getItens())
        );
    }

    public Financial toCategoryFinancial(FinancialEntity financialEntity) {
        return new Financial(
                financialEntity.getId(),
                financialEntity.getName(),
                toItemList(financialEntity.getItens())
        );
    }

    private List<FinancialItemEmbeddable> toEmbeddableList(List<FinancialItem> items) {
        return items.stream()
                .map(this::toEmbeddable)
                .collect(Collectors.toList());
    }

    private List<FinancialItem> toItemList(List<FinancialItemEmbeddable> embeddables) {
        return embeddables.stream()
                .map(this::toFinancialItem)
                .collect(Collectors.toList());
    }

    public List<Financial> toFinancialList(List<FinancialEntity> financialEntities) {
        return financialEntities.stream().map(this::toFinancial)
                .collect(Collectors.toList());
    }

    public List<FinancialEntity> toEntityList(List<Financial> financials) {
        return financials.stream().map(this::toEntity)
                .collect(Collectors.toList());
    }

    private FinancialItemEmbeddable toEmbeddable(FinancialItem item) {
        return new FinancialItemEmbeddable(
                item.getDescription(),
                item.getTypeFinancial(),
                item.getSourceDestination());
    }

    private FinancialItem toFinancialItem(FinancialItemEmbeddable embeddable) {
        return new FinancialItem(
                embeddable.getDescription(),
                embeddable.getTypeFinancial(),
                embeddable.getSourceDestination());
    }
}
