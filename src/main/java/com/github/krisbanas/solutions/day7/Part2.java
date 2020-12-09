package com.github.krisbanas.solutions.day7;

import com.github.krisbanas.util.FileHelper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Part2 {
    private final List<Bag> inputList;

    public Part2() {
        inputList = FileHelper.loadFileFromResources("Day7Input.txt").stream().map(this::createBagEntryWithNumbers).collect(Collectors.toList());
    }

    public long solve() {
        return countContainedBags("shiny gold");
    }

    private long countContainedBags(String colour) {
        return findBagOfColour(colour).getContainedBags().entrySet().stream()
                .map(x -> x.getValue() + x.getValue() * countContainedBags(x.getKey()))
                .mapToLong(x -> x).sum();
    }

    private Bag findBagOfColour(String colour) {
        return inputList.stream().filter(x -> x.getColour().equals(colour)).findFirst().orElse(new Bag("black", Collections.emptyMap()));
    }

    private Bag createBagEntryWithNumbers(String s) {
        var splitEntry = s.split("bags contain");

        Map<String, Integer> bagList = splitEntry[1].matches(".*other.*")
                ? Collections.emptyMap()
                : Arrays.stream(splitEntry[1].split(","))
                .map(x -> x.replaceAll("bag.?|\\.", "").trim())
                .collect(Collectors.toMap(x -> x.substring(1).trim(), x -> Integer.parseInt(x.substring(0, 1))));

        return new Bag(splitEntry[0].trim(), bagList);
    }

    private static class Bag {
        Bag(String colour, Map<String, Integer> containedBags) {
            this.colour = colour;
            this.containedBags = containedBags;
        }

        private final String colour;
        private final Map<String, Integer> containedBags;

        public Map<String, Integer> getContainedBags() {
            return containedBags;
        }

        public String getColour() {
            return colour;
        }
    }
}

