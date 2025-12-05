import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

        List<long[]> ranges = new ArrayList<>();
        try {
            Path path = Path.of("input/input.txt");
            for (String line : Files.readAllLines(path)) {
                if (line.isEmpty()) break;
                String[] parts = line.split("-");
                ranges.add(new long[]{Long.parseLong(parts[0]), Long.parseLong(parts[1])});
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ranges.sort(Comparator.comparingLong(r -> r[0]));

        LinkedList<long[]> mergedRanges = new LinkedList<>();
        mergedRanges.add(ranges.getFirst());

        for (int i = 1; i < ranges.size(); i++) {
            long[] currentRange = ranges.get(i);
            long[] lastMergedRange = mergedRanges.getLast();

            if (currentRange[0] <= lastMergedRange[1]) {
                lastMergedRange[1] = Math.max(lastMergedRange[1], currentRange[1]);
            } else {
                mergedRanges.add(currentRange);
            }
        }

        long totalValidIds = 0;

        for (long[] mergedRange : mergedRanges) {
            System.out.println("(" + mergedRange[0] + " - " + mergedRange[1] + ")");
            totalValidIds += (mergedRange[1] - mergedRange[0] + 1);
        }

        System.out.println("Total Valid IDs: " + totalValidIds);
    }

    public static void partOne() {
        List<String> freshDates = new ArrayList<>();
        List<Long> ingredients = new ArrayList<>();
        int spoiled = 0;

        try {
            Path path = Path.of("input/input.txt");
            boolean dates = true;
            for (String line : Files.readAllLines(path)) {
                if (dates && line.isBlank()) {
                    dates = false;
                    continue;
                }
                if (dates) {
                    freshDates.add(line);
                }
                else
                    ingredients.add(Long.parseLong(line));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (long ingredient : ingredients) {
            boolean spoiledIngredient = false;
            for (String date : freshDates) {
                long min = Long.parseLong(date.split("-")[0]);
                long max = Long.parseLong(date.split("-")[1]);
                if (ingredient >= min && ingredient <= max) {
                    spoiledIngredient = true;
                    break;
                }
            }
            if (spoiledIngredient) {
                spoiled++;
                spoiledIngredient = false;
            }
        }

        System.out.println(Arrays.toString(freshDates.toArray()) + "\n" + Arrays.toString(ingredients.toArray()));
        System.out.println("Spoiled Total: " + spoiled);
    }
}
