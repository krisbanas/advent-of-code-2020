package com.github.krisbanas.solutions.day3;

import com.github.krisbanas.util.FileHelper;

import java.util.List;

public class Day3 {

    private final List<String> landscape;
    private final List<int[]> parameterSetup;

    public Day3() {
        landscape = FileHelper.loadFileFromResources("Day3Input.txt");
        parameterSetup = List.of(
                new int[]{1, 1},
                new int[]{3, 1},
                new int[]{5, 1},
                new int[]{7, 1},
                new int[]{1, 2}
        );
    }

    public long solve() {
        return parameterSetup.stream()
                .map(parameters -> getTreeCountForSlopeParams(parameters[0], parameters[1]))
                .reduce(1L, (a, b) -> a * b);
    }

    private long getTreeCountForSlopeParams(int widthSkip, int heightSkip) {
        var count = 0;
        var height = landscape.size();
        var width = landscape.get(0).length();
        for (int i = 0; i < height; i++) {
            if (heightSkip * i >= height) break;
            if (landscape.get(heightSkip * i).charAt((widthSkip * i) % width) == '#') count++;
        }
        return count;
    }
}
