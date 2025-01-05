package com.bitwise.extractcvs.service.impl;

import com.bitwise.extractcvs.enums.InstituicaoBancaria;
import com.bitwise.extractcvs.enums.TipoExtrato;
import com.bitwise.extractcvs.model.Extrato;
import com.bitwise.extractcvs.strategy.interfaces.FileParserStrategy;
import com.bitwise.extractcvs.utils.Utils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class FileService {

    private final ExtratoService extratoService;
    private final InstituicaoBancariaService instituicaoBancariaService;

    private final FileParserStrategy<MultipartFile, Extrato> csvParserStrategy;
    private final FileParserStrategy<MultipartFile, Extrato> excelParserStrategy;

    public FileService(ExtratoService extratoService,
                       InstituicaoBancariaService instituicaoBancariaService,
                       @Qualifier("csvFileParserStrategy") FileParserStrategy<MultipartFile, Extrato> csvParserStrategy,
                       @Qualifier("excelFileParserStrategy") FileParserStrategy<MultipartFile, Extrato> excelParserStrategy) {

        this.extratoService = extratoService;
        this.instituicaoBancariaService = instituicaoBancariaService;
        this.csvParserStrategy = csvParserStrategy;
        this.excelParserStrategy = excelParserStrategy;
    }

    public List<Extrato> process(MultipartFile file) throws IOException {
        String fileType = Utils.getFileExtension(file.getOriginalFilename());
        FileParserStrategy<MultipartFile, Extrato> parserStrategy;

        if ("csv".equalsIgnoreCase(fileType)) {
            parserStrategy = csvParserStrategy;
        } else if ("xlsx".equalsIgnoreCase(fileType)) {
            parserStrategy = excelParserStrategy;
        } else {
            throw new IllegalArgumentException("Tipo de arquivo não suportado: " + fileType);
        }

        List<Extrato> extratoList = parserStrategy.parse(file);
        TipoExtrato tipoExtrato = extratoService.determinarTipoExtrato(file.getOriginalFilename());
        InstituicaoBancaria instituicaoBancaria = instituicaoBancariaService.determinarInstituicaoBancaria(file.getOriginalFilename());

        assert extratoList != null;

        for (Extrato extrato : extratoList) {
            extrato.setUuid(UUID.randomUUID());
            extrato.setTipoExtrato(tipoExtrato);
            extrato.setInstituicaoBancaria(instituicaoBancaria);
        }

        return extratoList;
    }
}
