package com.github.krisbanas.solutions.day16;

import com.github.krisbanas.util.FileHelper;

import java.util.List;

import static com.github.krisbanas.solutions.day16.DataExtractor.*;

public class Part1 {

    private static final String EMPTY_LINE = "\r\n\r\n";

    List<Range> ranges;
    List<Integer> myTicket;
    List<List<Integer>> nearbyTickets;

    public Part1() {
        String[] ticketAreas = FileHelper.loadFileFromResourcesAsString("Day16Input.txt").split(EMPTY_LINE);

        ranges = extractValidRangesFromTicketArea(ticketAreas[0]);
        myTicket = extractMyTicket(ticketAreas[1]);
        nearbyTickets = extractNearbyTickets(ticketAreas[2]);
    }

    public int solve() {
        int scanningErrorRate = 0;
        for (var ticket : nearbyTickets) {
            for (Integer ticketEntry : ticket) {
                if (ticketEntryNotInRange(ticketEntry)) scanningErrorRate += ticketEntry;
            }
        }
        return scanningErrorRate;
    }

    private boolean ticketEntryNotInRange(int ticketValue) {
        return ranges.stream()
                .filter(x -> x.contains(ticketValue))
                .findAny().isEmpty();
    }
}
