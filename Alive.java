package GameOfLife;

/**
 * 
 * @author Tristan Walters, Josh Baedke
 *
 */

public class Alive extends Cell {

	/**
	 * Constructs an Alive object
	 * 
	 * @param g:
	 *            The grid in which it is placed
	 * @param r:
	 *            The row position
	 * @param c:
	 *            The column position
	 * 
	 */
	public Alive(Grid g, int r, int c) {

		grid = g;
		row = r;
		column = c;

	}

	// Alive occupies the square.
	public State who() {

		return State.Alive;

	}

	/**
	 * Allows you to change the current state of a cell.
	 * 
	 * @param thisGrid:
	 *            The grid in which it is placed
	 * 
	 */
	public void nextState(Grid thisGrid) {
		if (Simulator.games.getSelectedIndex() == 1)
			thisGrid.board[row][column] = new Decay(thisGrid, row, column);
		else
			thisGrid.board[row][column] = new Dead(thisGrid, row, column);

	}

	/**
	 * An Alive cell will die if there are fewer than two alive neighbors An
	 * Alive cell will die if there are greater than three alive neighbors An
	 * Alive cell stays alive if there are only two or three alive neighbors
	 * 
	 * @param nGrid:
	 *            Grid of the next generation
	 * @return Cell: new Cell form occupying the same position
	 * 
	 */
	public Cell next(Grid nGrid) {

		Cell newCell = null;

		if (Simulator.games.getSelectedIndex() == 1) {
			newCell = new Decay(nGrid, row, column);
		} else {
			int[] neighboringCells = new int[2];

			this.getNeighbors(neighboringCells);
			// System.out.println( "This is the row that we are looking at: " +
			// row );
			// System.out.println( "This is the column that we are looking at: "
			// + column );
			// System.out.println( "These are the Dead neighboringCells: " +
			// neighboringCells[0] );
			// System.out.println( "These are the Alive neighboringCells: " +
			// neighboringCells[1] );

			if (neighboringCells[1] < 2 || neighboringCells[1] > 3) {

				newCell = new Dead(nGrid, row, column);

			} else {

				newCell = new Alive(nGrid, row, column);

			}
		}
		nGrid.board[row][column] = newCell;

		return newCell;

	}

}
