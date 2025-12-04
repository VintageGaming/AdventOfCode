import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    record Coordinate(int shelf, int roll) {}
    static int lineLength = 0;

    public static void main(String[] args) throws IOException {
        System.out.println("Part 1:");
        attempt1.call(false);
        attempt2.call(false);

        System.out.println("\nPart 2:");
        attempt1.call(true);
        attempt2.call(true);
    }
}
