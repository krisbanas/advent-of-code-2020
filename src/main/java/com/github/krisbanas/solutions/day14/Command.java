package com.github.krisbanas.solutions.day14;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Command {
    private String mask;
    private List<MemoryModification> memoryModificationList;
}
