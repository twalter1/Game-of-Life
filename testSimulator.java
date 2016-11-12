package GameOfLife;

import java.awt.BorderLayout;
import java.io.FileNotFoundException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 
 * @author Josh Baedke, Tristan Walters
 *
 */

public class testSimulator {

	static buildGrid showGrid;
	static Grid dataGrid = new Grid(0);
	static JFrame frame;

	public static void main(String[] args) throws FileNotFoundException {

		frame = new JFrame("Game Of Life");
		final JPanel background = new JPanel();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		background.setLayout(new BoxLayout(background, BoxLayout.Y_AXIS));

		Simulator simulate = new Simulator();
		showGrid = new buildGrid();
		frame.setJMenuBar(simulate.myMenuBar());
		frame.setSize(showGrid.getPreferredSize());
		background.add(simulate.simPanel());
		background.add(showGrid);

		frame.getContentPane().add(background);
		frame.pack();

		frame.setVisible(true);

	}

}
