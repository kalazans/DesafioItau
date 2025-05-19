package br.com.desafioItau.Itau.dto;

import java.util.DoubleSummaryStatistics;

public record EstatisticaDTO(long count, double sum, double min, double max, double average) {
    public EstatisticaDTO(DoubleSummaryStatistics doubleSummaryStatistics){
        this(doubleSummaryStatistics.getCount(),
                doubleSummaryStatistics.getSum(),
                doubleSummaryStatistics.getMin() ==Double.POSITIVE_INFINITY?0.0:doubleSummaryStatistics.getMin(),
                doubleSummaryStatistics.getMax() == Double.NEGATIVE_INFINITY?0.0:doubleSummaryStatistics.getMax(),
                doubleSummaryStatistics.getAverage());
    }
}
