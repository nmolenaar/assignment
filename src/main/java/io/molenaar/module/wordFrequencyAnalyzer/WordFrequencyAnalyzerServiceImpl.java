package io.molenaar.module.wordFrequencyAnalyzer;

import io.molenaar.assignment.WordFrequency;
import io.molenaar.module.wordFrequencyAnalyzer.domain.WordFrequencyImp;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class WordFrequencyAnalyzerServiceImpl implements WordFrequencyAnalyzerService {

    private static final Pattern WORD_PATTERN = Pattern.compile("[a-zA-Z]+");

    @Override
    public int calculateHighestFrequency(String text) {
        return Optional.ofNullable(text)
                .map(this::buildFrequencyMap)
                .flatMap(freqMap -> freqMap.values().stream().max(Integer::compareTo))
                .orElse(0);
    }

    @Override
    public int calculateFrequencyForWord(String text, String word) {
        if (word == null || word.isEmpty()) return 0;

        return Optional.ofNullable(text)
                .map(this::buildFrequencyMap)
                .map(freqMap -> freqMap.getOrDefault(word.toLowerCase(), 0))
                .orElse(0);
    }

    @Override
    public List<WordFrequency> calculateMostFrequentNWords(String text, int n) {
        if (n <= 0) return Collections.emptyList();

        return Optional.ofNullable(text)
                .map(this::buildFrequencyMap)
                .map(freqMap -> freqMap.entrySet().stream()
                        .sorted(Comparator.comparingInt(Map.Entry<String, Integer>::getValue).reversed()
                                .thenComparing(Map.Entry::getKey))
                        .limit(n)
                        .map(entry -> (WordFrequency) new WordFrequencyImp().setWord(entry.getKey()).setFrequency(entry.getValue()))
                        .toList()
                )
                .orElse(Collections.emptyList());
    }

    private Map<String, Integer> buildFrequencyMap(String text) {
        return WORD_PATTERN.matcher(text).results()
                .map(matchResult -> matchResult.group().toLowerCase())
                .collect(Collectors.groupingBy(w -> w, Collectors.summingInt(w -> 1)));
    }
}
