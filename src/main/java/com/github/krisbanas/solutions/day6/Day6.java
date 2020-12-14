package com.github.krisbanas.solutions.day6;

import com.github.krisbanas.util.FileHelper;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day6 {

    public static final String ANY_LETTER = "[a-z]";
    public static final String MARKER = "1";
    public static final String END_OF_LINE_MARKER = "\r\n";
    private final List<String> inputList;
    public static final String LEAVE_ONLY_UNIQUE_REGEX_TEMPLATE = "([a-z])\\1{%d}";

    public Day6() {
        inputList = Arrays.stream(FileHelper.loadFileFromResourcesAsString("Day6Input.txt")
                .split("\r\n\r\n"))
                .collect(Collectors.toList());
    }

    public long solve1() {
        return inputList.stream()
                .map(x -> x.replaceAll("\r\n", ""))
                .map(x -> Arrays.stream(x.split("")).collect(Collectors.toSet()))
                .mapToInt(Set::size)
                .sum();
    }

    public long solve2() {
        return inputList.stream()
                .mapToInt(this::numberOfQuestionsEveryoneAnswered)
                .sum();
    }

    private int numberOfQuestionsEveryoneAnswered(String oneGroupInfo) {
        return oneGroupInfo.replaceAll(END_OF_LINE_MARKER, "")
                .chars()
                .mapToObj(x -> Character.toString((char) x))
                .sorted()
                .reduce("", (x, y) -> x + y)
                .replaceAll(getRegex(oneGroupInfo), MARKER)
                .replaceAll(ANY_LETTER, "")
                .length();
    }

    private String getRegex(String oneGroupInfo) {
        int numberOfPeopleInGroup = Arrays.stream(oneGroupInfo.split(END_OF_LINE_MARKER)).toArray().length;
        return String.format(LEAVE_ONLY_UNIQUE_REGEX_TEMPLATE, numberOfPeopleInGroup - 1);
    }
}
