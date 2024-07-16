package lecture.file.traversal;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class RecursiveWalk extends Walk {
    public RecursiveWalk(String path) {
        super(path);
    }

    @Override
    protected void walking(File input, FileWriter writer) throws IOException {
        Scanner scanner = new Scanner(input);

        while (scanner.hasNext()) {
            File file = new File(path + scanner.next());

            if (file.isDirectory() && file.listFiles() != null) {
                recursive(file.listFiles(), writer);
            } else {
                writer.write(walk(file) + "\n");
            }
        }
    }

    private void recursive(File[] files, FileWriter writer) throws IOException {
        for (File file : files) {
            if (file.isDirectory() && file.listFiles() != null) {
                recursive(file.listFiles(), writer);
            } else {
                writer.write(walk(file) + "\n");
            }
        }
    }
}
