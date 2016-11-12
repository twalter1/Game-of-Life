package GameOfLife;

/**
 * 
 * @author Tristan Walters, Josh Baedke
 *
 */

public abstract class Cell {

	protected Grid grid; // the grid in which the life form resides
	protected int row; // location of the square on which
	protected int column; // the life form resides

	// constants to be used as indices.
	/*
	 * protected static final int Dead = 0; protected static final int Alive =
	 * 1; protected static final int DeadAnt = 2; protected static final int
	 * AliveAnt = 3; protected static final int Ant = 4; protected static final
	 * int DeadBrain = 5; protected static final int AliveBrain = 6; protected
	 * static final int DyingBrain = 7;
	 */

	/**
	 * getNeighbors gets all the Cells in the 3 X 3 neighborhood in a Grid.
	 * Count the number of Dead and Alive Cells in the 3 by 3 neighborhood
	 * centered at this Cell object. Store the counts in the array neighbors[]
	 * at indices 0 and 1 respectively.
	 * 
	 * @param neighbors
	 *            counts all of the neighboring Cells
	 */
	protected void getNeighbors(int neighbors[]) {

		Cell cellForms = null;
		int i = 0;
		int j = 0;

		for (i = row - 1; i <= row + 1; i++) {

			for (j = column - 1; j <= column + 1; j++) {

				if ((i >= 0 && j >= 0) && (i < grid.getWidth() && j < grid.getWidth()) && (i != row || j != column)) {

					cellForms = grid.board[i][j];
					if (cellForms.who() == State.Dead) {

						neighbors[0] = neighbors[0] + 1;

					} else if (cellForms.who() == State.Alive) {

						neighbors[1] = neighbors[1] + 1;

					}
				
					else if (cellForms.who() == State.Decay) {

						neighbors[2] = neighbors[2] + 1;

					}

				}

			}

		}

	}

	/**
	 * Gets the identity of the Cell on the Grid.
	 * 
	 * @return State
	 */
	public abstract State who();

	/**
	 * changes the current state of the Cell to the next possible state on the
	 * Grid.
	 */
	public abstract void nextState(Grid thisGrid);

	// To be implemented in each class of Dead and Alive.

	/**
	 * Determines the Cell on the Grid in the next generation.
	 * 
	 * @param nGrid
	 *            grid of the next generation
	 * @return Cell
	 */
	public abstract Cell next(Grid nGrid);
	// To be implemented in the classes Dead and Alive.
	//
	// For each class, carry out the following:
	//
	// 1. Obtains counts of Cells in the 3X3 neighborhood of the class
	// object.
	//
	// 2. Applies the survival rules for the Cell to determine the Cell
	// (on the same square) in the next cycle. These rules are found at
	// https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life
	//
	// 3. Generate this new Cell at the same location in the new Grid.

}
