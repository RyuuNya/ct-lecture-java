package lecture.file.traversal;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Walk {
    protected final String path;

    public Walk(String path) {
        this.path = path;
    }

    public void walking(final String input, final String output) {
        try(
                Scanner scanner = new Scanner(new File(path + input));
                FileWriter writer = new FileWriter(path + output, StandardCharsets.UTF_8);
        ) {
            while (scanner.hasNext()) {
                String name = scanner.next();
                writer.write(Integer.toHexString(walkInFile(name)) + " " + name);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public int walkInFile(String name) throws IOException {
        File file = new File(path + name);

        if (file.exists()) {
            FileReader reader = new FileReader(file);

            int r = 0;
            int sum = 0;
            char[] bytes = new char[4];
            while (r != -1) {
                sum += hash(bytes, r);
                r = reader.read(bytes);
            }

            reader.close();
            return sum;
        } else {
            return 0;
        }
    }

    public int hash(char[] bytes, int len) {
        int i = 0;
        int hash = 0;
        while (i != len) {
            hash += bytes[i++];
            hash += hash << 10;
            hash ^= hash >>> 6;
        }
        hash += hash << 3;
        hash ^= hash >> 11;
        hash += hash << 15;
        return hash;
    }
}
