package com.bitwise.extractcvs.model;

import com.bitwise.extractcvs.enums.InstituicaoBancaria;
import com.bitwise.extractcvs.enums.TipoExtrato;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class Extrato {
    private UUID uuid;
    private String dataLancamento;
    private String historico;
    private String descricao;
    private BigDecimal valor = BigDecimal.ZERO;
    private BigDecimal saldo = BigDecimal.ZERO;
    private TipoExtrato tipoExtrato;
    private InstituicaoBancaria instituicaoBancaria;

    public Extrato(String dataLancamento, String historico, String descricao, BigDecimal valor, BigDecimal saldo, TipoExtrato tipoExtrato) {
        this.uuid = UUID.randomUUID();
        this.dataLancamento = dataLancamento;
        this.historico = historico;
        this.descricao = descricao;
        this.valor = valor;
        this.saldo = saldo;
        this.tipoExtrato = tipoExtrato;
    }

    public Extrato() {}


}
