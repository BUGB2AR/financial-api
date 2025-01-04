package com.bitwise.extractcvs.service;

import com.bitwise.extractcvs.enums.InstituicaoBancaria;
import com.bitwise.extractcvs.enums.TipoExtrato;
import com.bitwise.extractcvs.model.Extrato;
import com.bitwise.extractcvs.utils.Utils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileService {

    private final ExtratoService extratoService;
    private final InstituicaoBancariaService instituicaoBancariaService;

    public FileService(ExtratoService extratoService, InstituicaoBancariaService instituicaoBancariaService) {
        this.extratoService = extratoService;
        this.instituicaoBancariaService = instituicaoBancariaService;
    }

    public List<Extrato> process(MultipartFile file) {
        List<Extrato> extratoList = extract(file);

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

    private List<Extrato> extract(MultipartFile file) {
        try {
            String fileType = Utils.getFileExtension(file.getOriginalFilename());
            if ("csv".equalsIgnoreCase(fileType)) {
                return parseCSV(file);
            } else if ("xlsx".equalsIgnoreCase(fileType)) {
                return parseExcel(file);
            } else {
                throw new IllegalArgumentException("Tipo de arquivo não suportado: " + fileType);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public List<Extrato> parseCSV(MultipartFile file) throws IOException {
        List<Extrato> extratoes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine || line.trim().isEmpty()) {
                    isFirstLine = false;
                    continue;
                }

                String[] values = line.split(";");
                if (values.length < 4) {
                    Utils.logInvalidLine("Linha inválida ou incompleta", line);
                    continue;
                }

                Extrato extrato = parseExtratoFromCSV(values, line);
                if (extrato != null) {
                    extratoes.add(extrato);
                }
            }
        }
        return extratoes;
    }

    private Extrato parseExtratoFromCSV(String[] values, String line) {
        try {
            Extrato extrato = new Extrato();
            extrato.setDataLancamento(values[0].trim());
            extrato.setHistorico(values[1].trim());
            extrato.setDescricao(values[2].trim());

            BigDecimal valor = Utils.parseBigDecimal(values[3].trim(), line, "Valor");
            if (valor != null) {
                extrato.setValor(valor);
            }

            BigDecimal saldo = Utils.parseBigDecimal(values[3].trim(), line, "Saldo");
            if (saldo != null) {
                extrato.setSaldo(saldo);
            }

            return extrato;
        } catch (Exception e) {
            Utils.logInvalidLine("Erro ao processar linha", line);
            return null;
        }
    }


    public List<Extrato> parseExcel(MultipartFile file) throws IOException {
        List<Extrato> extratoes = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for (int index = 1; index <= sheet.getPhysicalNumberOfRows(); index++) {
            Row row = sheet.getRow(index);

            if (row != null) {
                Extrato extrato = parseExtratoFromExcelRow(row);
                if (extrato != null) {
                    extratoes.add(extrato);
                }
            }
        }
        return extratoes;
    }

    private Extrato parseExtratoFromExcelRow(Row row) {
        try {
            Extrato extrato = new Extrato();

            extrato.setDataLancamento(validateCellAsString(row.getCell(0)));
            extrato.setHistorico(validateCellAsString(row.getCell(1)));
            extrato.setDescricao(validateCellAsString(row.getCell(2)));
            extrato.setValor(validateCellAsBigDecimal(row.getCell(3)));
            extrato.setSaldo(validateCellAsBigDecimal(row.getCell(4)));

            return extrato;
        } catch (Exception e) {
            System.out.println("Erro ao processar linha do Excel: " + e.getMessage());
            return null;
        }
    }

    private String validateCellAsString(Cell cell) {
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case BLANK -> "";
            default -> throw new IllegalArgumentException("Tipo de célula não suportado: " + cell.getCellType());
        };
    }

    private BigDecimal validateCellAsBigDecimal(Cell cell) {
        return switch (cell.getCellType()) {
            case STRING -> {
                try {
                    yield new BigDecimal(cell.getStringCellValue().trim());
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Valor da célula STRING não é um número válido: " + cell.getStringCellValue());
                }
            }
            case NUMERIC -> BigDecimal.valueOf(cell.getNumericCellValue());
            case BOOLEAN -> throw new IllegalArgumentException("Tipo BOOLEAN não é suportado para BigDecimal.");
            case BLANK -> BigDecimal.ZERO;
            default -> throw new IllegalArgumentException("Tipo de célula não suportado: " + cell.getCellType());
        };
    }
}
