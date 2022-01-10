package ru.vorobev.tictactoe;

import javax.swing.*;
import java.awt.*;

public class GUI {
	
	private JFrame window = new JFrame();
	private final JPanel mainPanel = new JPanel();
	private final JPanel centerPanel = new JPanel();
	private final JMenuBar menuBar = new JMenuBar();
	private final JMenu menu = new JMenu("Меню");
	private final JMenuItem newGame = new JMenuItem("Новая игра");
	private final JMenuItem exit = new JMenuItem("Выход");
	private final JLabel label = new JLabel("Игра началась");
	
	private final GameField gameField;
	private final int SIZE;
	private JButton[][] buttons;
	private final Game game;
	
	public GUI() {
		this.gameField = GameField.getInstance();
		this.SIZE = Settings.getFieldSize();
		this.game = new Game(this);
		
		initGUI();
	}
	
	protected void initGUI () {
		
		mainPanel.setLayout(new BorderLayout());
		centerPanel.setLayout(new GridLayout(SIZE, SIZE));
		
		buttons = new JButton[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				int finalJ = j;
				int finalI = i;
				
				buttons[i][j] = new JButton();
				buttons[i][j].addActionListener(e -> game.play(finalJ, finalI));
				centerPanel.add(buttons[i][j]);
			}
		}
		
		newGame.addActionListener(e -> {
			gameField.reset();
			window.setVisible(false);
			StartGame.start();
			window = null;
		});
		exit.addActionListener(e -> System.exit(0));
		menu.add(newGame);
		menu.add(exit);
		menuBar.add(menu);
		mainPanel.add(menuBar, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(label, BorderLayout.SOUTH);
		
		setButtonsValues();
		
		window.add(mainPanel);
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		window.setBounds(400, 400, 300, 300);
		window.setVisible(true);
	}
	
	protected void setButtonsValues () {
		char[][] map = gameField.getField();
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
