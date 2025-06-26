package com.war_thermometer.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class IndexCalculationService {

    private final NewsApiService newsApiService;

    @Value("${news.api.key}")
    private String newsApiKey;

    // Constantes com os limites máximos
    private static final double MAX_CONFLICT_POINTS = 40.0;
    private static final double MAX_NUCLEAR_MENTIONS_POINTS = 30.0;
    private static final double MAX_SANCTIONS_POINTS = 20.0;

    // Limites máximos de ocorrências reais
    private static final int MAX_CONFLICT_OCCURRENCES = 50;
    private static final int MAX_NUCLEAR_MENTIONS = 200;
    private static final int MAX_SANCTIONS = 100;

    // Fatores simulados
    private final AtomicInteger activeConflicts = new AtomicInteger(5);
    private final AtomicInteger internationalSanctions = new AtomicInteger(3);

    public IndexCalculationService(NewsApiService newsApiService) {
        this.newsApiService = newsApiService;
    }

    public double calculateWarIndex() {
        // Obter valores reais
        int conflictsCount = getActiveConflictsCount();
        int nuclearMentionsCount = getNuclearMentionsCount();
        int sanctionsCount = getInternationalSanctionsCount();

        // Calcular contribuições com limites
        double conflictContribution = calculateFactorContribution(
                conflictsCount,
                MAX_CONFLICT_OCCURRENCES,
                MAX_CONFLICT_POINTS
        );

        double nuclearContribution = calculateFactorContribution(
                nuclearMentionsCount,
                MAX_NUCLEAR_MENTIONS,
                MAX_NUCLEAR_MENTIONS_POINTS
        );

        double sanctionsContribution = calculateFactorContribution(
                sanctionsCount,
                MAX_SANCTIONS,
                MAX_SANCTIONS_POINTS
        );

        // Soma das contribuições
        double total = conflictContribution + nuclearContribution + sanctionsContribution;

        // Arredondar para 1 casa decimal
        return Math.round(total * 10.0) / 10.0;
    }

    // Método genérico para cálculo de contribuição
    private double calculateFactorContribution(int actualValue, int maxOccurrences, double maxPoints) {
        // Limitar ao máximo de ocorrências
        double normalizedValue = Math.min(actualValue, maxOccurrences);

        // Calcular proporção
        double ratio = normalizedValue / (double) maxOccurrences;

        // Retornar pontos proporcionais
        return ratio * maxPoints;
    }

    private int getActiveConflictsCount() {
        // Valor simulado (em produção viria de API real)
        return activeConflicts.get();
    }

    private int getInternationalSanctionsCount() {
        // Valor simulado
        return internationalSanctions.get();
    }

    private int getNuclearMentionsCount() {
        try {
            NewsApiService.NewsApiResponse response = newsApiService.getNews(
                    "\"nuclear war\" OR \"atomic war\"", // Consulta específica
                    newsApiKey,
                    "en"
            );
            // Aplicar limite máximo
            return Math.min(response.totalResults(), MAX_NUCLEAR_MENTIONS);
        } catch (Exception e) {
            return 30; // Valor padrão em caso de erro
        }
    }

    // Métodos de simulação para demonstração
    public void simulateConflictIncrease() {
        activeConflicts.incrementAndGet();
    }

    public void simulateSanctionsIncrease() {
        internationalSanctions.incrementAndGet();
    }
}