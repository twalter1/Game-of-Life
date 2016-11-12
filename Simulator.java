package GameOfLife;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

/**
 * 
 * @author Josh Baedke, Tristan Walters
 *
 */

public class Simulator {

	public static JComboBox games;
	private static JComboBox speed;
	private static JTextField size;
	static JTextField numGenerations;
	private static JButton startButton;
	private static JButton pauseButton;
	private static JButton resetButton;
	private static JButton clearButton;
	private static JButton runButton;
	private static JLabel theGames;
	private static JLabel theSize;
	private static JLabel theSpeed;
	private static JLabel theNumGenerations;
	private static JLabel theAge;
	static JFrame frame = testSimulator.frame;
	private static JMenuBar menuBar;
	private static JFileChooser fileChooser;
	private static File fileName;
	private static boolean uploadedFile;
	private static Grid generation0 = new Grid(10);
	static int millisec = 1000;
	static boolean holdrun = false;
	static boolean hold = false;
	static boolean kill = false;
	static String gameChoice = "Game of Life";
	static AntGeneration ants;
	private static int[] antXY;
	private static int age = 0;
	private static JPanel antControl;
	private static JPanel mainPanel;
	private static JPanel thirdPanel;
	private JButton up;
	private JButton down;
	private JButton left;
	private JButton right;
	private JButton center;

	public JPanel simPanel() {

		String[] game = { "Game of Life", "Brian's Brain", "Langton's Ant" };
		String[] speeds = { "Slow", "Regular", "Fast" };
		games = new JComboBox(game);
		games.addActionListener(
				// This is an ANONYMOUS class
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						age = 0;
						theAge.setText("# of Generations:  " + age);
						if (!uploadedFile) {
							unloadAnt();
							hold = false;
							holdrun = false;
							kill = true;

							testSimulator.dataGrid = new Grid(testSimulator.dataGrid.getWidth());

							testSimulator.showGrid.updateUI();

						}
						if (games.getSelectedIndex() == 0)
							gameChoice = "Game of Life";
						else if (games.getSelectedIndex() == 1)
							gameChoice = "Brian's Brain";
						else {
							gameChoice = "Langton's Ant";
							ants = new AntGeneration();
							loadAnt();
							testSimulator.showGrid.updateUI();
						}
					}

				}

		);
		speed = new JComboBox(speeds);
		speed.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int val = speed.getSelectedIndex();
				if (val == 0)
					millisec = 1000;
				else if (val == 1)
					millisec = 100;
				else
					millisec = 10;

			}
		});
		size = new JTextField("10", 5);
		size.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				int n = 0;
				try {

					n = Integer.parseInt(e.getActionCommand());

				} catch (NumberFormatException e1) {

					JOptionPane.showMessageDialog(frame,
							"The value you entered was not a number.\n Please enter a number for this field.",
							"Not a Number", JOptionPane.ERROR_MESSAGE);
					// e1.printStackTrace();

				}
				if ((n < 10) || (n < 0)) {

					if (n > 0) {

						JOptionPane.showMessageDialog(frame,
								"The value you entered was less than 10.\n Please enter a number greater than or equal to 10.",
								"Less than 10", JOptionPane.ERROR_MESSAGE);

					}

				} else if (n > 100) {

					JOptionPane.showMessageDialog(frame,
							"The value you entered was greater than 100.\n Please enter a number less than or equal to 100.",
							"Greater than 100", JOptionPane.ERROR_MESSAGE);

				} else {

					testSimulator.showGrid.updateGrid(n);
					testSimulator.showGrid.updateUI();

				}

			}

		});
		numGenerations = new JTextField("1", 5);
		numGenerations.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				int n = 0;
				try {

					n = Integer.parseInt(e.getActionCommand());

				} catch (NumberFormatException e1) {

					JOptionPane.showMessageDialog(frame,
							"The value you entered was not a number.\n Please enter a number for this field.",
							"Not a Number", JOptionPane.ERROR_MESSAGE);
					// e1.printStackTrace();

				}
				if (n < 0) {

					JOptionPane.showMessageDialog(frame,
							"The value you entered was less than 0.\n Please enter a number greater than or equal to 0.",
							"Less than 0", JOptionPane.ERROR_MESSAGE);

				}

			}

		});
		runButton = new JButton("Run");
		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// System.out.println(testSimulator.dataGrid.getGame());
				if (holdrun == true) {
					holdrun = false;
				} else {
					holdrun = false;
					kill = false;
					if (age == 0) {
						generation0 = new Grid(testSimulator.dataGrid);
						generation0.setGame(gameChoice);
					}
					if (gameChoice.equals("Langton's Ant")) {
						if (age == 0)
							antXY = AntGeneration.getXY();
						new runAntForward().execute();
					} else
						new runWorld().execute();
					testSimulator.showGrid.revalidate();
					testSimulator.showGrid.repaint(1);
					// System.out.println(generation0.toString());

				}
			}
		});

		startButton = new JButton("Step Forward");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// System.out.println(testSimulator.dataGrid.getGame());
				if (hold == true) {
					hold = false;
				} else {
					hold = false;
					kill = false;
					if (age == 0) {
						generation0 = new Grid(testSimulator.dataGrid);
						generation0.setGame(gameChoice);
					}
					if (gameChoice.equals("Langton's Ant")) {
						if (age == 0)
							antXY = AntGeneration.getXY();
						new stepAntForward().execute();
					} else
						new stepWorld().execute();
					testSimulator.showGrid.revalidate();
					testSimulator.showGrid.repaint(1);
					// System.out.println(generation0.toString());

				}
			}
		});
		pauseButton = new JButton("Pause ");
		pauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hold = true;
				holdrun = true;

			}
		});
		resetButton = new JButton("Reset ");
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hold = false;
				holdrun = false;

				try {
					TimeUnit.MILLISECONDS.sleep(20);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				kill = true;

				testSimulator.dataGrid = new Grid(generation0);
				testSimulator.dataGrid.setGame(gameChoice);
				if (gameChoice.equals("Langton's Ant")) {
					AntGeneration.setXY(antXY);
					AntGeneration.setFacing(0);
				}
				age = 0;
				theAge.setText("# of Generations:  " + age);
				testSimulator.showGrid.revalidate();
				testSimulator.showGrid.repaint();

			}
		});

		clearButton = new JButton("Clear ");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hold = false;
				holdrun = false;
				kill = true;

				testSimulator.dataGrid = new Grid(testSimulator.dataGrid.getWidth());
				testSimulator.dataGrid.setGame(gameChoice);
				age = 0;
				theAge.setText("# of Generations:  " + age);
				testSimulator.showGrid.revalidate();
				testSimulator.showGrid.repaint();

			}
		});

		theGames = new JLabel("Game: ");
		theSize = new JLabel("Width: ");
		theSpeed = new JLabel("Speed: ");
		theNumGenerations = new JLabel("Generations: ");
		theAge = new JLabel("# of Generations:  " + age);

		up = new JButton("^");
		up.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AntGeneration.west();
				Thread moveAnt = new Thread(new antUpdate());
				moveAnt.start();
			}
		});
		down = new JButton("v");
		down.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AntGeneration.east();
				Thread moveAnt = new Thread(new antUpdate());
				moveAnt.start();
			}
		});
		left = new JButton("<");
		left.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AntGeneration.north();
				Thread moveAnt = new Thread(new antUpdate());
				moveAnt.start();
			}
		});
		right = new JButton(">");
		right.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AntGeneration.south();
				Thread moveAnt = new Thread(new antUpdate());
				moveAnt.start();
			}
		});
		center = new JButton("center");
		center.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] XY = new int[2];
				XY[0] = testSimulator.showGrid.numColumns / 2;
				XY[1] = testSimulator.showGrid.numColumns / 2;
				AntGeneration.setXY(XY);
				Thread moveAnt = new Thread(new antUpdate());
				moveAnt.start();
			}
		});

		antControl = new JPanel();
		antControl.setLayout(new BoxLayout(antControl, BoxLayout.Y_AXIS));
		JPanel topPanel = new JPanel();
		JPanel midPanel = new JPanel();
		JPanel lowPanel = new JPanel();

		topPanel.add(up);
		midPanel.add(left);
		midPanel.add(center);
		midPanel.add(right);
		lowPanel.add(down);
		antControl.add(topPanel);
		antControl.add(midPanel);
		antControl.add(lowPanel);

		// This the basis for the other panels
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

		// This Panel will be the first column of the mainPanel
		JPanel firstPanel = new JPanel();
		firstPanel.setLayout(new BoxLayout(firstPanel, BoxLayout.Y_AXIS));

		// This is the first row of the firstPanel
		JPanel topFirstPanel = new JPanel();
		topFirstPanel.add(theGames);
		topFirstPanel.add(games);

		// This is the middle row of the firstPanel
		JPanel midFirstPanel = new JPanel();
		midFirstPanel.add(theSpeed);
		midFirstPanel.add(speed);

		// This is the last row of the firstPanel
		JPanel bottomFirstPanel = new JPanel();
		bottomFirstPanel.add(theSize);
		bottomFirstPanel.add(size);

		firstPanel.add(topFirstPanel);
		firstPanel.add(midFirstPanel);
		firstPanel.add(bottomFirstPanel);

		mainPanel.add(firstPanel);

		// This Panel will be the second column of the mainPanel
		JPanel secondPanel = new JPanel();
		secondPanel.setLayout(new BoxLayout(secondPanel, BoxLayout.Y_AXIS));

		// This Panel will be the third column of the mainPanel
		thirdPanel = new JPanel();
		thirdPanel.setLayout(new BoxLayout(thirdPanel, BoxLayout.Y_AXIS));

		// This is the bottom row of the secondPanel
		JPanel bottomSecondPanel = new JPanel();
		bottomSecondPanel.add(startButton);
		// bottomSecondPanel.add( theNumGenerations );
		bottomSecondPanel.add(numGenerations);

		// This is the top row of the secondPanel
		JPanel topSecondPanel = new JPanel();
		topSecondPanel.add(runButton);
		topSecondPanel.add(pauseButton);

		// This is the middle row of the secondPanel
		JPanel midSecondPanel = new JPanel();
		midSecondPanel.add(resetButton);
		midSecondPanel.add(clearButton);

		secondPanel.add(topSecondPanel);
		secondPanel.add(midSecondPanel);
		secondPanel.add(bottomSecondPanel);

		mainPanel.add(secondPanel);

		// This is the top of the third panel
		JPanel topThirdPanel = new JPanel();
		topThirdPanel.add(theAge);

		thirdPanel.add(topThirdPanel);
		// thirdPanel.add( midThirdPanel );
		// thirdPanel.add( bottomThirdPanel );

		mainPanel.add(thirdPanel);

		return mainPanel;

	}

	public static void stepAge() {
		age++;
		theAge.setText("# of Generations:  " + age);
	}

	public static void loadAnt() {

		thirdPanel.add(antControl);
	}

	public static void unloadAnt() {
		if (!getGames().equals("Langton's Ant"))
			thirdPanel.remove(antControl);
	}

	public JMenuBar myMenuBar() {

		menuBar = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenu rules = new JMenu("Rules");
		JMenuItem uploadFile = new JMenuItem("Load File");
		uploadFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int returnVal = fileChooser.showOpenDialog(frame);
				if (returnVal == JFileChooser.APPROVE_OPTION) {

					fileName = fileChooser.getSelectedFile();
					try {

						fileName = fileChooser.getSelectedFile();
						uploadedFile = true;
						testSimulator.dataGrid = new Grid(fileName);
						games.setSelectedItem(testSimulator.dataGrid.getGame());
						uploadedFile = false;
						testSimulator.showGrid.updateUI();

					} catch (FileNotFoundException e1) {
						JOptionPane.showMessageDialog(frame, "The file does not exist.", "File Does Not Exist",
								JOptionPane.ERROR_MESSAGE);

						// System.out.println("The file does not exist \n");
						e1.printStackTrace();

					}

				} else {
					JOptionPane.showMessageDialog(frame, "The command was cancelled by the user.", "Command Cancelled",
							JOptionPane.ERROR_MESSAGE);

					// System.out.println("Command cancelled by the user. \n");

				}
				return;

			}

		}

		);
		JMenuItem gameOfLifeRules = new JMenuItem("Game of Life Rules");
		gameOfLifeRules.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JOptionPane.showMessageDialog(frame,
						"An Alive Cell will become a Dead Cell if there are fewer than two Alive neighbors \n"
								+ "An Alive Cell will become a Dead Cell if there are greater than three Alive neighbors \n"
								+ "An Alive Cell will stay and Alive Cell if there are only two or three Alive neighbors \n"
								+ "A Dead Cell will become an Alive Cell if there are exactly three Alive neighbors \n\n"
								+ "Black Boxes = Dead Cells \n" + "White Boxes = Alive Cells\n" + "Red Boxes = The Ant",
						"Rules to the Game of Life", JOptionPane.INFORMATION_MESSAGE);
				return;

			}

		}

		);
		JMenuItem briansBrainRules = new JMenuItem("Brian's Brain Rules");
		briansBrainRules.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JOptionPane.showMessageDialog(frame,
						"Any Dying Cells will become Dead cells in the next generation \n"
								+ "Any Dead Cell with exactly two Alive neighbors will become an Alive Cell \n"
								+ "Any Alive Cells will become Dying Cells preventing any Cell from being born there \n\n"
								+ "Dead Cell = Black \n" + "Dying Cell = Blue \n" + "Alive Cell = White",
						"Rules to Brian's Brain", JOptionPane.INFORMATION_MESSAGE);
				return;

			}

		}

		);
		JMenuItem applicationRules = new JMenuItem("Application Rules");
		applicationRules.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JOptionPane.showMessageDialog(frame,
						"You can click a Cell to change the state of that Cell \n"
								+ "Game:  Allows you to choose between the different games each with their own rules \n"
								+ "Width:  Determines how big the Grid is.  Must be between 10 and 100 \n"
								+ "Speed:  Allows you to determine how fast the Generations are computed \n"
								+ "Generations:  How many cycles you would like the program to run for \n"
								+ "Start:  This button will start the program and will end when the number of Generations is met \n"
								+ "Pause:  This button will allow you to freeze the program at a certain generation \n"
								+ "Reset:  This button will set the Grid back to the state before you click play \n"
								+ "Clear:  This button will clear the Grid to a blank Grid \n"
								+ "File:  This will allow you to upload a file with predetermined states to run \n",
						"How to use the Application", JOptionPane.INFORMATION_MESSAGE);
				return;

			}

		}

		);
		JMenuItem langtonsAntRules = new JMenuItem("Langton's Ant Rules");
		langtonsAntRules.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JOptionPane.showMessageDialog(frame,
						"Specify starting point within the parameters of the Grid \n"
								+ "If the Cell is Dead, turn 90 degrees to the left and switch the Cell to Alive \n"
								+ "If the Cell is Alive, turn 90 degrees to the right and switch the Cell to Dead \n\n"
								+ "Dead Cell = Black \n" + "Alive Cell = White",
						"Rules to Langton's Ant", JOptionPane.INFORMATION_MESSAGE);
				return;

			}

		}

		);

		JMenuItem fileFormat = new JMenuItem("File format");
		fileFormat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JOptionPane.showMessageDialog(frame,
						"Proper file format for uploading: \n"
								+ "The name of the game, followed by the width, followed by the grid.\n"
								+"Dead cells are represented by 'D', Live cells by 'A', and Decay cells by 'X' for example:\n\n"
								+ "Game of Life \n" + "10\n" 
								+ "D  D  D  D  D  D  D  D  D  D \n" + "D  D  D  D  D  D  D  D  D  D \n" 
								+ "D  D  D  D  D  D  D  D  D  D \n" + "D  D  A  D  A  D  A  A  A  D \n" 
								+ "D  D  A  A  A  D  D  A  D  D \n" + "D  D  A  D  A  D  D  A  D  D \n" 
								+ "D  D  A  D  A  D  A  A  A  D \n" + "D  D  D  D  D  D  D  D  D  D \n" 
								+ "D  D  D  D  D  D  D  D  D  D \n" + "D  D  D  D  D  D  D  D  D  D \n\n"
								+ "(this example will create a Game of life grid saying 'hi')",
						"File Format", JOptionPane.INFORMATION_MESSAGE);
				return;

			}

		}

		);

		file.add(uploadFile);
		rules.add(applicationRules);
		rules.add(gameOfLifeRules);
		rules.add(briansBrainRules);
		rules.add(langtonsAntRules);
		rules.add(fileFormat);
		menuBar.add(file);
		menuBar.add(rules);

		return menuBar;

	}

	public static String getGames() {

		if (games.getSelectedItem().equals("Game of Life")) {

			return "Game of Life";

		} else if (games.getSelectedItem().equals("Langton's Ant")) {

			return "Langton's Ant";

		} else if (games.getSelectedItem().equals("Brian's Brain")) {

			return "Brian's Brain";

		}

		return "Game of Life";

	}

}

class generate extends Thread {
	int n;

	Grid old;

	public generate(Grid old) {
		this.old = old;

		n = 0;
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

	}

}

class stepWorld extends SwingWorker<Integer, Integer> {

	@Override
	protected Integer doInBackground() throws Exception {
		Grid even = testSimulator.dataGrid;
		Grid odd = new Grid(even.getWidth());
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

			// System.out.println("Invalid input. Please try again.");

		}

		for (int i = 0; i < generations; i++) {

			if ((i % 2) == 0) {

				Thread update = new Thread(new updateWorld(even, odd));
				update.start();
				synchronized (update) {
					try {
						update.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			} else {

				Thread update = new Thread(new updateWorld(odd, even));
				update.start();
				synchronized (update) {
					try {

						update.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
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

class updateWorld extends SwingWorker<Integer, Integer> {
	Grid oldGrid;
	Grid newGrid;

	public updateWorld(Grid oldGrid, Grid newGrid) {
		this.oldGrid = oldGrid;
		this.newGrid = newGrid;
	}

	@Override
	protected Integer doInBackground() throws Exception {
		// TODO Auto-generated method stub
		synchronized (this) {

			for (int i = 0; i < oldGrid.getWidth(); i++) {

				for (int j = 0; j < oldGrid.getWidth(); j++) {

					newGrid.board[i][j] = oldGrid.board[i][j].next(newGrid);

				}

			}
			testSimulator.dataGrid = newGrid;

			testSimulator.showGrid.revalidate();
			testSimulator.showGrid.updateUI();

			Simulator.stepAge();
			notify();
		}
		return null;
	}
}

class runWorld extends SwingWorker<Integer, Integer> {

	@Override
	protected Integer doInBackground() throws Exception {
		Grid even = testSimulator.dataGrid;
		Grid odd = new Grid(even.getWidth());
		int i = 0;
		while (true) {

			if ((i % 2) == 0) {

				Thread update = new Thread(new updateWorld(even, odd));
				update.start();
				synchronized (update) {
					try {

						update.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			} else {

				Thread update = new Thread(new updateWorld(odd, even));
				update.start();
				synchronized (update) {
					try {

						update.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
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

			i++;

		}

	}

}

class antUpdate extends SwingWorker<Integer, Integer> {

	@Override
	protected Integer doInBackground() throws Exception {
		// TODO Auto-generated method stub
		synchronized (this) {

			testSimulator.showGrid.revalidate();
			testSimulator.showGrid.updateUI();

			notify();
		}
		return null;
	}
}