package com.bitwise.extractcvs.service;

import com.bitwise.extractcvs.enums.TipoExtrato;
import org.springframework.stereotype.Component;

@Component
public class ExtratoService {

    public TipoExtrato determinarTipoExtrato(String name) {
        if (name.toLowerCase().contains("pessoa-fisica")) {
            return TipoExtrato.PF;
        } else if (name.toLowerCase().contains("pessoa-juridica")) {
            return TipoExtrato.PJ;
        }

        return TipoExtrato.PF;
    }
}
