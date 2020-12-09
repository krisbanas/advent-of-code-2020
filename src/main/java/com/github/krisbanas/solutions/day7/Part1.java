package com.github.krisbanas.solutions.day7;

import com.github.krisbanas.util.FileHelper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Part1 {
    private final List<Bag> inputList;

    public Part1() {
        inputList = FileHelper.loadFileFromResources("Day7Input.txt").stream().map(this::createBagEntry).collect(Collectors.toList());
    }

    public long solve() {
        int count = 0;
        List<Bag> possibleToCarry = inputList.stream()
                .filter(x -> x.colour.equals("shiny gold"))
                .collect(Collectors.toList());

        while (!possibleToCarry.isEmpty()) {
            count++;
            var feasibleBag = possibleToCarry.get(0);
            inputList.stream().filter(x -> x.contains(feasibleBag.colour) && !x.isAccountedFor()).forEach(
                    x -> {
                        x.accountFor();
                        possibleToCarry.add(x);
                    }
            );
            possibleToCarry.remove(feasibleBag);
        }
        return count - 1L;
    }

    private Bag createBagEntry(String s) {
        var splitEntry = s.split("bags contain");

        List<String> bagList = splitEntry[1].contains("[0-9]")
                ? Collections.emptyList()
                : Arrays.stream(splitEntry[1].split(","))
                .map(x -> x.replaceAll("[0-9]|bag.?|\\.", "").trim())
                .collect(Collectors.toList());

        return new Bag(splitEntry[0].trim(), bagList);
    }

    private class Bag {

        Bag(String colour, List<String> containedBags) {
            this.colour = colour;
            this.containedBags = containedBags;
        }

        private final String colour;
        private final List<String> containedBags;
        private boolean isAccountedFor;

        public boolean contains(String bagColour) {
            return containedBags.stream().anyMatch(x -> x.equals(bagColour));
        }

        public boolean isAccountedFor() {
            return isAccountedFor;
        }

        public void accountFor() {
            isAccountedFor = true;
        }
    }
}
