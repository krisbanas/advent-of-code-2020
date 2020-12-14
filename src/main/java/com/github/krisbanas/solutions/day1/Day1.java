package com.github.krisbanas.solutions.day1;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.github.krisbanas.util.FileHelper.loadFileFromResources;

public class Day1 {

    public static final String INPUT = "Day1Input.txt";
    public static final int TOTAL_SUM = 2020;
    private final List<Integer> numbers;

    public Day1() {
        numbers = extractNumbersFromInputFile();
    }

    public int solve() {
        return numbers.stream()
                .map(this::solveForSumEqualToIterant)
                .filter(Predicate.not(List::isEmpty))
                .map(x -> x.stream().reduce(1, (a, b) -> a * b))
                .findFirst()
                .orElseThrow();
    }

    private List<Integer> extractNumbersFromInputFile() {
        return loadFileFromResources(INPUT).stream()
                .map(Integer::valueOf)
                .collect(Collectors.toList());
    }

    private List<Integer> solveForSumEqualToIterant(Integer iterant) {
        int sumForIterant = TOTAL_SUM - iterant;
        List<Integer> complements = createAListOfComplementsForIterant(sumForIterant);
        return optionalSolutionForGivenIterant(iterant, sumForIterant, complements);
    }

    private List<Integer> createAListOfComplementsForIterant(int sumForIterant) {
        return numbers.stream()
                .map(x -> sumForIterant - x)
                .collect(Collectors.toList());
    }

    private List<Integer> optionalSolutionForGivenIterant(Integer iterant, int sumForIterant, List<Integer> complements) {
        for (int target : numbers) {
            if (complements.contains(target)) {
                System.out.println("I've found: " + target + " " + (sumForIterant - target) + " " + iterant);
                return List.of(target, sumForIterant - target, iterant);
            }
        }
        return Collections.emptyList();
    }
}
