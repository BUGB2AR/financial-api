package com.bitwise.extractcvs.converter;

import com.bitwise.extractcvs.model.Extrato;
import com.bitwise.extractcvs.model.Lancamento;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Converter {
    public static Lancamento convertExtratoToLancamento(Extrato extrato) {
        Lancamento lancamento = new Lancamento();

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataLancamento = LocalDate.parse(extrato.getDataLancamento(), formatter);
            lancamento.setDataLancamento(dataLancamento);
        } catch (Exception e) {
            e.printStackTrace();
        }

        lancamento.setValor(extrato.getValor());
        lancamento.setSaldo(extrato.getSaldo());
        lancamento.setHistorico(extrato.getHistorico());
        lancamento.setDescricao(extrato.getDescricao());
        lancamento.setInstituicaoBancaria(extrato.getInstituicaoBancaria());
        lancamento.setTipoExtrato(extrato.getTipoExtrato());

        return lancamento;
    }
}
