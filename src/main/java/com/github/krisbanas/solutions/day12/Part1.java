package com.github.krisbanas.solutions.day12;

import com.github.krisbanas.util.FileHelper;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Part1 {

    private final List<Entry<Direction, Integer>> movementList;
    private final Ship ship = new Ship();

    public Part1() {
        movementList = FileHelper.loadFileFromResources("Day12Input.txt")
                .stream()
                .map(entry -> new SimpleEntry<>(
                        Direction.valueOf(entry.substring(0, 1)),
                        Integer.valueOf(entry.substring(1))))
                .collect(Collectors.toList());
    }

    public int solve() {
        for (Entry<Direction, Integer> movement : movementList) {
            ship.move(movement.getKey(), movement.getValue());
            ship.localize();
        }
        return ship.localize();
    }
}
