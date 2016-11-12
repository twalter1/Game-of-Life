package GameOfLife;

/**
 * 
 * @author Josh Baedke, Tristan Walters
 *
 */

public class Decay extends Cell {
	/**
	 * Constructs a DyingBrain object for Brian's Brain
	 * 
	 * @param g:
	 *            The grid in which it is placed
	 * @param r:
	 *            The row position
	 * @param c:
	 *            The column position
	 * 
	 */
	public Decay(Grid g, int r, int c) {

		grid = g;
		row = r;
		column = c;

	}

	// DyingBrain occupies the square.
	public State who() {

		return State.Decay;

	}

	/**
	 * Allows you to change the current state of a cell.
	 * 
	 * @param thisGrid:
	 *            The grid in which it is placed
	 * 
	 */
	public void nextState(Grid thisGrid) {

		thisGrid.board[row][column] = new Dead(thisGrid, row, column);

	}

	/**
	 * A DyingBrain cell will always become a DeadBrain cell
	 * 
	 * @param nGrid:
	 *            Grid of the next generation
	 * @return Cell: new Cell form occupying the same position
	 * 
	 */
	public Cell next(Grid nGrid) {

		Cell newCell = new Dead(nGrid, row, column);

		nGrid.board[row][column] = newCell;

		return newCell;

	}

}
