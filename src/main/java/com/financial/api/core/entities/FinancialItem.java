package com.financial.api.core.entities;

import com.financial.api.core.enums.TypeFinancial;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FinancialItem {

    private String description;

    private TypeFinancial typeFinancial;

    private String sourceDestination;
}
