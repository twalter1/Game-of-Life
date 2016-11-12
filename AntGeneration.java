package GameOfLife;

import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/**
 * 
 * @author Josh Baedke, Tristan Walters
 *
 */

public class AntGeneration {
	public static int x = 0;
	public static int y = 0;
	public static int facing;

	public AntGeneration() {
		AntGeneration.x = testSimulator.showGrid.numColumns / 2;
		y = testSimulator.showGrid.numRows / 2;
		facing = 0;
	}

	public static int[] getXY() {
		int[] XY = new int[2];
		XY[0] = x;
		XY[1] = y;

		return XY;
	}

	public static void setXY(int[] XY) {
		x = XY[0];
		y = XY[1];
	}

	public static void setFacing(int f) {
		facing = f % 4;
	}

	public static void turnRight() {
		facing = (facing + 1) % 4;
	}

	public static void turnLeft() {
		facing = (3 + facing) % 4;
	}

	public static void north() {
		y--;
	}

	public static void east() {
		x++;
	}

	public static void south() {
		y++;
	}

	public static void west() {
		x--;
	}
}

class stepAntForward extends SwingWorker<Integer, Integer> {
	@Override
	protected Integer doInBackground() throws Exception {

		int n = 0;
		try {

			n = Integer.parseInt(Simulator.numGenerations.getText());

		} catch (NumberFormatException e1) {

			JOptionPane.showMessageDialog(Simulator.frame,
					"The value you entered was not a number.\n Please enter a number for this field.", "Not a Number",
					JOptionPane.ERROR_MESSAGE);
			// e1.printStackTrace();

		}
		if (n < 0) {

			JOptionPane.showMessageDialog(Simulator.frame,
					"The value you entered was less than 0.\n Please enter a number greater than or equal to 0.",
					"Less than 0", JOptionPane.ERROR_MESSAGE);

		}

		int generations = n;
		if (generations < 0) {

			System.out.println("Invalid input. Please try again.");

		}

		for (int i = 0; i < generations; i++) {
			// TODO Auto-generated method stub

			if (testSimulator.dataGrid.board[AntGeneration.x][AntGeneration.y].who() == State.Alive)
				AntGeneration.turnRight();
			else
				AntGeneration.turnLeft();


			Thread update = new Thread(new updateCell(AntGeneration.x, AntGeneration.y));

			int facing = AntGeneration.facing;
			if (facing == 0)
				AntGeneration.north();
			else if (facing == 1)
				AntGeneration.east();
			else if (facing == 2)
				AntGeneration.south();
			else
				AntGeneration.west();

			update.start();
			synchronized (update) {
				try {

					update.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			Simulator.stepAge();
			try {
				TimeUnit.MILLISECONDS.sleep(Simulator.millisec);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (Simulator.kill)
				return null;
			while (Simulator.hold == true) {
				if (Simulator.kill)
					return null;
				try {
					TimeUnit.MILLISECONDS.sleep(10);

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}


		return null;
	}

}

class runAntForward extends SwingWorker<Integer, Integer> {
	@Override
	protected Integer doInBackground() throws Exception {


		while (true) {
			if (testSimulator.dataGrid.board[AntGeneration.x][AntGeneration.y].who() == State.Alive)
				AntGeneration.turnRight();
			else
				AntGeneration.turnLeft();


			Thread update = new Thread(new updateCell(AntGeneration.x, AntGeneration.y));

			int facing = AntGeneration.facing;
			if (facing == 0)
				AntGeneration.north();
			else if (facing == 1)
				AntGeneration.east();
			else if (facing == 2)
				AntGeneration.south();
			else
				AntGeneration.west();


			update.start();
			synchronized (update) {
				try {

					update.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			Simulator.stepAge();
			try {
				TimeUnit.MILLISECONDS.sleep(Simulator.millisec);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (Simulator.kill)
				return null;
			while (Simulator.holdrun == true) {
				if (Simulator.kill)
					return null;
				try {
					TimeUnit.MILLISECONDS.sleep(10);

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
