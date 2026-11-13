package com.example.stringmatcher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

class DnaMatcherServiceTest {

    private final DnaMatcherService service = new DnaMatcherService();

    @Test
    void findsAllOccurrences() {
        assertEquals(List.of(0, 6, 10), service.findAll("ACGTTTACGAACG", "ACG"));
    }

    @Test
    void findsOverlappingOccurrences() {
        assertEquals(List.of(0, 1, 2), service.findAll("AAAA", "AA"));
        assertEquals(List.of(0, 2), service.findAll("ATATA", "ATA"));
    }

    @Test
    void noOccurrences() {
        assertEquals(List.of(), service.findAll("AAAA", "G"));
    }

    @Test
    void normalizesWhitespaceAndCase() {
        assertEquals("ACGTAC", service.normalize(" acg\nt\r\n ac "));
    }

    @Test
    void rejectsInvalidLetters() {
        assertThrows(IllegalArgumentException.class, () -> service.findAll("ACGX", "AC"));
        assertThrows(IllegalArgumentException.class, () -> service.findAll("ACGT", "AN"));
        assertThrows(IllegalArgumentException.class, () -> service.findAll("ACGT", ""));
    }
}
