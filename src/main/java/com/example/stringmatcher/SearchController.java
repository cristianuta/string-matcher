package com.example.stringmatcher;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SearchController {

    public record SearchRequest(String sequence, String pattern) {}

    /** sequence = sirul normalizat (fara spatii), pe care se raporteaza pozitiile. */
    public record SearchResponse(String sequence, String pattern, int count, List<Integer> positions) {}

    private final DnaMatcherService service;

    public SearchController(DnaMatcherService service) {
        this.service = service;
    }

    @PostMapping("/search")
    public SearchResponse search(@RequestBody SearchRequest request) {
        String sequence = service.normalize(request.sequence());
        String pattern = service.normalize(request.pattern());
        List<Integer> positions = service.findAll(sequence, pattern);
        return new SearchResponse(sequence, pattern, positions.size(), positions);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> badRequest(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }
}
