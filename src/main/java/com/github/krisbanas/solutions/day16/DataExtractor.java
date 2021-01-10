package com.github.krisbanas.solutions.day16;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DataExtractor {
    static List<Range> extractValidRangesFromTicketArea(String ticketArea) {
        return Arrays.stream(ticketArea.split(System.lineSeparator()))
                .map(x -> x.replaceAll("[a-z:]", "").trim())
                .map(Range::new)
                .collect(Collectors.toList());
    }

    static List<Integer> extractMyTicket(String ticketArea) {
        String myTicketString = ticketArea.split(System.lineSeparator())[1];
        return createTicketFromTicketString(myTicketString);
    }

    static List<List<Integer>> extractNearbyTickets(String ticketArea) {
        var nearbyTicketsWithoutHeader = ticketArea.replace("nearby tickets:\r\n", "");
        var ticketStringList = nearbyTicketsWithoutHeader.split(System.lineSeparator());
        return Arrays.stream(ticketStringList).map(DataExtractor::createTicketFromTicketString).collect(Collectors.toList());
    }

    static List<Integer> createTicketFromTicketString(String myTicketString) {
        return Arrays.stream(myTicketString.split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());
    }
}
