package com.github.krisbanas.solutions;

import com.github.krisbanas.util.FileHelper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day4 {

    public static final String DOUBLE_NEWLINE = "\r\n\r\n";
    public static final String END_OF_LINE_CHARACTERS = "\r|\n";
    private final List<String> inputList;

    public Day4() {
        inputList = Arrays.stream(FileHelper
                .loadFileFromResourcesAsString("Day4Input.txt")
                .split(DOUBLE_NEWLINE))
                .map(s -> s.replaceAll(END_OF_LINE_CHARACTERS, " "))
                .collect(Collectors.toList());
    }

    public long solve() {
        return inputList.stream()
                .filter(entry -> new Passport(entry).validate())
                .count();
    }

    private class Passport {
        int byr;
        int iyr;
        int eyr;
        String hgt = "";
        String hcl = "";
        String ecl = "";
        String pid = "";
        int cid;

        Passport(String passportEntryString) {
            String[] raw = passportEntryString.split(" +");
            Arrays.stream(raw).filter(x -> x.matches("^byr.+")).findFirst().ifPresent(s -> byr = Integer.parseInt(s.substring(4)));
            Arrays.stream(raw).filter(x -> x.matches("^iyr.+")).findFirst().ifPresent(s -> iyr = Integer.parseInt(s.substring(4)));
            Arrays.stream(raw).filter(x -> x.matches("^eyr.+")).findFirst().ifPresent(s -> eyr = Integer.parseInt(s.substring(4)));
            Arrays.stream(raw).filter(x -> x.matches("^hgt.+")).findFirst().ifPresent(s -> hgt = s.substring(4));
            Arrays.stream(raw).filter(x -> x.matches("^hcl.+")).findFirst().ifPresent(s -> hcl = s.substring(4));
            Arrays.stream(raw).filter(x -> x.matches("^ecl.+")).findFirst().ifPresent(s -> ecl = s.substring(4));
            Arrays.stream(raw).filter(x -> x.matches("^pid.+")).findFirst().ifPresent(s -> pid = s.substring(4));
            Arrays.stream(raw).filter(x -> x.matches("^cid.+")).findFirst().ifPresent(s -> cid = Integer.parseInt(s.substring(4)));
        }

        public boolean validate() {
            return validateBirthYear()
                    && validateIssueYear()
                    && validateExpirationYear()
                    && validateHeight()
                    && validateHairColour()
                    && validateEyeColour()
                    && validatePersonalId();
        }

        private boolean validateBirthYear() {
            return byr >= 1920 && byr <= 2002;
        }

        private boolean validateIssueYear() {
            return iyr >= 2010 && iyr <= 2020;
        }

        private boolean validateExpirationYear() {
            return eyr >= 2020 && eyr <= 2030;
        }

        private boolean validateHeight() {
            if (hgt.length() <= 2) return false;
            int hgtAsInt = Integer.parseInt(hgt.substring(0, hgt.length() - 2));
            if (hgt.endsWith("cm")) {
                return hgtAsInt >= 150 && hgtAsInt <= 193;
            }
            if (hgt.endsWith("in")) {
                return hgtAsInt >= 59 && hgtAsInt <= 76;
            }
            return false;
        }

        private boolean validateHairColour() {
            return hcl.matches("#[0-9a-f]{6}");
        }

        private boolean validateEyeColour() {
            return ecl.matches("amb|blu|brn|gry|grn|hzl|oth");
        }

        private boolean validatePersonalId() {
            return pid.matches("[0-9]{9}");
        }
    }
}
