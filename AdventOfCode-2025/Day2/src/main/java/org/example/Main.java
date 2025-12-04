package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        String[] ranges;
        try {
            Path path = Path.of("Day2/input/DayTwo.txt");
            ranges = Files.readString(path).split(",");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        partOne(ranges);
        partTwoTryTwo(ranges);

    }

    public static void partOne(String[] ranges) {
        long invalidTotal = 0;

        for (String range : ranges) {
            String[] splitRange = range.split("-");
            String lowerStr = splitRange[0];
            String upperStr = splitRange[1];

            if (lowerStr.length() % 2 != 0 && upperStr.length() % 2 != 0) continue;

            long lowerInt = Long.parseLong(splitRange[0]);
            long upperInt = Long.parseLong(splitRange[1]);
            long count = lowerInt;

            while (count <= upperInt) {
                String countStr = String.valueOf(count);
                int split = countStr.length();
                if (split % 2 != 0) {
                    count++;
                    continue;
                }

                long lowSplit = Long.parseLong(countStr.substring(0, (split/2)));
                long highSplit = Long.parseLong(countStr.substring((split/2), split));
                if (lowSplit == highSplit) {
                    invalidTotal += count;
                }
                count++;

            }
        }
        System.out.println("Invalid Total (Part 1): " + invalidTotal);
    }

    public static void partTwoTryTwo(String[] ranges) {
        long invalidTotal = 0;

        for (String range : ranges) {
            String[] splitRange = range.split("-");

            long lowerInt = Long.parseLong(splitRange[0]);
            long upperInt = Long.parseLong(splitRange[1]);

            for (var count = lowerInt; count <= upperInt; count++) {
                String countStr = String.valueOf(count);
                int countLength = countStr.length();

                for (int i = 1; i <= Math.floorDiv(countLength, 2); i++) {
                    String pattern = countStr.substring(0, i);
                    if (countLength % i != 0) continue;

                    String repeated = pattern.repeat(countLength / pattern.length());
                    if (countStr.equals(repeated)) {
                        invalidTotal += count;
                        break;
                    }
                }
            }
        }
        System.out.println("Invalid Total (Part 2): " + invalidTotal);
    }

    public static void partTwoOG(String[] ranges) {
        long invalidTotal = 0;

        for (String range : ranges) {
            String[] splitRange = range.split("-");

            long lowerInt = Long.parseLong(splitRange[0]);
            long upperInt = Long.parseLong(splitRange[1]);
            long count = lowerInt;

            while (count <= upperInt) {
                String countStr = String.valueOf(count);
                int countLength = countStr.length();
                boolean matching = false;
                for(int i = 1; i <= (Math.floorDiv(countLength, 2)); i++) {
                    String lowSplit = countStr.substring(0, i);


                    for (int j = i; j <= countLength; j+= lowSplit.length()) {

                        if (j + lowSplit.length() > countLength) break;
                        String runSplit = countStr.substring(j, j + lowSplit.length());
                        if (!lowSplit.equals(runSplit)) {
                            matching = false;
                            break;
                        }
                        if (j == countLength - lowSplit.length()) matching = true;
                    }
                    if (matching) break;

                }
                if (matching) {
                    invalidTotal += count;
                }
                count++;

            }
        }
        System.out.println("Invalid Total (Part 2): " + invalidTotal);
    }
}