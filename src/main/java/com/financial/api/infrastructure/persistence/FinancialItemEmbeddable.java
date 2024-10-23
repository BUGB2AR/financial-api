package com.financial.api.infrastructure.persistence;

import com.financial.api.core.enums.TypeFinancial;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "financial_item")
public class FinancialItemEmbeddable {

    private String description;

    @Enumerated(EnumType.STRING)
    private TypeFinancial typeFinancial;

    private String sourceDestination;

}
