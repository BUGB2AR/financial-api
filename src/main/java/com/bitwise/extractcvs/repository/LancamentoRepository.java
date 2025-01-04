package com.bitwise.extractcvs.repository;

import com.bitwise.extractcvs.model.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, String> {

    @Query("SELECT l FROM Lancamento l WHERE l.dataLancamento BETWEEN :startDate AND :endDate")
    List<Lancamento> findByPeriod(LocalDate startDate, LocalDate endDate);

    @Query("SELECT SUM(CASE " +
            "WHEN l.historico LIKE '%liberado%' OR l.historico LIKE '%recebido%' OR l.historico LIKE '%Aplicação%' THEN ABS(l.valor) " +
            "ELSE l.valor END) FROM Lancamento l WHERE l.dataLancamento BETWEEN :startDate AND :endDate AND " +
            "(l.historico LIKE '%liberado%' OR l.historico LIKE '%recebido%' OR l.historico LIKE '%Aplicação%')")
    BigDecimal findTotalEntradaByPeriod(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("SELECT SUM(CASE " +
            "WHEN l.historico LIKE '%Resgate%' OR l.historico LIKE '%Compra no débito%' OR l.historico LIKE '%Pagamento efetuado%' OR " +
            "l.historico LIKE '%Pix enviado%' OR l.historico LIKE '%Pagamento de Convênio%' OR l.historico LIKE '%Compra no crédito%' THEN ABS(l.valor) " +
            "ELSE 0 END) FROM Lancamento l WHERE l.dataLancamento BETWEEN :startDate AND :endDate AND " +
            "(l.historico LIKE '%Resgate%' OR l.historico LIKE '%Compra no débito%' OR l.historico LIKE '%Pagamento efetuado%' OR " +
            "l.historico LIKE '%Pix enviado%' OR l.historico LIKE '%Pagamento de Convênio%' OR l.historico LIKE '%Compra no crédito%')")
    BigDecimal findTotalSaidaByPeriod(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("SELECT SUM(CASE " +
            "WHEN l.historico LIKE '%liberado%' OR l.historico LIKE '%recebido%' OR l.historico LIKE '%Aplicação%' THEN ABS(l.valor) " +
            "ELSE l.valor END) FROM Lancamento l WHERE FUNCTION('YEAR', l.dataLancamento) = :year AND " +
            "(l.historico LIKE '%liberado%' OR l.historico LIKE '%recebido%' OR l.historico LIKE '%Aplicação%')")
    BigDecimal findTotalEntradaByYear(
            @Param("year") int year);

    @Query("SELECT SUM(CASE " +
            "WHEN l.historico LIKE '%Resgate%' OR l.historico LIKE '%Compra no débito%' OR l.historico LIKE '%Pagamento efetuado%' OR " +
            "l.historico LIKE '%Pix enviado%' OR l.historico LIKE '%Pagamento de Convênio%' OR l.historico LIKE '%Compra no crédito%' THEN ABS(l.valor) " +
            "ELSE 0 END) FROM Lancamento l WHERE FUNCTION('YEAR', l.dataLancamento) = :year AND " +
            "(l.historico LIKE '%Resgate%' OR l.historico LIKE '%Compra no débito%' OR l.historico LIKE '%Pagamento efetuado%' OR " +
            "l.historico LIKE '%Pix enviado%' OR l.historico LIKE '%Pagamento de Convênio%' OR l.historico LIKE '%Compra no crédito%')")
    BigDecimal findTotalSaidaByYear(
            @Param("year") int year);

    @Query("SELECT SUM(CASE " +
            "WHEN l.historico LIKE '%Aplicação%' THEN ABS(l.valor) " +
            "ELSE 0 END) FROM Lancamento l WHERE l.dataLancamento BETWEEN :startDate AND :endDate AND " +
            "l.historico LIKE '%Aplicação%'")
    BigDecimal findTotalAplicacaoByPeriod(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);


    @Query("SELECT SUM(CASE " +
            "WHEN l.historico LIKE '%Aplicação%' THEN ABS(l.valor) " +
            "ELSE 0 END) FROM Lancamento l WHERE FUNCTION('YEAR', l.dataLancamento) = :year AND " +
            "l.historico LIKE '%Aplicação%'")
    BigDecimal findTotalAplicacaoByYear(
            @Param("year") int year);


}

