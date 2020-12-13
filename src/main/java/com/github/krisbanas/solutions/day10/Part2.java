package com.github.krisbanas.solutions.day10;

import com.github.krisbanas.util.FileHelper;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Part2 {

    private final List<Integer> adapterList;
    private final Map<Integer, Long> waysOfConnecting;

    public Part2() {
        adapterList = FileHelper.loadFileFromResources("Day10Input.txt")
                .stream()
                .map(Integer::valueOf)
                .collect(Collectors.toList());
        waysOfConnecting = new HashMap<>();
        waysOfConnecting.put(0, 1L);
        adapterList.add(0);
        Collections.sort(adapterList);
        adapterList.add(adapterList.get(adapterList.size() - 1) + 3);
    }

    public long solve() {
        for (int joltValue : adapterList) {
            countNumberOfWaysForJoltValue(joltValue);
        }
        return waysOfConnecting.get(adapterList.get(adapterList.size() - 1));
    }

    private void countNumberOfWaysForJoltValue(int joltValue) {
        if (joltValue == 0) return;
        var waysOfConnectingForCurrent =
                waysOfConnecting.getOrDefault(joltValue - 3, 0L) +
                        waysOfConnecting.getOrDefault(joltValue - 2, 0L) +
                        waysOfConnecting.getOrDefault(joltValue - 1, 0L);
        waysOfConnecting.put(joltValue, waysOfConnectingForCurrent);
    }
}
