public class Maze {
	public static void main(String args[]){
		if (args.length < 3) {
			System.out.println("Invalid number of arguments, expecting 3");
			return;
		}

		int dimension = Integer.parseInt(args[0]);
		boolean printBorder = Boolean.parseBoolean(args[1]);
		boolean printOrder = Boolean.parseBoolean(args[2]);

		if (dimension < 1) {
			System.out.println("Invalid n value, expecting n >= 1");
			return;
		}

		MazeConstructor mazeConstructor = new MazeConstructor(dimension + 2, printBorder, printOrder);
		System.out.println("\nResult: " + mazeConstructor.construct());
	}
}