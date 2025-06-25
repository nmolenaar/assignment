package io.molenaar;

import io.molenaar.client.api.NotFoundException;
import io.molenaar.client.model.*;
import io.molenaar.module.wordFrequencyAnalyzer.WordFrequencyAnalyzerControllerImpl;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WordFrequencyAnalyzerControllerImplTest {

    private WordFrequencyAnalyzerControllerImpl controller;

    @BeforeEach
    void setUp() {
        controller = new WordFrequencyAnalyzerControllerImpl();
    }

    @Test
    void testGetFrequencyOfWord() throws NotFoundException {
        FrequencyWordRequestDTO request = new FrequencyWordRequestDTO()
                .text("Hello world, hello again")
                .word("hello");

        Response response = controller.getFrequencyOfWord(request, null);
        assertEquals(200, response.getStatus());

        FrequencyWordResponseDTO dto = (FrequencyWordResponseDTO) response.getEntity();
        assertEquals("hello", dto.getWord());
        assertEquals(2, dto.getFrequency());
    }

    @Test
    void testGetHighestFrequency() throws NotFoundException {
        FrequencyHighestRequestDTO request = new FrequencyHighestRequestDTO()
                .text("This is a test. This test is only a test.");

        Response response = controller.getHighestFrequency(request, null);
        assertEquals(200, response.getStatus());

        FrequencyHighestResponseDTO dto = (FrequencyHighestResponseDTO) response.getEntity();
        assertEquals(3, dto.getFrequency()); // "test" appears 3 times
    }

    @Test
    void testGetTopNWords() throws NotFoundException {
        FrequencyTopRequestDTO request = new FrequencyTopRequestDTO()
                .text("The quick brown fox jumps over the lazy dog. The quick brown fox.")
                .n(3);

        Response response = controller.getTopNWords(request, null);
        assertEquals(200, response.getStatus());

        @SuppressWarnings("unchecked")
        var dtoList = (java.util.List<FrequencyTopResponseInnerDTO>) response.getEntity();
        assertEquals(3, dtoList.size());

        // Example check for top word
        assertEquals("the", dtoList.get(0).getWord().toLowerCase());
        assertTrue(dtoList.get(0).getFrequency() >= dtoList.get(1).getFrequency());
    }
}
