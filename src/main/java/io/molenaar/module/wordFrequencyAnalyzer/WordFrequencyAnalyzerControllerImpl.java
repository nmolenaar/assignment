package io.molenaar.module.wordFrequencyAnalyzer;

import io.molenaar.client.api.ApiFrequencyService;
import io.molenaar.client.api.NotFoundException;
import io.molenaar.client.model.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;

public class WordFrequencyAnalyzerControllerImpl extends ApiFrequencyService {
    private final WordFrequencyAnalyzerService wordFrequencyAnalyzerService = new WordFrequencyAnalyzerServiceImpl();

    @Override
    public Response getFrequencyOfWord(FrequencyWordRequestDTO wordFrequencyRequestDTO, SecurityContext securityContext) throws NotFoundException {
        FrequencyWordResponseDTO response = new FrequencyWordResponseDTO();
        response.setFrequency(
                wordFrequencyAnalyzerService.calculateFrequencyForWord(
                        wordFrequencyRequestDTO.getText(),
                        wordFrequencyRequestDTO.getWord()
                )
        );

        response.setWord(wordFrequencyRequestDTO.getWord());

        return Response
                .ok()
                .entity(response)
                .build();
    }

    @Override
    public Response getHighestFrequency(FrequencyHighestRequestDTO textRequestDTO, SecurityContext securityContext) throws NotFoundException {

        FrequencyHighestResponseDTO response = new FrequencyHighestResponseDTO();
        response.setFrequency(
                wordFrequencyAnalyzerService.calculateHighestFrequency(textRequestDTO.getText())
        );

        return Response
                .ok()
                .entity(response)
                .build();
    }

    @Override
    public Response getTopNWords(FrequencyTopRequestDTO topNRequestDTO, SecurityContext securityContext) throws NotFoundException {
        List<FrequencyTopResponseInnerDTO> response = wordFrequencyAnalyzerService.calculateMostFrequentNWords(topNRequestDTO.getText(), topNRequestDTO.getN()).stream().map(
                result -> {
                    FrequencyTopResponseInnerDTO frequencyTopResponseInnerDTO = new FrequencyTopResponseInnerDTO();
                    frequencyTopResponseInnerDTO.setWord(result.getWord());
                    frequencyTopResponseInnerDTO.setFrequency(result.getFrequency());
                    return frequencyTopResponseInnerDTO;
                }
        ).toList();

        return Response
                .ok()
                .entity(response)
                .build();
    }
}
