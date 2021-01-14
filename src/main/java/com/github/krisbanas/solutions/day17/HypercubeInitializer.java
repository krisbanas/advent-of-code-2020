package com.github.krisbanas.solutions.day17;

import java.util.Arrays;

public class HypercubeInitializer {

    private final int numberOfRounds;
    private final int problemSize;
    private final String inputString;

    HypercubeInitializer(int numberOfRounds, String inputString) {
        this.numberOfRounds = numberOfRounds;
        this.problemSize = inputString.split(System.lineSeparator())[0].length() + 2 * numberOfRounds;
        this.inputString = inputString;
    }

    boolean[][][][] createChangesHypercube() {
        boolean[][][][] changesCube = new boolean[problemSize][][][];
        for (int i = 0; i < problemSize; i++) {
            changesCube[i] = new boolean[problemSize][][];
            for (int j = 0; j < problemSize; j++) {
                changesCube[i][j] = new boolean[problemSize][];
                for (int k = 0; k < problemSize; k++) {
                    changesCube[i][j][k] = new boolean[problemSize];
                }
            }
        }
        return changesCube;
    }

    String[][][][] createInputHypercube() {
        String[][][][] hypercube = new String[problemSize][][][];

        for (int i = 0; i < problemSize; i++) {
            String[][][] cube;
            if (problemSize / 2 == i) {
                cube = createMiddleCube();
            } else cube = createEmptyCube();
            hypercube[i] = cube;
        }
        return hypercube;
    }

    private String[][][] createEmptyCube() {
        String[][][] emptyCube = new String[problemSize][][];
        for (int i = 0; i < problemSize; i++) {
            emptyCube[i] = new String[problemSize][];
            for (int j = 0; j < problemSize; j++) {
                emptyCube[i][j] = new String[problemSize];
                for (int k = 0; k < problemSize; k++) {
                    emptyCube[i][j][k] = "";
                }
            }
        }
        return emptyCube;
    }

    String[][][] createMiddleCube() {
        String[][] inputStringCharacters = Arrays.stream(inputString.split(System.lineSeparator()))
                .map(x -> x.split(""))
                .toArray(String[][]::new);

        String[][][] cube = new String[problemSize][][];

        for (int i = 0; i < problemSize; i++) {
            String[][] slice;
            if (problemSize / 2 == i) {
                slice = createMiddleSlice(inputStringCharacters);
            } else slice = createEmptySlice();
            cube[i] = slice;
        }

        return cube;
    }

    private String[][] createMiddleSlice(String[][] inputStringCharacters) {
        String[][] middleSlice = createEmptySlice();
        for (int i = numberOfRounds; i < problemSize - numberOfRounds; i++) {
            for (int j = numberOfRounds; j < problemSize - numberOfRounds; j++) {
                middleSlice[i][j] = inputStringCharacters[i - numberOfRounds][j - numberOfRounds];
            }
        }
        return middleSlice;
    }

    private String[][] createEmptySlice() {
        String[][] slice = new String[problemSize][problemSize];
        for (int i = 0; i < problemSize; i++) {
            for (int j = 0; j < problemSize; j++) {
                slice[i][j] = ".";
            }
        }
        return slice;
    }
}
