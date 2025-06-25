package io.molenaar;

import io.molenaar.assignment.WordFrequency;
import io.molenaar.module.wordFrequencyAnalyzer.WordFrequencyAnalyzerServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordFrequencyAnalyzerServiceImplTest {

    private final WordFrequencyAnalyzerServiceImpl service = new WordFrequencyAnalyzerServiceImpl();

    @Test
    void testCalculateHighestFrequency() {
        int frequency = service.calculateHighestFrequency("The sun shines over the lake");
        assertEquals(2, frequency); // "the" appears twice
    }

    @Test
    void testCalculateFrequencyForWord() {
        int frequency = service.calculateFrequencyForWord("The sun shines over the lake", "the");
        assertEquals(2, frequency);

        assertEquals(0, service.calculateFrequencyForWord("Hello world", "absent"));
        assertEquals(0, service.calculateFrequencyForWord(null, "word"));
    }

    @Test
    void testCalculateMostFrequentNWords() {
        List<WordFrequency> topWords = service.calculateMostFrequentNWords("The sun shines over the lake", 3);
        assertEquals(3, topWords.size());
        assertEquals("the", topWords.get(0).getWord());
        assertEquals(2, topWords.get(0).getFrequency());

        // check order for equal frequencies (alphabetical)
        List<WordFrequency> result = service.calculateMostFrequentNWords("a b b c c", 2);
        assertEquals("b", result.get(0).getWord());
        assertEquals("c", result.get(1).getWord());
    }
}
