package com.github.krisbanas.solutions;

import com.github.krisbanas.util.FileHelper;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day5 {

    private final List<String> inputList;

    public Day5() {
        inputList = FileHelper.loadFileFromResources("Day5Input.txt");
    }

    public long solve1() {
        return inputList.stream()
                .mapToInt(entry -> calculateSeatId(
                        findRow(entry.substring(0, 7)),
                        findColumn(entry.substring(7))))
                .max()
                .getAsInt();
    }

    public long solve2() {
        List<Integer> passportSeatIds = inputList.stream()
                .map(entry -> calculateSeatId(
                        findRow(entry.substring(0, 7)),
                        findColumn(entry.substring(7))))
                .collect(Collectors.toList());

        List<Integer> availableSeats = IntStream.rangeClosed(0, 8 * 127 + 7)
                .boxed().collect(Collectors.toList());

        return availableSeats.stream()
                .filter(x -> !passportSeatIds.contains(x))
                .filter(x -> x < 900 && x > 100)
                .findAny()
                .get();
    }

    private int calculateSeatId(int row, int column) {
        return row * 8 + column;
    }

    private int findRow(String directions) {
        return findSeat(0, 127, directions);
    }

    private int findColumn(String directions) {
        return findSeat(0, 7, directions);
    }

    private int findSeat(int lower, int higher, String directions) {
        if (directions.length() == 0) return higher;
        if (directions.startsWith("F") || directions.startsWith("L")) {
            return findSeat(lower, halveDistance(lower, higher), directions.substring(1));
        } else {
            return findSeat(halveDistance(lower, higher) + 1, higher, directions.substring(1));
        }
    }

    private int halveDistance(int lower, int higher) {
        return (higher - lower) / 2 + lower;
    }
}

