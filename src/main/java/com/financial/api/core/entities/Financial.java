package com.financial.api.core.entities;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Financial {

    private Long id;

    private String name;

    private List<FinancialItem> itens;
}
