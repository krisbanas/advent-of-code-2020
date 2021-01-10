package com.github.krisbanas.solutions.day16;

import lombok.Getter;

@Getter
public class Range {

    private final int lowStart;
    private final int lowEnd;
    private final int highStart;
    private final int highEnd;

    public Range(String rangeDescriptor) {
        var descriptorSplit = rangeDescriptor.split(" ");
        var currentDescriptorPart = descriptorSplit[0];
        var currentDescriptorPartSplit = currentDescriptorPart.split("-");
        lowStart = Integer.parseInt(currentDescriptorPartSplit[0]);
        lowEnd = Integer.parseInt(currentDescriptorPartSplit[1]);

        currentDescriptorPart = descriptorSplit[2];
        currentDescriptorPartSplit = currentDescriptorPart.split("-");
        highStart = Integer.parseInt(currentDescriptorPartSplit[0]);
        highEnd = Integer.parseInt(currentDescriptorPartSplit[1]);
    }

    public boolean contains(int ticketValue) {
        return (ticketValue >= lowStart && ticketValue <= lowEnd) || (ticketValue >= highStart && ticketValue <= highEnd);
    }
}
