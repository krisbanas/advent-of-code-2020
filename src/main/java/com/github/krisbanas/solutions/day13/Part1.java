package com.github.krisbanas.solutions.day13;

import com.github.krisbanas.util.FileHelper;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Part1 {

    private final List<Integer> busList;
    private final int busDepartureTime;

    public Part1() {
        var inputList = FileHelper.loadFileFromResources("Day13Input.txt");
        busDepartureTime = Integer.parseInt(inputList.get(0));
        busList = Arrays.stream(inputList.get(1).split(","))
                .filter(x -> !x.equals("x"))
                .map(Integer::valueOf)
                .collect(Collectors.toList());
    }

    public int solve() {
        var moduloIdMap = busList.stream()
                .map(x -> new SimpleEntry<>(x, busDepartureTime % x))
                .peek(x -> x.setValue(x.getKey() - x.getValue()))
                .collect(Collectors.toList());

        var min = moduloIdMap.stream().min(java.util.Map.Entry.comparingByValue()).get();
        return min.getValue() * min.getKey();
    }
}
