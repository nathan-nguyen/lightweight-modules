import java.io.FileInputStream;
import java.io.Scanner;

public class FileComparator {

  /**
   * Main class for FileComparator.
   * Two arguments: FileOne FileTwo.
   */
  public static void main(String[] args) {
    System.out.println("\nSimple file comparator");
    System.out.println("Please note that both input files have to be sorted\n");

    if (args.length != 2) {
      System.out.println("Syntax Error: java LRCom <leftFile> <rightFile>");
      return;
    }

    System.out.println("Left file: " + args[0]);
    System.out.println("Right file: " + args[1]);

    System.out.println("\nProcessing data ...");

    String left = null;
    String right = null;

    Scanner scannerLeft = null;
    Scanner scannerRight = null;
    try {
      scannerLeft = new Scanner(new FileInputStream(args[0]));
      scannerRight = new Scanner(new FileInputStream(args[1]));

      while (true) {
        if (left == null && scannerLeft.hasNextLine()) {
          left = scannerLeft.nextLine();
        }
        if (right == null && scannerRight.hasNextLine()) {
          right = scannerRight.nextLine();
        }

        if (left == null || right == null) {
          if (left != null) {
            System.out.println("[Left]  " + left);
          }
          if (right != null) {
            System.out.println("[Right] " + right);
          }
          break;
        }

        int result = left.compareTo(right);
        if (result < 0) {
          System.out.println("[Left]  " + left);
          left = null;
        } else if (result > 0) {
          System.out.println("[Right] " + right);
          right = null;
        } else {
          left = null;
          right = null;
        }
      }

      if (!scannerLeft.hasNextLine()) {
        while (scannerRight.hasNextLine()) {
          System.out.println("[Right] " + scannerRight.nextLine());
        }
      }

      if (!scannerRight.hasNextLine()) {
        while (scannerLeft.hasNextLine()) {
          System.out.println("[Left]  " + scannerLeft.nextLine());
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (scannerLeft != null) {
        scannerLeft.close();
      }
      if (scannerRight != null) {
        scannerRight.close();
      }
    }
  }
}
