package com.github.krisbanas.solutions.day12;

class MapPoint {
    private int x;
    private int y;

    public MapPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    void move(Direction direction, int value) {
        switch (direction) {
            case E -> x += value;
            case W -> x -= value;
            case N -> y += value;
            case S -> y -= value;
        }
        System.out.print("Direction: " + direction.name());
        System.out.printf(" Value: %3d ", value);
        System.out.printf(" X value: %3d", x);
        System.out.printf(" Y value: %3d", y);
        System.out.println();
    }

    int localize() {
        return Math.abs(x) + Math.abs(y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
