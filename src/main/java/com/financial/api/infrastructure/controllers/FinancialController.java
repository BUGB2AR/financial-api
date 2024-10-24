package com.financial.api.infrastructure.controllers;

import com.financial.api.core.entities.Financial;
import com.financial.api.core.usecases.CreateFinancialUseCase;
import com.financial.api.core.usecases.FindByFinancialUseCase;
import com.financial.api.core.usecases.FindBynameContainingUseCase;
import com.financial.api.infrastructure.dtos.FinancialDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static com.financial.api.infrastructure.util.ObjectUtil.copy;

@RestController
@RequestMapping("/financial")
@AllArgsConstructor
public class FinancialController {

    private final CreateFinancialUseCase createFinancialUseCase;
    private final FindBynameContainingUseCase findBynameContainingUseCase;
    private final FindByFinancialUseCase findByFinancialUseCase;
    private final FinancialDtoMapper financialDtoMapper;

    @PostMapping
    public FinancialDTO registerFinancial(@RequestBody final FinancialDTO financialDTO) {
        Financial newFinancial = createFinancialUseCase.execute(financialDtoMapper.toDomain(financialDTO));
        return financialDtoMapper.toDTO(newFinancial);
    }

    @GetMapping("/find-by-name")
    public List<FinancialDTO> findFinancialByNameIgnoreCase(@RequestParam final String name) {
        List<Financial> financials = findBynameContainingUseCase.execute(name);
        return financialDtoMapper.toDTOList(financials);
    }

    @GetMapping("find-by/{id}")
    public FinancialDTO findFinancialById(@PathVariable final Long id) {
        Financial financial = findByFinancialUseCase.execute(id);
        return financialDtoMapper.toDTO(financial);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<FinancialDTO> editFinancial(
            @PathVariable final Long id,
            @RequestBody  final FinancialDTO financialDTO) {

        Financial realObject = financialDtoMapper.toDomain(financialDTO);
        Financial copied = findByFinancialUseCase.execute(id);

        copy(realObject, copied);

        Financial save = createFinancialUseCase.execute(copied);
        FinancialDTO finacialDtoSaved = financialDtoMapper.toDTO(save);

        return ResponseEntity.ok(finacialDtoSaved);
    }

}
