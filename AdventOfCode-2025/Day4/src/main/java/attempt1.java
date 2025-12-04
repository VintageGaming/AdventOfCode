import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class attempt1 {

    record Coordinate(int shelf, int roll) {}
    private static boolean part2 = true;


    public static void call(boolean part2) throws IOException {
        attempt1.part2 = part2;

        long startTime = System.nanoTime();
        int removals = day4();
        long endTime = System.nanoTime();
        long durationMethod1 = (endTime - startTime) / 1_000_000;
        System.out.println("Attempt 1 ( String[][] ) Final removals: " + removals);
        System.out.println("Attempt 1 took: " + durationMethod1 + " ms\n");
    }

    private static int day4() throws IOException {
        Path path = Path.of("Day4/input/input.txt");
        String[][] shelves = Arrays.stream(Files.readString(path).split("\n")).map(s -> s.trim().split("")).toArray(String[][]::new);

        int removalCount = 0;
        List<Coordinate> removals = looper(shelves);

        while (!removals.isEmpty()) {
            for (Coordinate c : removals) {
                shelves[c.shelf()][c.roll()] = ".";
            }
            removalCount += removals.size();
            if (part2)
                removals = looper(shelves);
            else
                break;
        }
        return removalCount;
    }

    private static List<Coordinate> looper(String[][] shelves) {
        List<Coordinate> removals = new ArrayList<>();

        for (int shelf = 0; shelf < shelves.length; shelf++) {
            String[] shelfArray = shelves[shelf];

            for (int roll = 0; roll < shelfArray.length; roll++) {
                if (!shelfArray[roll].equals("@")) continue;
                int adjacent = 0;

                // Left
                if (roll > 0 && shelfArray[roll - 1].equals("@")) adjacent++;
                // Right
                if (roll < shelfArray.length - 1 && shelfArray[roll + 1].equals("@")) adjacent++;

                // Up
                if (shelf > 0) {
                    if (shelves[shelf - 1][roll].equals("@")) adjacent++;
                    if (roll > 0 && shelves[shelf - 1][roll - 1].equals("@")) adjacent++;
                    if (roll < shelfArray.length - 1 && shelves[shelf - 1][roll + 1].equals("@")) adjacent++;
                }

                // Down
                if (shelf < shelves.length - 1) {
                    if (shelves[shelf + 1][roll].equals("@")) adjacent++;
                    if (roll > 0 && shelves[shelf + 1][roll - 1].equals("@")) adjacent++;
                    if (roll < shelfArray.length - 1 && shelves[shelf + 1][roll + 1].equals("@")) adjacent++;
                }

                if (adjacent < 4) {
                    removals.add(new Coordinate(shelf, roll));
                }
            }
        }
        return removals;
    }
}
