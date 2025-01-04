package com.bitwise.extractcvs.enums;

import lombok.Getter;

@Getter
public enum TipoExtrato {
    PF("Pessoa Física"),
    PJ("Pessoa Jurídica");

    private final String descricao;

    TipoExtrato(String descricao) {
        this.descricao = descricao;
    }


}
