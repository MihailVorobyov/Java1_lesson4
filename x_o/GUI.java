package x_o;

import javax.swing.*;
import java.awt.*;

public class GUI {

	private JFrame window = new JFrame();
	private JPanel mainPanel = new JPanel();
	private JPanel centerPanel = new JPanel();
	private JMenuBar menuBar = new JMenuBar();
	private JMenu menu = new JMenu("Меню");
	private JMenuItem newGame = new JMenuItem("Новая игра");
	private JMenuItem exit = new JMenuItem("Выход");
	private JLabel label = new JLabel("Игра началась");

	private Game game;
	private final int SIZE;
	private JButton[][] buttons;

	public GUI(int size, Game game) {
		this.SIZE = size;
		this.game = game;
		game.setGui(this);
		initGUI();

	}

	protected void initGUI () {

		mainPanel.setLayout(new BorderLayout());
		centerPanel.setLayout(new GridLayout(3, 3));

		buttons = new JButton[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				int finalJ = j;
				int finalI = i;

				buttons[i][j] = new JButton();
				buttons[i][j].addActionListener(e -> {
					game.humanStep(finalJ, finalI);
				});
				centerPanel.add(buttons[i][j]);
			}
		}

		newGame.addActionListener(e -> Main.go(Main.getSIZE()));
		exit.addActionListener(e -> System.exit(0));
		menu.add(newGame);
		menu.add(exit);
		menuBar.add(menu);
		mainPanel.add(menuBar, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(label, BorderLayout.SOUTH);

		setButtonsValues(game.getMap());

		window.add(mainPanel);
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		window.setBounds(400, 400, 300, 300);
		window.setVisible(true);
	}

	protected void setButtonsValues (char[][] map) {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				buttons[i][j].setText("" + map[i][j]);
			}
		}
	}

	protected void setLabelText (String text) {
		label.setText(text);
	}

	protected void setButtonsDisabled () {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				buttons[i][j].setEnabled(false);
			}
		}
	}
}
