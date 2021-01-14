package com.github.krisbanas.solutions.day17;

import com.github.krisbanas.util.FileHelper;

public class Part1 {

    public static final int NUMBER_OF_ROUNDS = 6;
    private final int PROBLEM_SIZE;

    private final String[][][] cube;
    private boolean[][][] activeNextCube;
    private final CubeInitializer cubeInitializer;

    public Part1() {
        var inputString = FileHelper.loadFileFromResourcesAsString("Day17Input.txt");
        cubeInitializer = new CubeInitializer(NUMBER_OF_ROUNDS, inputString);
        this.cube = cubeInitializer.createMiddleCube();
        this.PROBLEM_SIZE = cube.length;
    }

    public int solve() {
        this.activeNextCube = cubeInitializer.createChangesCube();
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
                    if (cube[i][j][k].equals("#")) counter++;
                }
            }
        }
        return counter;
    }

    private void markState() {
        for (int i = 0; i < PROBLEM_SIZE; i++) {
            for (int j = 0; j < PROBLEM_SIZE; j++) {
                for (int k = 0; k < PROBLEM_SIZE; k++) {
                    int numberOfAdjacentActiveFields = countAdjacentActiveFields(new Point(i, j, k));
                    markStateForElement(new Point(i, j, k), numberOfAdjacentActiveFields);
                }
            }
        }
    }

    private void swapState() {
        for (int i = 0; i < PROBLEM_SIZE; i++) {
            for (int j = 0; j < PROBLEM_SIZE; j++) {
                for (int k = 0; k < PROBLEM_SIZE; k++) {
                    cube[i][j][k] = activeNextCube[i][j][k] ? "#" : ".";
                }
            }
        }
    }

    private void markStateForElement(Point p, int numberOfAdjacentActiveFields) {
        if (elementIsActive(p)) {
            activeNextCube[p.getX()][p.getY()][p.getZ()] = (numberOfAdjacentActiveFields == 2 || numberOfAdjacentActiveFields == 3);
        } else
            activeNextCube[p.getX()][p.getY()][p.getZ()] = numberOfAdjacentActiveFields == 3;
    }

    private boolean elementIsActive(Point p) {
        return cube[p.getX()][p.getY()][p.getZ()].equals("#");
    }

    private int countAdjacentActiveFields(Point p) {
        int counter = 0;
        for (int i = p.getX() - 1; i <= p.getX() + 1; i++) {
            for (int j = p.getY() - 1; j <= p.getY() + 1; j++) {
                for (int k = p.getZ() - 1; k <= p.getZ() + 1; k++) {
                    try {
                        String value = cube[i][j][k];
                        if (value.equals("#")) counter++;
                    } catch (ArrayIndexOutOfBoundsException ignored) {
                    }
                }
            }
        }
        if (cube[p.getX()][p.getY()][p.getZ()].equals("#")) counter--;
        return counter;
    }
}
