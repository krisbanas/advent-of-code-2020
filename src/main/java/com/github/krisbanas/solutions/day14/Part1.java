package com.github.krisbanas.solutions.day14;

import com.github.krisbanas.util.FileHelper;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Part1 {

    private static final int MASK_SIZE = 36;
    private final List<Command> commandList;
    private final Map<Long, Long> memoryValuesList = new HashMap<>();

    public Part1() {
        commandList = Arrays.stream(FileHelper.loadFileFromResourcesAsString("Day14Input.txt")
                .split("mask = "))
                .filter(Predicate.not(String::isBlank))
                .map(this::mapToCommandFromInput)
                .collect(Collectors.toList());
    }

    public Long solve() {
        commandList.forEach(command -> {
            var maskSplitArray = command.getMask().split("");
            processCommand(command, maskSplitArray);
        });
        return memoryValuesList.values().stream().reduce(0L, Long::sum);
    }

    private void processCommand(Command command, String[] maskSplitArray) {
        for (var memModif : command.getMemoryModificationList()) {
            String[] modificationBinary = mapMemoryModificationToBinaryStringArray(memModif);
            List<String> modificationMasked = createMaskedModification(maskSplitArray, modificationBinary);
            long valueMaskedAsDecimal = convertMaskedModificationToDecimalValue(modificationMasked);
            memoryValuesList.put(memModif.getAddress(), valueMaskedAsDecimal);
        }
    }

    private String[] mapMemoryModificationToBinaryStringArray(MemoryModification memModif) {
        String[] template;
        template = generateTemplate();
        var valueAsBinaryArray = Long.toBinaryString(memModif.getValue()).split("");
        var valueAsBinaryArrayLen = valueAsBinaryArray.length;
        for (int i = 0; i < valueAsBinaryArrayLen; i++) {
            template[valueAsBinaryArrayLen - i - 1] = valueAsBinaryArray[i];
        }
        return template;
    }

    private String[] generateTemplate() {
        return Stream.iterate("0", x -> x).limit(36).map(String::valueOf).toArray(String[]::new);
    }

    private List<String> createMaskedModification(String[] maskSplitArray, String[] modificationBinary) {
        IntStream.range(0, MASK_SIZE)
                .filter(i -> maskSplitArray[MASK_SIZE - i - 1].matches("[0-1]"))
                .forEach(i -> modificationBinary[i] = maskSplitArray[MASK_SIZE - i - 1]);

        addZeroPadding(modificationBinary);

        var maskedModificationAsList = Arrays.stream(modificationBinary).collect(Collectors.toList());
        Collections.reverse(maskedModificationAsList);
        return maskedModificationAsList;
    }

    private long convertMaskedModificationToDecimalValue(List<String> modificationMasked) {
        var valueMaskedAsBinaryString = String.join("", modificationMasked.toArray(String[]::new));
        return Long.parseLong(valueMaskedAsBinaryString, 2);
    }

    private void addZeroPadding(String[] modificationBinary) {
        int i = MASK_SIZE - 1;
        while (modificationBinary[i].equals("0")) {
            modificationBinary[i] = "";
            i--;
        }
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
