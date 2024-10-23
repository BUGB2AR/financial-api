package com.financial.api.infrastructure.controllers;

import com.financial.api.core.entities.Financial;
import com.financial.api.core.usecases.CreateFinancialUseCase;
import com.financial.api.core.usecases.FindBynameContainingUseCase;
import com.financial.api.infrastructure.dtos.FinancialDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/financial")
@AllArgsConstructor
public class FinancialController {

    private final CreateFinancialUseCase createFinancialUseCase;
    private final FindBynameContainingUseCase findBynameContainingUseCase;
    private final FinancialDtoMapper financialDtoMapper;

    @PostMapping
    public FinancialDTO registerFinancial(@RequestBody final FinancialDTO financialDTO) {
        Financial newFinancial = createFinancialUseCase.execute(financialDtoMapper.toDomain(financialDTO));
        return financialDtoMapper.toDTO(newFinancial);
    }

    @GetMapping("/find-by-name")
    public List<FinancialDTO> findFinancialByNameIgnoreCase(@RequestParam String name) {
        List<Financial> financials = findBynameContainingUseCase.execute(name);
        return financialDtoMapper.toDTOList(financials);
    }

}
