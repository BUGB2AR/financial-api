package com.financial.api.core.usecases;

import com.financial.api.core.entities.Financial;

import java.util.List;

public interface FindBynameContainingUseCase {
    List<Financial> execute(String name);
}
