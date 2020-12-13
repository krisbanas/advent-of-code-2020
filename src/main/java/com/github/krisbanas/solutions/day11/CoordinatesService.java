package com.github.krisbanas.solutions.day11;

class CoordinatesService {

    public static void moveCoordinates(Coordinates coordinates, Direction direction) {
        switch (direction) {
            case LEFT -> coordinates.setX(coordinates.getX() - 1);
            case RIGHT -> coordinates.setX(coordinates.getX() + 1);
            case UP -> coordinates.setY(coordinates.getY() - 1);
            case DOWN -> coordinates.setY(coordinates.getY() + 1);
            case LEFT_UP -> {
                coordinates.setX(coordinates.getX() - 1);
                coordinates.setY(coordinates.getY() - 1);
            }
            case LEFT_DOWN -> {
                coordinates.setX(coordinates.getX() - 1);
                coordinates.setY(coordinates.getY() + 1);
            }
            case RIGHT_UP -> {
                coordinates.setX(coordinates.getX() + 1);
                coordinates.setY(coordinates.getY() - 1);
            }
            case RIGHT_DOWN -> {
                coordinates.setX(coordinates.getX() + 1);
                coordinates.setY(coordinates.getY() + 1);
            }
        }
    }

    public static Coordinates createCoordinatesFor(int x, int y, Direction direction) {
        return switch (direction) {
            case LEFT:
                yield new Coordinates(x - 1, y);
            case RIGHT:
                yield new Coordinates(x + 1, y);
            case UP:
                yield new Coordinates(x, y - 1);
            case DOWN:
                yield new Coordinates(x, y + 1);
            case LEFT_UP:
                yield new Coordinates(x - 1, y - 1);
            case LEFT_DOWN:
                yield new Coordinates(x - 1, y + 1);
            case RIGHT_UP:
                yield new Coordinates(x + 1, y - 1);
            case RIGHT_DOWN:
                yield new Coordinates(x + 1, y + 1);
        };
    }
}
