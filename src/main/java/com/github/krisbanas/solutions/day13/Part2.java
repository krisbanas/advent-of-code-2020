package com.github.krisbanas.solutions.day13;

import com.github.krisbanas.util.FileHelper;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Part2 {

    private final List<Congruence> congruenceList;

    public Part2() {
        List<String> busList = getBusListFromInput();

        congruenceList = IntStream.range(0, busList.size())
                .filter(i -> !busList.get(i).equals("x"))
                .mapToObj(i -> createCongruenceEntry(busList.get(i), i))
                .sorted(Comparator.comparingLong(Congruence::getModulo))
                .collect(Collectors.toList());

        Collections.reverse(congruenceList);
    }

    public long solve() {
        /*
         * Step 1: Calculate the big N - a product of all modulos in the system of equations.
         * Then, create a list of quotients m such that m = big N / modulo.
         */
        int numberOfEquations = congruenceList.size();
        long bigN = congruenceList.stream()
                .map(Congruence::getModulo)
                .reduce(1L, (x, y) -> x * y);

        List<Long> mList = congruenceList.stream()
                .map(x -> bigN / x.getModulo())
                .collect(Collectors.toList());

        /*
         * Step 2: For each pair (modulo, mValue) calculate Bezout Identity (u, v).
         */
        List<BezoutIdentity> bezoutIdentityList = IntStream.range(0, numberOfEquations)
                .mapToObj(i -> calculateBezoutIdentityFor(congruenceList.get(i).getModulo(), mList.get(i)))
                .collect(Collectors.toList());

        /*
         * Step 3: Create a list of e values such that e = v * m.
         */
        List<Long> eList = IntStream.range(0, numberOfEquations)
                .mapToObj(i -> bezoutIdentityList.get(i).getV() * mList.get(i))
                .collect(Collectors.toList());

        /*
         * Step 4: Calculate the sum of products offset * e.
         */
        long solution = IntStream.range(0, numberOfEquations)
                .mapToLong(i -> congruenceList.get(i).getOffset() * eList.get(i))
                .sum();

        /*
         * While Step 4 gave a valid solution, the solution is repeated by modulo big N.
         * The solution we are looking for is the first number greater than zero.
         */
        while (solution < 0) solution += bigN;
        return solution % bigN;
    }

    private List<String> getBusListFromInput() {
        return Arrays.stream(FileHelper
                .loadFileFromResources("Day13Input.txt")
                .get(1)
                .split(","))
                .collect(Collectors.toList());
    }

    private Congruence createCongruenceEntry(String butNumber, int i) {
        var modulo = Long.parseLong(butNumber);
        var offset = (modulo - (i % modulo)) % modulo;
        return new Congruence(offset, modulo);
    }

    private BezoutIdentity calculateBezoutIdentityFor(long greaterNumber, long smallerNumber) {

        long divider, reminder, x1, x2, y1, y2, u, v;

        /* Initialize the cache */
        x2 = 1;
        x1 = 0;
        y2 = 0;
        y1 = 1;

        while (smallerNumber > 0) {

            /* Step 1: Calculate the divider and reminder */
            divider = greaterNumber / smallerNumber;
            reminder = greaterNumber % smallerNumber;

            /* Step 2: Calculate the intermediate u and v */
            u = x2 - divider * x1;
            v = y2 - divider * y1;
            greaterNumber = smallerNumber;
            smallerNumber = reminder;

            /* Step 3: Cache the intermediate results */
            x2 = x1;
            x1 = u;
            y2 = y1;
            y1 = v;
        }

        /* Assign the final values from cache */
        u = x2;
        v = y2;

        return new BezoutIdentity(u, v);
    }
}
