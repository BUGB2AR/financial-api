package com.bitwise.extractcvs.controller;

import com.bitwise.extractcvs.converter.Converter;
import com.bitwise.extractcvs.model.Extrato;
import com.bitwise.extractcvs.model.Lancamento;
import com.bitwise.extractcvs.service.impl.FileService;
import com.bitwise.extractcvs.service.impl.LancamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CvsExportController {

    private final LancamentoService lancamentoService;
    private final FileService excelService;

    public CvsExportController(LancamentoService lancamentoService, FileService excelService) {
        this.lancamentoService = lancamentoService;
        this.excelService = excelService;
    }

    @PostMapping(value = "/importar-extrato", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<Extrato>> extract(@RequestParam MultipartFile file) throws IOException {
        List<Extrato> extratos = excelService.process(file);

        if (extratos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(extratos);
        }

        for (Extrato extrato : extratos) {
            Lancamento lancamento = Converter.convertExtratoToLancamento(extrato);

            if (extrato.getUuid() != null) {
                lancamento.setUuid(extrato.getUuid().toString());
            }

            lancamentoService.createLancamento(lancamento);
        }

        return ResponseEntity.ok(extratos);
    }

}
