package com.github.krisbanas.solutions;

import com.github.krisbanas.util.FileHelper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Not my best solution...
 */
public class Day8 {

    private final Instruction[] instructions;
    private int programCounter;
    private int acc;

    public Day8() {
        instructions = FileHelper.loadFileFromResources("Day8Input.txt")
                .stream()
                .map(Instruction::new)
                .toArray(Instruction[]::new);
    }

    public int solve() {
        Set<Integer> nopIndexes = findIndices(InstructionType.NOP);
        Set<Integer> jmpIndexes = findIndices(InstructionType.JMP);
        try {
            searchWithJmpsChangedToNops(jmpIndexes);
            searchWithNopsChangedToJmps(nopIndexes);
        } catch (IndexOutOfBoundsException ex) {
            return acc;
        }
        return 0;
    }

    private void searchWithNopsChangedToJmps(Set<Integer> nopIndexes) {
        for (int nopIndex : nopIndexes) {
            acc = 0;
            programCounter = 0;
            var alteredList = Arrays.stream(instructions).map(Instruction::new).toArray(Instruction[]::new);
            alteredList[nopIndex].setInstructionType(InstructionType.JMP);
            solveSingle(alteredList);
        }
    }

    private void searchWithJmpsChangedToNops(Set<Integer> jmpIndexes) {
        for (int jmpIndex : jmpIndexes) {
            acc = 0;
            programCounter = 0;
            var alteredList = Arrays.stream(instructions).map(Instruction::new).toArray(Instruction[]::new);
            alteredList[jmpIndex].setInstructionType(InstructionType.NOP);

            solveSingle(alteredList);
        }
    }

    private Set<Integer> findIndices(InstructionType instructionType) {
        Set<Integer> indices = new HashSet<>();
        for (int i = 0; i < instructions.length; i++)
            if (instructions[i].instructionType == instructionType) indices.add(i);
        return indices;
    }

    private void solveSingle(Instruction[] singleSetOfInstructions) {
        while (!singleSetOfInstructions[programCounter].isExecuted()) {
            var currentInstruction = singleSetOfInstructions[programCounter];
            currentInstruction.execute();
            switch (currentInstruction.instructionType) {
                case ACC -> executeIncrementationOperand(currentInstruction);
                case JMP -> programCounter += currentInstruction.getInstructionValue();
                case NOP -> programCounter++;
            }
        }
    }

    private void executeIncrementationOperand(Instruction currentInstruction) {
        acc += currentInstruction.getInstructionValue();
        programCounter++;
    }

    static class Instruction {
        private boolean isExecuted;
        private InstructionType instructionType;
        private final int instructionValue;

        Instruction(Instruction template) {
            this.instructionType = template.getInstructionType();
            this.instructionValue = template.getInstructionValue();
        }

        Instruction(String instructionString) {
            var instructionSplit = instructionString.split(" ");
            instructionType = InstructionType.valueOf(instructionSplit[0].toUpperCase());
            instructionValue = Integer.parseInt(instructionSplit[1]);
        }

        public void execute() {
            isExecuted = true;
        }

        public boolean isExecuted() {
            return isExecuted;
        }

        public void setInstructionType(InstructionType instructionType) {
            this.instructionType = instructionType;
        }

        public int getInstructionValue() {
            return instructionValue;
        }

        public InstructionType getInstructionType() {
            return instructionType;
        }
    }

    enum InstructionType {
        NOP, ACC, JMP
    }

}
