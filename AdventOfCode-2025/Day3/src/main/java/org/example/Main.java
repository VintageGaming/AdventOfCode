package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        String[] banks;

        try {
            Path path = Path.of("Day3/input/DayThree.txt");
            banks = Files.readString(path).split("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // --- Speed Test ---

        // Time partOne()
        long startTimePartOne = System.nanoTime();
        partOne(banks);
        long endTimePartOne = System.nanoTime();
        long durationPartOne = (endTimePartOne - startTimePartOne) / 1_000_000; // milliseconds
        System.out.println("partOne() execution time: " + durationPartOne + " ms\n");

        // Time partTwo()
        long startTimePartTwo = System.nanoTime();
        partTwo(banks);
        long endTimePartTwo = System.nanoTime();
        long durationPartTwo = (endTimePartTwo - startTimePartTwo) / 1_000_000; // milliseconds
        System.out.println("partTwo() execution time: " + durationPartTwo + " ms");

    }

    public static void partOne(String[] banks) {
        int joltage = 0;

        for (String bank : banks) {
            bank = bank.trim();
            int first = 0;
            int second = 0;
            for (int cell = 0; cell < bank.length(); cell++) {
                int current = Character.getNumericValue(bank.charAt(cell));
                if (current > first && cell < bank.length() - 1) {
                    first = current;
                    second = 0;
                    continue;
                }
                if (current > second && Integer.parseInt(first + "" + current) > Integer.parseInt(first + "" + second)) {
                    second = current;
                }
            }
            joltage += Integer.parseInt(first + "" + second);
        }

        System.out.println("Joltage (partOne): " + joltage);
    }

    public static void partTwo(String[] banks) {
        long joltage = 0;

        for (String bank : banks) {
            bank = bank.trim();
            if (bank.isEmpty()) continue;

            char[] splitBank = new char[12];

            for (int cell = 0; cell < bank.length(); cell++) {
                char current = bank.charAt(cell);

                int startIndex = 0;
                int remainingChars = bank.length() - cell;
                if (remainingChars < splitBank.length) {
                    startIndex = splitBank.length - remainingChars;
                }

                for (int i = startIndex; i < splitBank.length; i++) {
                    // Check if the slot is null character
                    if (splitBank[i] == '\u0000') {
                        splitBank[i] = current;
                        break;
                    }

                    if (current > splitBank[i] && bank.length() - cell >= (splitBank.length - i)) {
                        splitBank[i] = current;
                        if (i + 1 < splitBank.length)
                            // Reset subsequent slots to be null
                            Arrays.fill(splitBank, i + 1, splitBank.length, '\u0000');
                        break;
                    }
                }
            }

            joltage += Long.parseLong(new String(splitBank));
        }

        System.out.println("Joltage (partTwo): " + joltage);
    }

}
