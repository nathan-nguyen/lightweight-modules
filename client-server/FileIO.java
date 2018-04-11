import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Singleton pattern with only one instance
public class FileIO {

    private static FileIO instanceFileIO = null;

    private FileIO(){
    }

    public static FileIO instance(){
        if (instanceFileIO == null)
	    instanceFileIO = new FileIO();
	return instanceFileIO;
    }

    //Read the file and return file content in List type
    public List read(String fileName) throws IOException {
        List<String> data = new ArrayList<String>();
        Scanner scanner = new Scanner(new FileInputStream(fileName));
        try {
            while (scanner.hasNextLine())
                data.add(scanner.nextLine());
        } finally {
            scanner.close();
        }
        return data;
    }
     
    //Append the file
    public static void append(String fileName, String data) throws IOException {
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
 
        try {            
            out.println(data);
        } finally {
            out.close();
        }
    }

    public void readEntries() throws IOException {
        ArrayList stringArray = (ArrayList) read("entry.dat");
 
        for (int i = 0; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
	    System.out.println(st);
        }        
    }      

    public void appendEntry(String data) throws IOException {
        append("entry.dat", data);
    }

}
