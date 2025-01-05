package com.bitwise.extractcvs.service.impl;

import com.bitwise.extractcvs.model.Lancamento;
import com.bitwise.extractcvs.repository.LancamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class LancamentoService {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    public void createLancamento(Lancamento lancamento) {
        if (lancamento.getUuid() == null || lancamento.getUuid().isEmpty()) {
            lancamento.setUuid(UUID.randomUUID().toString());
        }

        lancamentoRepository.save(lancamento);
    }

}
