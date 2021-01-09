package com.github.krisbanas.solutions.day15;

import com.github.krisbanas.util.FileHelper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Solution {

    public static final int PROBLEM_SIZE = 30000000;
    List<Integer> spokenNumbers;
    Map<Integer, Integer> lastTurnForSpokenNumber = new HashMap<>();

    public Solution() {
        spokenNumbers = Arrays.stream(FileHelper.loadFileFromResourcesAsString("Day15Input.txt")
                .split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private void initialize() {
        for (int i = 0; i < spokenNumbers.size() - 1; i++) {
            lastTurnForSpokenNumber.put(spokenNumbers.get(i), i + 1);
        }
    }

    public int solve() {
        initialize();
        for (int i = spokenNumbers.size(); i < PROBLEM_SIZE; i++) {
            var x = spokenNumbers.get(i - 1);
            spokenNumbers.add(numberAlreadySpoken(x)
                    ? i - lastTurnForSpokenNumber.get(x)
                    : 0);
            lastTurnForSpokenNumber.put(x, i);
        }

        return spokenNumbers.get(PROBLEM_SIZE - 1);
    }

    private boolean numberAlreadySpoken(Integer x) {
        return lastTurnForSpokenNumber.containsKey(x);
    }
}
