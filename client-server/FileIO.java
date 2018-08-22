import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Singleton pattern with only one instance
public class FileIO {

    private static FileIO instanceFileIO = null;

    private FileIO() {}

    public static FileIO instance() {
        if (instanceFileIO == null) {
            instanceFileIO = new FileIO();
        }
        return instanceFileIO;
    }

    //Append the file
    public static void append(String fileName, String data) throws IOException {
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
        out.println(data);
        out.close();
    }

    //Read the file and return file content in List type
    private List<String> read(String fileName) throws IOException {
        List<String> data = new ArrayList<>();
        Scanner scanner = new Scanner(new FileInputStream(fileName));

        while (scanner.hasNextLine()) {
            data.add(scanner.nextLine());
        }

        scanner.close();
        return data;
    }

    public void readEntries(String fileName) throws IOException {
        List<String> stringArray = read(fileName);
        stringArray.forEach(System.out::println);
    }

}
