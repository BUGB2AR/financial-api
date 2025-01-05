package com.bitwise.extractcvs.strategy.impl;

import com.bitwise.extractcvs.model.Extrato;
import com.bitwise.extractcvs.strategy.interfaces.FileParserStrategy;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@Qualifier("excelFileParserStrategy")
public class ExcelFileParserStrategy implements FileParserStrategy<MultipartFile, Extrato> {

    @Override
    public List<Extrato> parse(MultipartFile file) throws IOException {
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
