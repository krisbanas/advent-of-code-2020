package com.github.krisbanas.solutions.day9;

import com.github.krisbanas.util.FileHelper;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class Part1 {

    private final List<Long> inputList;
    private final Queue<Long> possibleElements = new ArrayDeque<>();
    public static final int PREAMBLE_SIZE = 25;

    public Part1() {
        inputList = FileHelper.loadFileFromResources("Day9Input.txt")
                .stream().map(Long::valueOf)
                .collect(Collectors.toList());

        for (int i = 0; i < PREAMBLE_SIZE; i++) {
            possibleElements.offer(inputList.get(i));
        }
    }

    public long solve() {
        for (int i = PREAMBLE_SIZE; i < inputList.size(); i++) {
            var currentSum = inputList.get(i);
            if (!canBeCreated(currentSum))
                return currentSum;
            possibleElements.poll();
            possibleElements.offer(currentSum);
        }


        return 0;
    }

    private boolean canBeCreated(Long currentSum) {
        for (long element : possibleElements) {
            if (possibleElements.stream().anyMatch(x -> x == currentSum - element)) return true;
        }
        return false;
    }

}
