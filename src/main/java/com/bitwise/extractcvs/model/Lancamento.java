package com.bitwise.extractcvs.model;

import com.bitwise.extractcvs.enums.InstituicaoBancaria;
import com.bitwise.extractcvs.enums.TipoExtrato;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Lancamento {

    @Id
    private String uuid;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataLancamento;

    private String historico;

    private String descricao;

    private BigDecimal valor = BigDecimal.ZERO;

    private BigDecimal saldo = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    private TipoExtrato tipoExtrato;

    @Enumerated(EnumType.STRING)
    private InstituicaoBancaria instituicaoBancaria;

    public Lancamento(LocalDate dataLancamento, String historico, String descricao,
                      BigDecimal valor, BigDecimal saldo, TipoExtrato tipoExtrato,
                      InstituicaoBancaria instituicaoBancaria) {
        this.uuid = UUID.randomUUID().toString();
        this.dataLancamento = dataLancamento;
        this.historico = historico;
        this.descricao = descricao;
        this.valor = valor;
        this.saldo = saldo;
        this.tipoExtrato = tipoExtrato;
        this.instituicaoBancaria = instituicaoBancaria;
    }

    public Lancamento() {}

    @PrePersist
    public void prePersist() {
        if (this.dataLancamento != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDate = this.dataLancamento.format(formatter);
            try {
                this.dataLancamento = LocalDate.parse(formattedDate, formatter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
