package com.github.krisbanas.solutions.day16;

import com.github.krisbanas.util.FileHelper;

import java.util.*;
import java.util.stream.Collectors;

import static com.github.krisbanas.solutions.day16.DataExtractor.*;

public class Part2 {

    private static final String EMPTY_LINE = "\r\n\r\n";

    List<Range> ranges;
    List<Integer> myTicket;
    List<List<Integer>> nearbyTickets;

    public Part2() {
        String[] ticketAreas = FileHelper.loadFileFromResourcesAsString("Day16Input.txt").split(EMPTY_LINE);
        ranges = extractValidRangesFromTicketArea(ticketAreas[0]);
        myTicket = extractMyTicket(ticketAreas[1]);
        nearbyTickets = extractNearbyTickets(ticketAreas[2]);
    }

    public long solve() {
        List<List<Integer>> validTickets = findAllValidTickets();
        Map<Integer, List<Integer>> possibleCategoriesForTicketColumns = determinePossibleCategoriesForTicketColumns(validTickets);
        Map<Integer, Integer> ticketCategoriesMap = computeTicketCategoriesMap(possibleCategoriesForTicketColumns);


        return ticketCategoriesMap.entrySet().stream()
                .filter(x -> x.getKey() < 6)
                .mapToLong(Map.Entry::getValue)
                .map(x -> myTicket.get((int) x))
                .reduce(1, (x, y) -> x * y);

//        String[] info = FileHelper.loadFileFromResourcesAsString("Day16Input.txt").split(EMPTY_LINE)[0].split(System.lineSeparator());
//        String[] info2 = Arrays.stream(info)
//                .map(x -> x.replaceAll("[0-9]", ""))
//                .map(x -> x.replaceAll("-", ""))
//                .map(x -> x.replace("or", ""))
//                .toArray(String[]::new);
//
//        for (int i=0; i < myTicket.size(); i++) {
//            info2[i] = info2[i] + myTicket.get(ticketCategoriesMap.get(i));
//        }
//        var xd = ticketCategoriesMap.size();
//        return 0;
    }

    private List<List<Integer>> findAllValidTickets() {
        var validTickets = nearbyTickets.stream().filter(this::isTicketValid).collect(Collectors.toList());
        validTickets.add(myTicket);
        return validTickets;
    }

    private Map<Integer, Integer> computeTicketCategoriesMap(Map<Integer, List<Integer>> possibleCategoriesForTicketColumns) {
        Map<Integer, Integer> ticketCategoriesMap = new HashMap<>();
        while (!possibleCategoriesForTicketColumns.isEmpty()) {
            var ticketEntryIndex = findTheOnlyMatchingCategory(possibleCategoriesForTicketColumns);
            var categoryIndex = possibleCategoriesForTicketColumns.remove(ticketEntryIndex).get(0);
            ticketCategoriesMap.put(ticketEntryIndex, categoryIndex);
            possibleCategoriesForTicketColumns.forEach((key, value) -> value.remove(categoryIndex));
        }
        return ticketCategoriesMap;
    }

    private Integer findTheOnlyMatchingCategory(Map<Integer, List<Integer>> possibleCategoriesForTicketColumns) {
        for (var entry : possibleCategoriesForTicketColumns.entrySet()) {
            if (entry.getValue().size() == 1) return entry.getKey();
        }
        throw new RuntimeException("Something is wrong");
    }

    private Map<Integer, List<Integer>> determinePossibleCategoriesForTicketColumns(List<List<Integer>> validTickets) {
        int problemSize = myTicket.size();
        Map<Integer, List<Integer>> possibleCategoriesForTicketColumns = new HashMap<>();
        for (int i = 0; i < problemSize; i++) {
            int currentTicketEntryIndex = i;
            var ticketColumn = validTickets.stream().map(x -> x.get(currentTicketEntryIndex)).collect(Collectors.toList());

            for (int j = 0; j < problemSize; j++) {
                var category = ranges.get(j);
                var areAllSliceElementsInCurrentCategory = ticketColumn.stream().map(category::contains).reduce((x, y) -> x && y).orElse(false);
                if (areAllSliceElementsInCurrentCategory) {
                    if (!possibleCategoriesForTicketColumns.containsKey(i))
                        possibleCategoriesForTicketColumns.put(i, new ArrayList<>());
                    possibleCategoriesForTicketColumns.get(i).add(j);
                }
            }
        }
        return possibleCategoriesForTicketColumns;
    }

    private boolean isTicketValid(List<Integer> ticket) {
        for (Integer ticketEntry : ticket) {
            if (ticketEntryNotInRange(ticketEntry)) return false;
        }
        return true;
    }

    private boolean ticketEntryNotInRange(int ticketValue) {
        return ranges.stream()
                .filter(x -> x.contains(ticketValue))
                .findAny().isEmpty();
    }
}
