package com.example.stringmatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class DnaMatcherService {

    private static final Pattern DNA = Pattern.compile("[ACGT]+");
    private static final Pattern WHITESPACE = Pattern.compile("\\s+");

    /** Elimina spatiile / liniile noi (apar des la copy-paste) si trece totul la litere mari. */
    public String normalize(String input) {
        return input == null ? "" : WHITESPACE.matcher(input).replaceAll("").toUpperCase();
    }

    /**
     * Returneaza pozitiile (0-based) ale tuturor aparitiilor secventei in sir,
     * inclusiv cele care se suprapun (ex: "AA" in "AAA" -> 0, 1).
     * Foloseste un lookahead regex: (?=SECVENTA) potriveste fiecare pozitie de start.
     */
    public List<Integer> findAll(String sequence, String pattern) {
        if (!DNA.matcher(pattern).matches()) {
            throw new IllegalArgumentException("Secventa cautata poate contine doar literele A, C, G, T.");
        }
        if (!sequence.isEmpty() && !DNA.matcher(sequence).matches()) {
            throw new IllegalArgumentException("Sirul ADN poate contine doar literele A, C, G, T.");
        }
        Matcher m = Pattern.compile("(?=" + Pattern.quote(pattern) + ")").matcher(sequence);
        List<Integer> positions = new ArrayList<>();
        while (m.find()) {
            positions.add(m.start());
        }
        return positions;
    }
}
