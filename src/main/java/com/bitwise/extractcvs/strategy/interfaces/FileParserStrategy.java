package com.bitwise.extractcvs.strategy.interfaces;

import org.springframework.data.repository.NoRepositoryBean;

import java.io.IOException;
import java.util.List;

@NoRepositoryBean
public interface FileParserStrategy<T, R> {
    List<R> parse(T file) throws IOException;
}

