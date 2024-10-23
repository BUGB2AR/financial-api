package com.financial.api.infrastructure.persistence;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "category_financial")
public class FinancialEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ElementCollection
    @CollectionTable(name = "financial_item", joinColumns = @JoinColumn(name = "category_id"))
    private List<FinancialItemEmbeddable> itens;
}
