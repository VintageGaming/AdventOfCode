import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class attempt2 {
    static int lineLength = 0;
    private static boolean part2 = true;

    public static void call(boolean part2) throws IOException {
        attempt2.part2 = part2;

        long startTime = System.nanoTime();
        int removals = day4();
        long endTime = System.nanoTime();
        long durationMethod2 = (endTime - startTime) / 1_000_000;
        System.out.println("Attempt 2 ( Char[] ) Final Removals: " + removals);
        System.out.println("Attempt 2 Method took: " + durationMethod2 + " ms");
    }

    private static int day4() throws IOException {
        Path path = Path.of("Day4/input/input.txt");
        String rack = Files.readString(path);
        lineLength = rack.indexOf("\r");
        if (lineLength == -1) {
            lineLength = rack.indexOf("\n");
        }
        rack = rack.trim().replace("\r", "").replace("\n", "");

        int removalCount = 0;
        char[] rackArr = rack.toCharArray();
        List<Integer> removals = mather(rackArr);

        while (!removals.isEmpty()) {
            for (int c : removals) {
                rackArr[c] = '.';
            }
            removalCount += removals.size();

            if (part2)
                removals = mather(rackArr);
            else {
                break;
            }
        }
        return removalCount;
    }

    private static List<Integer> mather(char[] shelf) {
        List<Integer> removals = new ArrayList<>();
        boolean prev = false;
        for (int roll = 0; roll < shelf.length; roll++) {
            if (shelf[roll] != '@') {
                prev = false;
                continue;
            }

            int adjacent = 0;
            boolean checkLeft = (roll % lineLength > 0);
            boolean checkRight = (roll % lineLength < lineLength - 1);
            boolean checkAbove = (roll >= lineLength);
            boolean checkBelow = (roll < shelf.length - lineLength);

            // Above Check
            if (checkAbove) {
                // Left Check
                if (checkLeft && shelf[roll - lineLength - 1] == '@') adjacent++;
                // Middle Check
                if (shelf[roll - lineLength] == '@') adjacent++;
                // Right Check
                if (checkRight && shelf[roll - lineLength + 1] == '@') adjacent++;
            }

            // Neighbor Check
            if (checkLeft && prev) adjacent++;
            if (adjacent == 4) continue;
            if (checkRight && shelf[roll + 1] == '@') adjacent++;

            // Below Check
            if (adjacent < 4 && checkBelow) {
                // Left Check
                if (checkLeft && shelf[roll + lineLength - 1] == '@') adjacent++;
                // Middle Check
                if (shelf[roll + lineLength] == '@') adjacent++;
                // Right Check
                if (checkRight && shelf[roll + lineLength + 1] == '@') adjacent++;
            }

            if (adjacent < 4) {
                removals.add(roll);
            }
            prev = true;
        }
        return removals;
    }
}
