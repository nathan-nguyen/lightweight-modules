import java.util.Scanner;
import java.io.FileInputStream;

public class LRCom {
	public static void main(String[] args){
		System.out.println("\nSimple file comparator");
		System.out.println("Please note that both input files have to be sorted\n");

		if (args.length != 2){
			System.out.println("Syntax Error: java LRCom <leftFile> <rightFile>");
			return;
		}

		System.out.println("Left file: " + args[0]);
		System.out.println("Right file: " + args[1]);

		System.out.println("\nProcessing data ...");

		String left = null;
		String right = null;
		boolean readLeft = true;
		boolean readRight = true;

		Scanner scannerLeft = null;
		Scanner scannerRight = null;
		try {
			scannerLeft = new Scanner(new FileInputStream(args[0]));
			scannerRight = new Scanner(new FileInputStream(args[1]));

			while (scannerLeft.hasNextLine() && scannerRight.hasNextLine()) {
				if (readLeft) left = scannerLeft.nextLine();
				if (readRight) right = scannerRight.nextLine();

				int result = left.compareTo(right);
				if (result < 0){
					System.out.println("[Left]  " + left);
					readLeft = true;
					readRight = false;
				}
				else if (result > 0) {
					System.out.println("[Right] " + right);
					readLeft = false;
					readRight = true;
				}
				else {
					readLeft = true;
					readRight = true;
				}
			}

			if (!scannerLeft.hasNextLine()){
				if (!readRight) System.out.println("[Right] " + right);
				while (scannerRight.hasNextLine()) System.out.println("[Right] " + scannerRight.nextLine());
			}

			if (!scannerRight.hasNextLine()){
				if (!readLeft) System.out.println("[Left] " + left);
				while (scannerLeft.hasNextLine()) System.out.println("[Left]  " + scannerLeft.nextLine());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (scannerLeft != null) scannerLeft.close();
			if (scannerRight != null) scannerRight.close();
		}
	}
}
