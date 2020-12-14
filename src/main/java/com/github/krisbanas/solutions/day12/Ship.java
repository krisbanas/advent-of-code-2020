package com.github.krisbanas.solutions.day12;

class Ship {
    int x = 0;
    int y = 0;
    int angle = 0;

    void move(Direction direction, int value) {
        switch (direction) {
            case E -> x += value;
            case W -> x -= value;
            case N -> y += value;
            case S -> y -= value;
            case L -> angle += value;
            case R -> angle -= value;
            case F -> this.moveForward(value);
        }
        System.out.print("Direction: " + direction.name());
        System.out.printf(" Value: %3d ", value);
        System.out.printf(" X value: %3d", x);
        System.out.printf(" Y value: %3d", y);
        System.out.println(", angle value: " + angle);
    }

    private void moveForward(int value) {
        x += value * Math.round(Math.cos(Math.toRadians(angle)));
        y += value * Math.round(Math.sin(Math.toRadians(angle)));
    }

    int localize() {
        return Math.abs(x) + Math.abs(y);
    }
}
