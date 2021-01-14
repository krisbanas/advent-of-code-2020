package com.github.krisbanas.solutions.day17;

import com.github.krisbanas.util.FileHelper;

public class Part2 {

    public static final int NUMBER_OF_ROUNDS = 6;
    private final int PROBLEM_SIZE;

    private final String[][][][] hypercube;
    private boolean[][][][] activeNextHypercube;
    private final HypercubeInitializer hypercubeInitializer;

    public Part2() {
        var inputString = FileHelper.loadFileFromResourcesAsString("Day17Input.txt");
        hypercubeInitializer = new HypercubeInitializer(NUMBER_OF_ROUNDS, inputString);
        this.hypercube = hypercubeInitializer.createInputHypercube();
        this.PROBLEM_SIZE = hypercube.length;
    }

    public int solve() {
        this.activeNextHypercube = hypercubeInitializer.createChangesHypercube();
        int iterationCounter = NUMBER_OF_ROUNDS;
        while (iterationCounter-- > 0) {
            markState();
            swapState();
        }
        return countActiveElements();
    }

    private int countActiveElements() {
        int counter = 0;
        for (int i = 0; i < PROBLEM_SIZE; i++) {
            for (int j = 0; j < PROBLEM_SIZE; j++) {
                for (int k = 0; k < PROBLEM_SIZE; k++) {
                    for (int l = 0; l < PROBLEM_SIZE; l++) {
                        if (hypercube[i][j][k][l].equals("#")) counter++;
                    }
                }
            }
        }
        return counter;
    }

    private void markState() {
        for (int i = 0; i < PROBLEM_SIZE; i++) {
            for (int j = 0; j < PROBLEM_SIZE; j++) {
                for (int k = 0; k < PROBLEM_SIZE; k++) {
                    for (int l = 0; l < PROBLEM_SIZE; l++) {
                        int numberOfAdjacentActiveFields = countAdjacentActiveFields(new HyperPoint(i, j, k, l));
                        markStateForElement(new HyperPoint(i, j, k, l), numberOfAdjacentActiveFields);
                    }
                }
            }
        }
    }

    private void swapState() {
        for (int i = 0; i < PROBLEM_SIZE; i++) {
            for (int j = 0; j < PROBLEM_SIZE; j++) {
                for (int k = 0; k < PROBLEM_SIZE; k++) {
                    for (int l = 0; l < PROBLEM_SIZE; l++) {
                        hypercube[i][j][k][l] = activeNextHypercube[i][j][k][l] ? "#" : ".";
                    }
                }
            }
        }
    }

    private void markStateForElement(HyperPoint p, int numberOfAdjacentActiveFields) {
        if (elementIsActive(p)) {
            activeNextHypercube[p.getX()][p.getY()][p.getZ()][p.getD()] = (numberOfAdjacentActiveFields == 2 || numberOfAdjacentActiveFields == 3);
        } else
            activeNextHypercube[p.getX()][p.getY()][p.getZ()][p.getD()] = numberOfAdjacentActiveFields == 3;
    }

    private boolean elementIsActive(HyperPoint p) {
        return hypercube[p.getX()][p.getY()][p.getZ()][p.getD()].equals("#");
    }

    private int countAdjacentActiveFields(HyperPoint p) {
        int counter = 0;
        for (int i = p.getX() - 1; i <= p.getX() + 1; i++) {
            for (int j = p.getY() - 1; j <= p.getY() + 1; j++) {
                for (int k = p.getZ() - 1; k <= p.getZ() + 1; k++) {
                    for (int l = p.getD() - 1; l <= p.getD() + 1; l++) {
                        try {
                            String value = hypercube[i][j][k][l];
                            if (value.equals("#")) counter++;
                        } catch (ArrayIndexOutOfBoundsException ignored) {
                        }
                    }
                }
            }
        }
        if (hypercube[p.getX()][p.getY()][p.getZ()][p.getD()].equals("#")) counter--;
        return counter;
    }
}
