package lecture.file.traversal;

import java.io.*;
import java.util.Scanner;

public class Walk {
    protected final String path;

    protected char[] chars;
    protected int len;

    public Walk(String path) {
        this.path = path;

        this.chars = new char[1024];
        this.len = 0;
    }

    public void run(String nameInput, String nameOutput) {
        File input = new File(path + nameInput);
        File output = new File(path + nameOutput);

        if (input.exists() && input.canRead() && output.exists() && output.canWrite()) {
            try(
                FileWriter writer = new FileWriter(output);
            ) {
                walking(input, writer);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.err.println("read/write file does not exist or is not available");
        }
    }

    public void walking(File input, FileWriter writer) throws IOException {
        Scanner scanner = new Scanner(input);

        while (scanner.hasNext()) {
            writer.write(walk(new File(path + scanner.next())) + "\n");
        }
    }

    public String walk(File file) throws IOException {
        System.out.println(file.getName());
        if (file.exists() && file.canRead()) {
            FileReader reader = new FileReader(file);

            int sum = 0;
            while (len != -1) {
                sum += hash(chars, len);
                len = reader.read(chars);
            }

            reader.close();
            return sum == 0 ? "00000000" : Integer.toHexString(sum);
        } else {
            return "00000000";
        }
    }

    protected int hash(char[] bytes, int len) {
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

    public String getPath() {
        return path;
    }
}
