package com.github.krisbanas.solutions.day12;

import com.github.krisbanas.util.FileHelper;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Part2 {

    private final List<Entry<Direction, Integer>> movementList;
    private final MapPoint waypoint = new MapPoint(10, 1);
    private final MapPoint ship = new MapPoint(0, 0);

    public Part2() {
        movementList = FileHelper.loadFileFromResources("Day12Input.txt")
                .stream()
                .map(entry -> new SimpleEntry<>(
                        Direction.valueOf(entry.substring(0, 1)),
                        Integer.valueOf(entry.substring(1))))
                .collect(Collectors.toList());
    }

    public int solve() {
        for (Entry<Direction, Integer> movement : movementList) {
            executeMove(movement.getKey(), movement.getValue());
            waypoint.localize();
            ship.localize();
        }
        return ship.localize();
    }

    private void executeMove(Direction direction, Integer value) {
        switch (direction) {
            case E -> waypoint.move(Direction.E, value);
            case W -> waypoint.move(Direction.W, value);
            case N -> waypoint.move(Direction.N, value);
            case S -> waypoint.move(Direction.S, value);
            case L -> spinWaypoint(value);
            case R -> spinWaypoint(-value);
            case F -> moveBoth(value);
        }
    }

    private void spinWaypoint(int value) {
        var x1 = waypoint.getX() - ship.getX();
        var y1 = waypoint.getY() - ship.getY();

        var r = Math.sqrt(x1 * x1 + y1 * y1);
        var alfa1 = Math.atan2(y1, x1);

        var alfa2 = alfa1 + Math.toRadians(value);

        var x2 = Math.round(r * Math.cos(alfa2));
        var y2 = Math.round(r * Math.sin(alfa2));

        waypoint.move(Direction.E, (int) (x2 - x1));
        waypoint.move(Direction.N, (int) (y2 - y1));
    }

    private void moveBoth(Integer value) {
        var xDistance = waypoint.getX() - ship.getX();
        var yDistance = waypoint.getY() - ship.getY();

        waypoint.move(Direction.E, value * xDistance);
        waypoint.move(Direction.N, value * yDistance);
        ship.move(Direction.E, value * xDistance);
        ship.move(Direction.N, value * yDistance);
    }
}
