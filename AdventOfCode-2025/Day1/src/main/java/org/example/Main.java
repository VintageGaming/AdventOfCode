package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        String[] values;
        try{
            Path path = Path.of("Day1/input/DayOne.txt");
            values = Files.readString(path).split("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        partOne(values);
        partTwo(values);

    }

    public static void partOne(String[] values) {
        int dial = 50;
        int password = 0;

        for (String value : values) {
            int rotation = Integer.parseInt(value.trim().substring(1));

            //Right Turns
            if (value.charAt(0) == 'R') {
                dial = (dial + rotation) % 100;
            }
            // Left Turns
            else {
                dial -= rotation;
                if (dial < 0) {
                    dial = (Math.abs(dial) % 100 == 0 ?
                            0 :
                            100 - (Math.abs(dial) % 100));
                }
            }
            // Check if Dial is at 0
            if (dial == 0)  {
                password++;
            }
        }
        System.out.println("Password (Part 1): " + password);
    }

    // PART 2

    public static void partTwo(String[] values) {
        int dial = 50;
        int password = 0;

        for (String value : values) {
            int rotation = Integer.parseInt(value.trim().substring(1));

            password += Math.floorDiv(rotation, 100);
            if (dial == 0 && rotation % 100 == 0) password--; // Didn't need this for my test, but probably needed for special cases

            //Right Turns
            if (value.charAt(0) == 'R') {
                if (dial + (rotation % 100) > 100) password++;

                dial = (dial + rotation) % 100;
            }
            // Left Turns
            else {
                if (dial - (rotation % 100) < 0 && dial != 0) password++;

                dial -= rotation;
                if (dial < 0) {
                    dial = 100 - (Math.abs(dial) % 100);

                    if (dial == 100) dial = 0;
                }
            }
            // Check if Dial is at 0
            if (dial == 0)  {
                password++;
            }
        }
        System.out.println("Password (Part 2): " + password);
    }
}