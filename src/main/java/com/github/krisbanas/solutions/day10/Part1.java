package com.github.krisbanas.solutions;

import com.github.krisbanas.util.FileHelper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Not my best solution...
 */
public class Part1 {

    private final List<Integer> adapterList;
    private int oneCounter;
    private int threeCounter;

    public Part1() {
        adapterList = FileHelper.loadFileFromResources("Day10Input.txt")
                .stream()
                .map(Integer::valueOf)
                .sorted()
                .collect(Collectors.toList());
    }

    public int solve() {
        if (adapterList.get(0) == 1) oneCounter++;
        if (adapterList.get(0) == 3) threeCounter++;

        for (int i = 0; i < adapterList.size() - 1; i++) {
            var diff = adapterList.get(i + 1) - adapterList.get(i);
            if (diff == 1) oneCounter++;
            if (diff == 3) threeCounter++;
        }

        threeCounter++;

        System.out.println("One counter: " + oneCounter + "Three counter: " + threeCounter);

        return oneCounter * threeCounter;
    }

}
