import java.io.*;
import java.util.*;

public class Sudoku {
	public static void main(String [] args) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("all_17"));
			BufferedWriter out = new BufferedWriter(new FileWriter("all_16"));
			String line = null;

			while ((line = br.readLine()) != null) {
				String grid = "";

				Random generator = new Random();
				int num = generator.nextInt(17) + 1;
				int counter = 1;
				System.out.println("num: " + num);

				for (int index = 0; index < line.length(); index++)  {
					char c = line.charAt(index);

					if (c != '.') {
						if (counter != num) {
							grid = grid + getRow(index) + getColumn(index) + c + " ";
						}
						counter = counter + 1;
					}
				}
				out.write(grid + "\n");
				out.flush();

			}

			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static int getRow (int index) {
		return (index / 9) + 1;
	}

	static int getColumn (int index) {
		return (index % 9) + 1;
	}
}