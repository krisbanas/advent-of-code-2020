package com.github.krisbanas.solutions.day14;

import com.github.krisbanas.util.FileHelper;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Part2 {

    private static final int MASK_SIZE = 36;
    private final List<Command> commandList;
    private final Map<Long, Long> memoryValuesList = new HashMap<>();

    public Part2() {
        commandList = Arrays.stream(FileHelper.loadFileFromResourcesAsString("Day14Input.txt")
                .split("mask = "))
                .filter(Predicate.not(String::isBlank))
                .map(this::mapToCommandFromInput)
                .collect(Collectors.toList());
    }

    public Long solve() {
        for (var command : commandList) {
            processCommand(command);
        }
        return memoryValuesList.values().stream().reduce(0L, Long::sum);
    }

    private void processCommand(Command command) {
        var maskSplitArray = command.getMask().split("");
        for (var memModif : command.getMemoryModificationList()) {
            processMemoryModification(maskSplitArray, memModif);
        }
    }

    private void processMemoryModification(String[] maskSplitArray, MemoryModification memModif) {
        String[] addressAsBinaryArray = mapToBinaryArray(memModif.getAddress());
        handleZerosAndOnes(maskSplitArray, addressAsBinaryArray);
        List<String[]> toSave = processXesIntoListOfAddresses(addressAsBinaryArray);
        fillTheMemoryModificationList(memModif, toSave);
    }

    private void fillTheMemoryModificationList(MemoryModification memModif, List<String[]> toSave) {
        for (var addressString : toSave) {
            var address = Long.parseLong(String.join("", addressString), 2);
            memoryValuesList.put(address, memModif.getValue());
        }
    }

    private List<String[]> processXesIntoListOfAddresses(String[] addressAsBinaryArray) {
        List<String[]> toSave;
        toSave = new ArrayList<>();
        List<String[]> toProcess = new ArrayList<>();
        toProcess.add(addressAsBinaryArray);
        while (!toProcess.isEmpty()) {
            String addressAsBinaryString = String.join("", toProcess.remove(0));
            if (!addressAsBinaryString.contains("X")) {
                toSave.add(addressAsBinaryString.split(""));
            } else {
                toProcess.add(addressAsBinaryString.replaceFirst("X", "0").split(""));
                toProcess.add(addressAsBinaryString.replaceFirst("X", "1").split(""));
            }
        }
        return toSave;
    }

    private void handleZerosAndOnes(String[] maskSplitArray, String[] addressAsBinaryArray) {
        for (int i = 0; i < MASK_SIZE; i++) {
            if (maskSplitArray[i].equals("1"))
                addressAsBinaryArray[i] = "1";
            if (maskSplitArray[i].equals("X"))
                addressAsBinaryArray[i] = "X";
        }
    }

    private String[] mapToBinaryArray(long memModifAddress) {
        String[] zeroPaddedAddressBinary = new String[MASK_SIZE];
        var addressAsBinaryArray = Long.toBinaryString(memModifAddress).split("");
        for (int i = 0; i < MASK_SIZE; i++) {
            if (i < addressAsBinaryArray.length) {
                zeroPaddedAddressBinary[MASK_SIZE - i - 1] = addressAsBinaryArray[addressAsBinaryArray.length - 1 - i];
            } else {
                zeroPaddedAddressBinary[MASK_SIZE - i - 1] = "0";
            }
        }
        return zeroPaddedAddressBinary;
    }

    private Command mapToCommandFromInput(String commandString) {
        String[] commandSplit = commandString.split(System.lineSeparator());
        var mask = commandSplit[0];
        var memoryModificationList = Arrays.stream(commandSplit, 1, commandSplit.length)
                .map(this::mapToMemoryModification)
                .collect(Collectors.toList());
        return new Command(mask, memoryModificationList);
    }

    private MemoryModification mapToMemoryModification(String memoryModificationString) {
        var address = Integer.parseInt(memoryModificationString.substring(4).split("] =")[0]);
        var value = Integer.parseInt(memoryModificationString.split(" = ")[1]);
        return new MemoryModification(address, value);
    }
}
