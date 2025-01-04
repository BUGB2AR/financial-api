package com.bitwise.extractcvs.controller;

import com.bitwise.extractcvs.model.Lancamento;
import com.bitwise.extractcvs.repository.LancamentoRepository;
import com.bitwise.extractcvs.response.TotalEntradaResponse;
import com.bitwise.extractcvs.response.TotalSaidaResponse;
import com.bitwise.extractcvs.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/lancamentos")
public class LancamentoController {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @GetMapping
    public ResponseEntity<List<Lancamento>> getAllLancamentos() {
        List<Lancamento> lancamentos = lancamentoRepository.findAll();
        return new ResponseEntity<>(lancamentos, HttpStatus.OK);
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<Lancamento>> findByPeriod(
            @RequestParam String startDate, @RequestParam String endDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        List<Lancamento> lancamentos = lancamentoRepository.findByPeriod(start, end);
        return new ResponseEntity<>(lancamentos, HttpStatus.OK);
    }

    @GetMapping("/ano")
    public ResponseEntity<TotalEntradaResponse> findTotalEntradaByYear(@RequestParam int year) {

        BigDecimal totalEntrada = lancamentoRepository.findTotalEntradaByYear(year);
        String totalEntradaFormatted = Utils.formatToCurrency(totalEntrada);

        TotalEntradaResponse response = new TotalEntradaResponse(totalEntradaFormatted);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/saida/ano")
    public ResponseEntity<TotalSaidaResponse> findTotalSaidaByYear(@RequestParam int year) {

        BigDecimal totalSaida = lancamentoRepository.findTotalSaidaByYear(year);
        String totalSaidaFormatted = Utils.formatToCurrency(totalSaida);

        TotalSaidaResponse response = new TotalSaidaResponse(totalSaidaFormatted);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/entrada/periodo")
    public ResponseEntity<TotalEntradaResponse> findTotalEntradaByPeriod(
            @RequestParam String startDate, @RequestParam String endDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        BigDecimal totalEntrada = lancamentoRepository.findTotalEntradaByPeriod(start, end);
        String totalEntradaFormatted = Utils.formatToCurrency(totalEntrada);

        TotalEntradaResponse response = new TotalEntradaResponse(totalEntradaFormatted);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/saida/periodo")
    public ResponseEntity<TotalSaidaResponse> findTotalSaidaByPeriod(
            @RequestParam String startDate, @RequestParam String endDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        BigDecimal totalSaida = lancamentoRepository.findTotalSaidaByPeriod(start, end);
        String totalSaidaFormatted = Utils.formatToCurrency(totalSaida);

        TotalSaidaResponse response = new TotalSaidaResponse(totalSaidaFormatted);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/investimento/periodo/aplicacao")
    public ResponseEntity<TotalEntradaResponse> findTotalAplicacaoByPeriod(
            @RequestParam String startDate, @RequestParam String endDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        BigDecimal totalAplicacao = lancamentoRepository.findTotalAplicacaoByPeriod(start, end);
        String totalAplicacaoFormatted = Utils.formatToCurrency(totalAplicacao);

        TotalEntradaResponse response = new TotalEntradaResponse(totalAplicacaoFormatted);

        return ResponseEntity.ok(response);
    }
    @GetMapping("/investimento/ano/aplicacao")
    public ResponseEntity<TotalEntradaResponse> findTotalAplicacaoByYear(@RequestParam int year) {

        BigDecimal totalAplicacao = lancamentoRepository.findTotalAplicacaoByYear(year);
        String totalAplicacaoFormatted = Utils.formatToCurrency(totalAplicacao);

        TotalEntradaResponse response = new TotalEntradaResponse(totalAplicacaoFormatted);

        return ResponseEntity.ok(response);
    }


}
