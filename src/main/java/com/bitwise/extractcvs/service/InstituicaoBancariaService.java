package com.bitwise.extractcvs.service;

import com.bitwise.extractcvs.enums.InstituicaoBancaria;
import org.springframework.stereotype.Component;

@Component
public class InstituicaoBancariaService {

    public InstituicaoBancaria determinarInstituicaoBancaria(String name) {
        if (name.toLowerCase().contains("inter")) {
            return  InstituicaoBancaria.INTER;
        } else if (name.toLowerCase().contains("neon")) {
            return InstituicaoBancaria.NEON;
        } else if (name.toLowerCase().contains("nubank")) {
            return InstituicaoBancaria.NUBANK;
        } else if (name.toLowerCase().contains("bradesco")) {
            return InstituicaoBancaria.BRADESCO;
        }
        return null;
    }

}
