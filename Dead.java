package GameOfLife;

/**
 * 
 * @author Tristan Walters, Josh Baedke
 *
 */

public class Dead extends Cell {

	/**
	 * Constructs a Dead object
	 * 
	 * @param g:
	 *            The grid in which it is placed
	 * @param r:
	 *            The row position
	 * @param c:
	 *            The column position
	 * 
	 */
	public Dead(Grid g, int r, int c) {

		grid = g;
		row = r;
		column = c;

	}

	// Dead occupies the square.
	public State who() {

		return State.Dead;

	}

	/**
	 * Allows you to change the current state of a cell.
	 * 
	 * @param thisGrid:
	 *            The grid in which it is placed
	 * 
	 */
	public void nextState(Grid thisGrid) {

		thisGrid.board[row][column] = new Alive(thisGrid, row, column);

	}

	/**
	 * A Dead cell will become Alive if there are exactly three Alive neighbors
	 * 
	 * @param nGrid:
	 *            Grid of the next generation
	 * @return Cell: new Cell form occupying the same position
	 * 
	 */
	public Cell next(Grid nGrid) {

		Cell newCell = null;
		if (Simulator.games.getSelectedIndex() == 1) {
			int[] neighboringCells = new int[3];

			this.getNeighbors(neighboringCells);

			if (neighboringCells[1] == 2) {

				newCell = new Alive(nGrid, row, column);

			} else {

				newCell = new Dead(nGrid, row, column);

			}
		} else {
			int[] neighboringCells = new int[2];

			this.getNeighbors(neighboringCells);

			if (neighboringCells[1] == 3) {

				newCell = new Alive(nGrid, row, column);

			} else {

				newCell = new Dead(nGrid, row, column);

			}
		}
		nGrid.board[row][column] = newCell;

		return newCell;

	}

}
