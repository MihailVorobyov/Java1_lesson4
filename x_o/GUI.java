package x_o;

import javax.swing.*;
import java.awt.*;

public class GUI {

	private JFrame window = new JFrame();
	private JPanel mainPanel = new JPanel();
	private JPanel northPanel = new JPanel();
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
		northPanel.setLayout(new GridLayout(3, 3));

		buttons = new JButton[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				int finalJ = j;
				int finalI = i;

				buttons[i][j] = new JButton();
				buttons[i][j].addActionListener(e -> {
					System.out.println("Ход пользователя: x = " + finalJ + " , y = " + finalI);
					game.humanStep(finalJ, finalI);
				});
				northPanel.add(buttons[i][j]);
			}
		}

		mainPanel.add(northPanel, BorderLayout.CENTER);
		mainPanel.add(label, BorderLayout.SOUTH);

		setButtonsValues(game.getMap());

		window.setSize(200, 200);

		window.add(mainPanel);
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
