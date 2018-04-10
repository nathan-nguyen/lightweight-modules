import java.util.ArrayList;
import java.util.Random;

public class MazeConstructor {
	private static final boolean CENTRE_MODE = false;

	private static final int EMPTY = 0;
	private static final int INVALID = -1;

	private static String[] unit = {
	};

	private Random rand = new Random();

	// Using two dimensions array for easy maintenance
	private int[][] map;
	private int dimension;
	private boolean printBorder;
	private boolean printOrder;

	public MazeConstructor(int n, boolean printBorder,boolean printOrder){
		this.dimension = n;
		this.printBorder = printBorder;
		this.printOrder = printOrder;
	}

	public boolean construct(){
		ArrayList<Integer> expandableList = new ArrayList<>();
		// Using two dimension array for easy maintenance
		map = new int[dimension][dimension];

		for (int i = 0; i < dimension; ++i){
			map[0][i] = INVALID;
			map[i][0] = INVALID;
			map[dimension - 1][i] = INVALID;
			map[i][dimension - 1] = INVALID;
		}

		// Add (1, 1) to expandableList
		expandableList.add(dimension + 1);

		// 0 < next / n < n-1 && 0 < next % n < n-1
		int next = dimension + 1;
		int order = 0;
		map[1][1] = order % 8 + 1;

		// Keep expanding the maze until there is not any space left
		while (true) {
			if (map[next / dimension - 1][next % dimension] == EMPTY && !isValidPosition(next - dimension, dimension))
				map[next / dimension - 1][next % dimension] = INVALID;
			if (map[next / dimension][next % dimension + 1] ==  EMPTY && !isValidPosition(next + 1, dimension))
				map[next / dimension][next % dimension + 1] = INVALID;
			if (map[next / dimension + 1][next % dimension] == EMPTY && !isValidPosition(next + dimension, dimension))
				map[next / dimension + 1][next % dimension] = INVALID;
			if (map[next / dimension][next % dimension - 1] == EMPTY && !isValidPosition(next - 1, dimension))
				map[next / dimension][next % dimension - 1] = INVALID;

			if (map[next / dimension - 1][next % dimension] != EMPTY && map[next / dimension][next % dimension + 1] != EMPTY
				&& map[next / dimension + 1][next % dimension] != EMPTY && map[next / dimension][next % dimension - 1] != EMPTY) {
				expandableList.remove(Integer.valueOf(next));

				// No space left
				if (expandableList.size() == 0) break;

				next = expandableList.get(rand.nextInt(expandableList.size()));
				++order;
				continue;
			}
			int nextSquare = nextSquare(next, dimension);
			expandableList.add(nextSquare);
			map[nextSquare / dimension][nextSquare % dimension] = order % 8 + 1;

			if (CENTRE_MODE && nextSquare / dimension == dimension / 2 && nextSquare % dimension == dimension / 2)
				map[nextSquare / dimension][nextSquare % dimension] = 9;

			next = nextSquare;
//			next = expandableList.get(rand.nextInt(expandableList.size()));
		}

//		printMap();
//		printMaze();
		printWall();

		return CENTRE_MODE ? map[dimension / 2][dimension / 2] == 9 : map[dimension - 2][dimension - 2] > 0;
	}

	private int nextSquare(int next, int n){
		// Keep looking for the next square
		// This is always guarantee to terminate
		while (true) {
			int order = rand.nextInt(4);
			switch (order) {
				case 0: if (map[next / n - 1][next % n] == EMPTY && isValidPosition(next - n, n)) return next - n;
				case 1: if (map[next / n][next % n + 1] == EMPTY && isValidPosition(next + 1, n)) return next + 1;
				case 2: if (map[next / n + 1][next % n] == EMPTY && isValidPosition(next + n, n)) return next + n;
				case 3: if (map[next / n][next % n - 1] == EMPTY && isValidPosition(next - 1, n)) return next - 1;
			}
		}
	}

	private boolean isValidPosition(int x, int n) {
		int count = 0;
		if (map[x / n - 1][x % n] > 0) ++count;
		if (map[x / n][x % n + 1] > 0) ++count;
		if (map[x / n + 1][x % n] > 0) ++count;
		if (map[x / n][x % n - 1] > 0) ++count;
		return count == 1;
	}

	private void printMap(){
		for (int i = 0; i < dimension; ++i){
			for (int j = 0; j < dimension; ++j){
				if (i == 0 || i == dimension - 1 || j == 0 || j == dimension - 1) System.out.print(printBorder ? "x " : "  ");
				else if (map[i][j] > 0) System.out.print(printOrder ? map[i][j] + " " : "o ");
				else System.out.print("  ");
			}
			System.out.println();
		}
	}

	private void printWall() {
		System.out.println();

		// Open the entrance
		map[0][1] = 1;

		// Open the exit
		if (!CENTRE_MODE && map[dimension - 2][dimension - 2] > 0) map[dimension - 2][dimension - 1] = 1;

		char[][] display = new char[dimension][dimension * 2];
		for (int i = 0; i < dimension; ++i){
			for (int j = 0; j < dimension; ++j){
				if (map[i][j] > 0) continue;
				display[i][2 * j] = (char) ('0' + rand.nextInt(10));
				display[i][2 * j + 1] = (char) ('0' + rand.nextInt(10));
			}
		}

		// Mark the destination
		if (CENTRE_MODE && map[dimension / 2][dimension / 2] == 9){
			display[dimension / 2][2 * (dimension / 2)] = 'X';
			display[dimension / 2][2 * (dimension / 2) + 1] = 'X';
		}

		// Print the maze
		for (int i = 0; i < dimension; ++i){
			for (int j = 0; j < dimension * 2; ++j){
				if (display[i][j] == 0) System.out.print(" ");
				else System.out.print(display[i][j]);
			}
			System.out.println();
		}

	}

	// Please dont use this method unless you have already initialized unit array properly
	private void printMaze() {
		// Open the entrace
		map[0][1] = 1;

		// Open the exit
		if (!CENTRE_MODE && map[dimension - 2][dimension - 2] > 0) map[dimension - 2][dimension - 1] = 1;

		char[][] display = new char[(dimension - 2) * 2][(dimension - 2) * 2];
		for (int i = 1; i < dimension - 1; ++i){
			for (int j = 1; j < dimension - 1; ++j){
				if (map[i][j] <= 0) continue;

				int displayValue = 0;
				if (map[i - 1][j] > 0) displayValue |= 1 << 3;
				if (map[i][j + 1] > 0) displayValue |= 1 << 2;
				if (map[i + 1][j] > 0) displayValue |= 1 << 1;
				if (map[i][j - 1] > 0) displayValue |= 1;

				display[(i - 1) * 2][(j - 1) * 2] = unit[displayValue].charAt(0);
				display[(i - 1) * 2][(j - 1) * 2 + 1] = unit[displayValue].charAt(1);
				display[(i - 1) * 2 + 1][(j - 1) * 2] = unit[displayValue].charAt(2);
				display[(i - 1) * 2 + 1][(j - 1) * 2 + 1] = unit[displayValue].charAt(3);
			}
		}

		// Print the maze
		for (int i = 0; i < (dimension - 2) * 2; ++i) {
			for (int j = 0; j < (dimension - 2) * 2; ++j){
				if (display[i][j] == 0) System.out.print("  ");
				else System.out.print(display[i][j]);
			}
			System.out.println();
		}
	}
}