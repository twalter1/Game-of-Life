package GameOfLife;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * 
 * @author Tristan Walters, Josh Baedke
 *
 */

public class Grid {

	private int width;
	public Cell[][] board;
	private String game = "Game of Life";

	/**
	 * 
	 * Constructor takes in a file and reads from that file
	 * 
	 * Assumption: The input file is in correct format. You may create the grid
	 * world in the following steps:
	 * 
	 * 1) The First line is an int used to set the width of the board. 2)
	 * Creates a board according to the int from the first line. 3) The rest of
	 * the file is the board itself so fill in the board according to the input
	 * file.
	 * 
	 * Be sure to close the input file when you are done.
	 * 
	 */
	public Grid(File inputFileName) throws FileNotFoundException {

		String temp = "";
		game = "";
		char temp2 = 0;
		int count = 0;
		int i = 0;
		int j = 0;
		int numAnts = 0;
		Scanner scan = new Scanner(inputFileName);

		game = scan.nextLine();
		if (game.equals("Game of Life") || game.equals("Langton's Ant") || game.equals("Brian's Brain")) {

//			System.out.println("The chosen game is: " + game);

		} else {
			JOptionPane.showMessageDialog(Simulator.frame,
					"Please change the first line of the file to either: \n" + "Game of Life \n"
					+ "Brian's Brain \n" + "Langton's Ant",
					"Game Not Recognized", JOptionPane.ERROR_MESSAGE);

//			System.out.println("The name of the game was not recognized");
//			System.out.println("Please change the first line of the file to either: \n" + "Game of Life \n"
//					+ "Brian's Brain \n" + "Langton's Ant");
//			System.exit(2);
			scan.close();
			return;

		}
		count = scan.nextInt();
		width = count;
		board = new Cell[width][width];
		scan.nextLine();

		for (i = 0; i < width; i++) {

			for (j = 0; j < width; j++) {

				temp = scan.next().trim();
				temp2 = temp.charAt(0);
				if (temp2 == 'D') {


					board[i][j] = new Dead(this, i, j);


				} else if (temp2 == 'A') {

					

					board[i][j] = new Alive(this, i, j);

					

				} else if (temp2 == 'X') {

					 if (game.equals("Brian's Brain")) {

						board[i][j] = new Decay(this, i, j);

					} else {
						JOptionPane.showMessageDialog(Simulator.frame,
								"The Game of Life or Langton's Ant do not accept X please remove the X from the file",
								"X Not Accepted", JOptionPane.ERROR_MESSAGE);
//						System.out.println("The Game of Life or Langton's Ant do not accept X please remove the X from the file");
//						System.exit(2);
						return;

					}

				}

			}

		}
		if (game.equals("Langton's Ant") && numAnts == 0) {

			// TODO
			// board[ i / 2 ][ j / 2] = new Ant( this );

		}
		scan.close();

	}

	/**
	 * 
	 * Constructor takes in a String, converts it to a file and then reads from
	 * that file
	 * 
	 * Assumption: The input file is in correct format. You may create the grid
	 * world in the following steps:
	 * 
	 * 1) The First line is an int used to set the width of the board. 2)
	 * Creates a board according to the int from the first line. 3) The rest of
	 * the file is the board itself so fill in the board according to the input
	 * file.
	 * 
	 * Be sure to close the input file when you are done.
	 * 
	 */
	public Grid(String inputFileName) throws FileNotFoundException {

		String temp = "";
		game = "";
		char temp2 = 0;
		int count = 0;
		int i = 0;
		int j = 0;
		int numAnts = 0;
		File file = new File(inputFileName);
		Scanner scan = new Scanner(file);

		game = scan.nextLine();
		if (game.equals("Game of Life") || game.equals("Langton's Ant") || game.equals("Brian's Brain")) {

//			System.out.println("The chosen game is: " + game);

		} else {
			
			JOptionPane.showMessageDialog(Simulator.frame,
					"Please change the first line of the file to either: \n" + "Game of Life \n"
					+ "Brian's Brain \n" + "Langton's Ant",
					"Game Not Recognized", JOptionPane.ERROR_MESSAGE);

//			System.out.println("The name of the game was not recognized");
//			System.out.println("Please change the first line of the file to either: \n" + "Game of Life \n"
//					+ "Brian's Brain \n" + "Langton's Ant");
//			System.exit(2);
			scan.close();
			return;

		}
		count = scan.nextInt();
		width = count;
		board = new Cell[width][width];
		scan.nextLine();

		for (i = 0; i < width; i++) {

			for (j = 0; j < width; j++) {

				temp = scan.next().trim();
				temp2 = temp.charAt(0);
				if (temp2 == 'D') {

					board[i][j] = new Dead(this, i, j);

					
				} else if (temp2 == 'A') {


					board[i][j] = new Alive(this, i, j);

				} else if (temp2 == 'X') {

					if (game.equals("Brian's Brain")) {

						board[i][j] = new Decay(this, i, j);

					} else {
						JOptionPane.showMessageDialog(Simulator.frame,
								"The Game of Life or Langton's Ant do not accept X please remove the X from the file",
								"X Not Accepted", JOptionPane.ERROR_MESSAGE);

//						System.out.println("The Game of Life or Langton's Ant do not accept X please remove the X from the file");
						System.exit(2);

					}

				}

			}

		}
		if (game.equals("Langton's Ant") && numAnts == 0) {
			// TODO
			// board[ i / 2 ][ j / 2] = new Ant( this );

		}
		scan.close();

	}

	public Grid() {

		int i = 0;
		int j = 0;
		game = String.valueOf(Simulator.games.getSelectedItem());
		

		for (i = 0; i < width; i++) {

			for (j = 0; j < width; j++) {

				

				board[i][j] = new Dead(this, i, j);

				

			}

		}

	}

	/**
	 * Constructor that builds a w X w board without initializing it.
	 * 
	 * @param w
	 *            The width of the board
	 */
	public Grid(int w) {

		width = w;

		board = new Cell[width][width];
		for (int i = 0; i < width; i++) {

			for (int j = 0; j < width; j++) {
				board[i][j] = new Dead(this, i, j);

			}
		}

	}

	public int getWidth() {

		return width;

	}

	public void setGame(String gameName) {

		game = gameName;

	}

	public String getGame() {

		return game;

	}

	public void resize(int size) {

		if (width > size) {

			return;

		}
		Cell[][] Blank = new Cell[size][size];
		for (int i = 0; i < size; i++) {

			for (int j = 0; j < size; j++) {

				if (i >= width || j >= width) {

					

					Blank[i][j] = new Dead(this, i, j);

					
				} else {

					Blank[i][j] = board[i][j];

				}

			}

		}
		width = size;
		board = Blank;

	}

	/**
	 * Write the Grid board as a string.
	 */
	@Override
	public String toString() {

		int i = 0;
		int j = 0;

		for (i = 0; i < width; i++) {

			for (j = 0; j < width; j++) {

				if (board[i][j].who() == State.Dead) 
				{

					System.out.print("D ");

				}

				else if (board[i][j].who() == State.Alive) 
				{

					System.out.print("A ");

				} else if (board[i][j].who() == State.Decay)
				{

					System.out.print("X ");

				}

			}

			System.out.println();

		}

		return "";

	}

	public Grid(Grid baseGrid) {
		board = new Cell[baseGrid.getWidth()][baseGrid.getWidth()];
		game = baseGrid.getGame();
		width = baseGrid.getWidth();
		for (int i = 0; i < width; i++) {

			for (int j = 0; j < width; j++) {
				if (baseGrid.board[i][j].who() == State.Dead) 
				{

					board[i][j] = new Dead(this, i, j);

				}

				else if (baseGrid.board[i][j].who() == State.Alive) 
				{

					board[i][j] = new Alive(this, i, j);

				} else 
				{

					board[i][j] = new Decay(this, i, j);

				}

			}
		}
		if (Simulator.games.getSelectedIndex() == 0)
			setGame("Game of Life");
		else if (Simulator.games.getSelectedIndex() == 1)
			setGame("Brian's Brain");
		else
			setGame("Langton's Ant");
	}

}
