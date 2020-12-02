package com.github.krisbanas.solutions;

import com.github.krisbanas.util.FileHelper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Day2 {

    public static final String SPLITTING_REGEX = "-|:? ";
    public static final String NOT_GIVEN_LETTER = "[^%s]";
    private final List<PasswordEntry> passwordEntries;

    public Day2() {
        passwordEntries = FileHelper.loadFileFromResources("Day2Input.txt")
                .stream()
                .map(x -> x.split(SPLITTING_REGEX))
                .map(this::createPasswordEntryFromStringArray)
                .collect(Collectors.toList());
    }

    public long solvePartOne() {
        return passwordEntries.stream()
                .peek(this::removeLettersOtherThanGiven)
                .filter(checkIfLengthInBoundaries())
                .count();
    }

    public long solvePartTwo() {
        return passwordEntries.stream()
                .filter(this::checkIfExactlyOneLetterMatches)
                .count();
    }

    private PasswordEntry createPasswordEntryFromStringArray(String[] x) {
        return new PasswordEntry(Integer.parseInt(x[0]), Integer.parseInt(x[1]), x[2], x[3]);
    }

    private void removeLettersOtherThanGiven(PasswordEntry entry) {
        String regex = String.format(NOT_GIVEN_LETTER, entry.letter);
        entry.passwordString = entry.passwordString.replaceAll(regex, "");
    }

    private Predicate<PasswordEntry> checkIfLengthInBoundaries() {
        return x -> x.passwordString.length() <= x.maximumCount && x.passwordString.length() >= x.minimumCount;
    }

    private boolean checkIfExactlyOneLetterMatches(PasswordEntry pw) {
        return checkIfBoundaryMatches(pw, BoundaryType.LOWER) ^ checkIfBoundaryMatches(pw, BoundaryType.UPPER);
    }

    private boolean checkIfBoundaryMatches(PasswordEntry pw, BoundaryType boundaryType) {
        var letterPosition = boundaryType == BoundaryType.LOWER ? pw.minimumCount : pw.maximumCount;

        boolean isWithinBound = pw.passwordString.length() >= letterPosition;
        char expectedLetter = pw.letter.charAt(0);
        char testedLetter = pw.passwordString.charAt(letterPosition - 1);
        boolean lettersMatch = testedLetter == expectedLetter;

        return isWithinBound && lettersMatch;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    private static class PasswordEntry {

        int minimumCount;
        int maximumCount;
        String letter;
        String passwordString;

    }

    private enum BoundaryType {
        UPPER, LOWER
    }
}
