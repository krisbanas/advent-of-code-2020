package com.github.krisbanas.solutions.day9;

import com.github.krisbanas.util.FileHelper;

import java.util.List;
import java.util.stream.Collectors;

public class Part2 {

    private final List<Long> inputList;
    public static final long TARGET_NUMBER = 167829540L;

    public Part2() {
        inputList = FileHelper.loadFileFromResources("Day9Input.txt")
                .stream().map(Long::valueOf)
                .collect(Collectors.toList());
    }

    public long solve() {
        int leftPointer = 0;
        int rightPointer = 1;

        while (rightPointer < inputList.size() - 1) {
            long sum;
            sum = findSumOfSublist(leftPointer, rightPointer);

            if (sum < TARGET_NUMBER) rightPointer++;
            else if (sum > TARGET_NUMBER) leftPointer++;
            else return sumOfMinAndMaxOfRange(leftPointer, rightPointer);
        }

        return 0;
    }

    private long findSumOfSublist(int leftPointer, int rightPointer) {
        long sum = 0;
        for (int i = leftPointer; i <= rightPointer; i++) {
            sum += inputList.get(i);
        }
        return sum;
    }

    private long sumOfMinAndMaxOfRange(int leftPointer, int rightPointer) {
        var solutionList = inputList.subList(leftPointer, rightPointer);
        var min = solutionList.stream().min(Long::compare).orElse(0L);
        var max = solutionList.stream().max(Long::compare).orElse(Long.MAX_VALUE);
        return min + max;
    }
}
