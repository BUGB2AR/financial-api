package com.bitwise.extractcvs.strategy.impl;

import com.bitwise.extractcvs.model.Extrato;
import com.bitwise.extractcvs.strategy.interfaces.FileParserStrategy;
import com.bitwise.extractcvs.utils.Utils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@Qualifier("csvFileParserStrategy")
public class CSVFileParserStrategy implements FileParserStrategy<MultipartFile, Extrato> {

    @Override
    public List<Extrato> parse(MultipartFile file) throws IOException {
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
}
