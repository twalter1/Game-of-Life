package GameOfLife;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

/**
 * 
 * @author Josh Baedke, Tristan Walters
 *
 */

public class buildGrid extends JPanel {

	static int numColumns = 10;
	static int numRows = 10;
	// private List<Rectangle> cells;

	int width;
	int height;

	int cellWidth;
	int cellHeight;

	int xOffset;

	Graphics2D g2d;

	static boolean hold;

	public void hold() {
		hold = false;
	}

	public boolean getHold() {
		return hold;
	}

	public buildGrid() {

		MouseAdapter mouseHandler;
		mouseHandler = new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				int column = e.getX() / cellWidth;
				int row = e.getY() / cellHeight;
				Thread update = new Thread(new updateCell(row, column));
				update.start();
			}

			@Override
			public void mouseClicked(MouseEvent e) {

				

				int column = e.getX() / cellWidth;
				int row = e.getY() / cellHeight;
				Thread update = new Thread(new updateCell(row, column));
				update.start();
				

			}

		};
		addMouseListener(mouseHandler);

	}

	@Override
	public Dimension getPreferredSize() {

		return new Dimension(600, 600);

	}

	public void updateGrid(int n) {

		numColumns = n;
		numRows = n;
		testSimulator.dataGrid.resize(n);
		

	}

	@Override
	protected synchronized void paintComponent(Graphics g) {
		synchronized (this) {
			super.paintComponent(g);
			g2d = (Graphics2D) g.create();

			width = getWidth();
			height = getHeight();

			cellWidth = width / numColumns;
			cellHeight = height / numRows;

			xOffset = (width - (numColumns * cellWidth)) / 2;

			if (testSimulator.dataGrid.getWidth() < numColumns) {

				testSimulator.dataGrid.resize(numColumns);

			}

			for (int i = 0; i < numColumns; i++) {

				for (int j = 0; j < numColumns; j++) {

					Cell spot = testSimulator.dataGrid.board[j][i];
					
					if (spot == null) {

						g2d.setColor(Color.BLACK);
						g2d.fill(new Rectangle(i * cellWidth + xOffset, j * cellHeight, cellWidth, cellHeight));
						g2d.setColor(Color.GRAY);
						g2d.draw(new Rectangle(i * cellWidth + xOffset, j * cellHeight, cellWidth, cellHeight));

					} else if (spot.who() == State.Alive) 
					{

						g2d.setColor(Color.WHITE);
						g2d.fill(new Rectangle(i * cellWidth + xOffset, j * cellHeight, cellWidth, cellHeight));
						g2d.setColor(Color.GRAY);
						g2d.draw(new Rectangle(i * cellWidth + xOffset, j * cellHeight, cellWidth, cellHeight));

					}
					
					else if (spot.who() == State.Decay) 
					{

						g2d.setColor(Color.BLUE);
						g2d.fill(new Rectangle(i * cellWidth + xOffset, j * cellHeight, cellWidth, cellHeight));
						g2d.setColor(Color.GRAY);
						g2d.draw(new Rectangle(i * cellWidth + xOffset, j * cellHeight, cellWidth, cellHeight));

					} else {

						g2d.setColor(Color.BLACK);
						g2d.fill(new Rectangle(i * cellWidth + xOffset, j * cellHeight, cellWidth, cellHeight));
						g2d.setColor(Color.GRAY);
						g2d.draw(new Rectangle(i * cellWidth + xOffset, j * cellHeight, cellWidth, cellHeight));

					}
				}
			}

			if (Simulator.gameChoice.equals("Langton's Ant")) {
				g2d.setColor(Color.RED);
				g2d.fill(new Rectangle(AntGeneration.y * cellWidth + xOffset + cellWidth / 4,
						AntGeneration.x * cellHeight + cellHeight / 4, cellWidth / (2), cellHeight / (2)));
			}

			hold = false;
			notify();
		}

	}

	class redrawGrid extends Thread {

		public redrawGrid() {

		}

		public void run() {
			synchronized (this) {
				for (int i = 0; i < buildGrid.numColumns; i++) {

					for (int j = 0; j < buildGrid.numColumns; j++) {

						Cell spot = testSimulator.dataGrid.board[j][i];
						Thread drawCell = new Thread(new drawCell(spot));
						drawCell.run();
					}
				}
				System.out.println(this.getName() + " redrawGrid is done");
				notify();
			}
		}

	}

	class drawCell extends Thread {
		Cell cell;
		int i;
		int j;

		public drawCell(Cell cell) {
			this.cell = cell;
			i = cell.column;
			j = cell.row;
		}

		public void run() {
			
			if (cell == null) {

				g2d.setColor(Color.BLACK);
				g2d.fill(new Rectangle(i * cellWidth + xOffset, j * cellHeight, cellWidth, cellHeight));
				g2d.setColor(Color.GRAY);
				g2d.draw(new Rectangle(i * cellWidth + xOffset, j * cellHeight, cellWidth, cellHeight));

			} else if (cell.who() == GameOfLife.State.Alive)
			{

				g2d.setColor(Color.WHITE);
				g2d.fill(new Rectangle(i * cellWidth + xOffset, j * cellHeight, cellWidth, cellHeight));
				g2d.setColor(Color.GRAY);
				g2d.draw(new Rectangle(i * cellWidth + xOffset, j * cellHeight, cellWidth, cellHeight));

			}
			
			else if (cell.who() == GameOfLife.State.Decay) 
			{

				g2d.setColor(Color.BLUE);
				g2d.fill(new Rectangle(i * cellWidth + xOffset, j * cellHeight, cellWidth, cellHeight));
				g2d.setColor(Color.GRAY);
				g2d.draw(new Rectangle(i * cellWidth + xOffset, j * cellHeight, cellWidth, cellHeight));

			} else {

				g2d.setColor(Color.BLACK);
				g2d.fill(new Rectangle(i * cellWidth + xOffset, j * cellHeight, cellWidth, cellHeight));
				g2d.setColor(Color.GRAY);
				g2d.draw(new Rectangle(i * cellWidth + xOffset, j * cellHeight, cellWidth, cellHeight));

			}

			
		}
	}

}

class updateCell extends SwingWorker<Integer, Integer> {
	Cell cell;
	int x;
	int y;

	public updateCell(int x, int y) {
		this.x = x;
		this.y = y;
		cell = testSimulator.dataGrid.board[x][y];
	}

	@Override
	protected Integer doInBackground() throws Exception {
		
		synchronized (this) {
			
			cell.nextState(testSimulator.dataGrid);

			testSimulator.showGrid.revalidate();
			testSimulator.showGrid.updateUI();

			notify();
		}
		return null;
	}
}
