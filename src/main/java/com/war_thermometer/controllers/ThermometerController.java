package com.war_thermometer.controllers;
import com.war_thermometer.services.IndexCalculationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ThermometerController {

    private final IndexCalculationService indexService;

    public ThermometerController(IndexCalculationService indexService) {
        this.indexService = indexService;
    }

    @GetMapping("/api/current-index")
    public WarIndexResponse getCurrentIndex() {
        return new WarIndexResponse(
                indexService.calculateWarIndex(),
                "World War III Threat Level"
        );
    }

    @GetMapping("/api/methodology")
    public MethodologyResponse getMethodology() {
        return new MethodologyResponse(
                "World War Thermometer Index Methodology v2",
                """
                The index is calculated based on three weighted factors with capped contributions:
                
                1. Active Armed Conflicts (40% weight, max 40 points)
                   - Max occurrences: 50 conflicts
                   - Contribution: (actual_conflicts / 50) * 40
                   
                2. Nuclear War Mentions (30% weight, max 30 points)
                   - Max occurrences: 200 mentions
                   - Contribution: (actual_mentions / 200) * 30
                   
                3. International Sanctions (20% weight, max 20 points)
                   - Max occurrences: 100 sanctions
                   - Contribution: (actual_sanctions / 100) * 20
                   
                Total possible maximum: 90 points (reserving 10 points for future metrics)
                
                Data sources:
                - Armed Conflicts: ACLED API (simulated in this demo)
                - News Analysis: NewsAPI
                - Sanctions: OECD API (simulated in this demo)
                
                Threat levels:
                0-30: Low tension
                31-60: Moderate tension
                61-90: High tension
                91-100: Critical (currently not achievable)
                """
        );
    }

    // Records para responses imutáveis
    record WarIndexResponse(double currentIndex, String description) {}
    record MethodologyResponse(String title, String description) {}
}