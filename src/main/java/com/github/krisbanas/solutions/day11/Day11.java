package com.github.krisbanas.solutions.day11;

import com.github.krisbanas.util.FileHelper;

import java.util.Arrays;


public class Day11 {

    public static final String NEWLINE_SYMBOL = System.lineSeparator();
    private final String[][] seatList;
    private final boolean[][] markedForChange;
    private boolean seatsStateChanged;

    public Day11() {
        seatList = Arrays.stream(FileHelper.loadFileFromResourcesAsString("Day11Input.txt")
                .split(NEWLINE_SYMBOL))
                .map(line -> line.split(""))
                .toArray(String[][]::new);
        markedForChange = new boolean[seatList.length][seatList[0].length];
        for (var line : markedForChange) line = new boolean[seatList[0].length];
    }

    public int solve() {
        do {
            printTable();
            seatsStateChanged = false;
            cleanMarkedTable();
            markingSweep();
            applyingSweep();
            System.out.println(calculateOccupiedSeats());
        } while (seatsStateChanged);
        return calculateOccupiedSeats();
    }

    private void printTable() {
        for (int i = 0; i < seatList.length; i++) {
            for (int j = 0; j < seatList[0].length; j++) {
                System.out.print(seatList[i][j]);
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }

    private void cleanMarkedTable() {
        for (int i = 0; i < seatList.length; i++) {
            for (int j = 0; j < seatList[0].length; j++) {
                markedForChange[i][j] = false;
            }
        }
    }

    private void markingSweep() {
        for (int i = 0; i < seatList.length; i++) {
            for (int j = 0; j < seatList[0].length; j++) {
                var adjacentSeatsTaken = getNumberOfVisibleSeatsTaken(i, j);
                markChanges(i, j, adjacentSeatsTaken);
            }
        }
    }

    private void applyingSweep() {
        for (int i = 0; i < seatList.length; i++) {
            for (int j = 0; j < seatList[0].length; j++) {
                applyIfMarked(i, j);
            }
        }
    }

    private int calculateOccupiedSeats() {
        var counter = 0;
        for (int i = 0; i < seatList.length; i++) {
            for (int j = 0; j < seatList[0].length; j++) {
                if (seatList[i][j].equals("#")) counter++;
            }
        }
        return counter;
    }

    private void applyIfMarked(int i, int j) {
        if (markedForChange[i][j]) {
            if (seatList[i][j].equals("#")) {
                seatList[i][j] = "L";
                seatsStateChanged = true;
            } else if (seatList[i][j].equals("L")) {
                seatList[i][j] = "#";
                seatsStateChanged = true;
            }
        }
    }

    private int getNumberOfVisibleSeatsTaken(int row, int column) {
        var counter = 0;
        Coordinates coordinates;

        for (Direction direction : Direction.values()) {
            coordinates = CoordinatesService.createCoordinatesFor(row, column, direction);
            while (coordinatesInRange(coordinates)) {
                var stateOfSeat = seatList[coordinates.getX()][coordinates.getY()];
                if (stateOfSeat.equals("#")) {
                    counter++;
                    break;
                } else if (stateOfSeat.equals(".")) {
                    CoordinatesService.moveCoordinates(coordinates, direction);
                } else break;
            }
        }

        return counter;
    }

    private boolean coordinatesInRange(Coordinates coordinates) {
        int i = coordinates.getX();
        int j = coordinates.getY();

        return i >= 0 && i < seatList.length && j >= 0 && j < seatList[0].length;
    }

    private void markChanges(int i, int j, int adjacentSeatsTaken) {
        if (seatList[i][j].equals("L") && adjacentSeatsTaken == 0) {
            markedForChange[i][j] = true;
            seatsStateChanged = true;
        }
        if (seatList[i][j].equals("#") && adjacentSeatsTaken >= 5) {
            markedForChange[i][j] = true;
            seatsStateChanged = true;
        }
    }
}
