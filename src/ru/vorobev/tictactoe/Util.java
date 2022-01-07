package ru.vorobev.tictactoe;

public class Util {
	
	
	private static final int FIELD_SIZE = 3;
	
	private static final char X_DOT = 'X';
	private static final char O_DOT = 'O';

	private static GameField gameField;
	private static GUI gui;
	private static WinChecker winChecker;
	private static Human human;
	private static Computer computer;
	private static Game game;
	
	public static void initialize() {
		gameField = new GameField();
		gui = new GUI();
		winChecker = new WinChecker();
		human = new Human();
		computer = new Computer();
		game = new Game();
	}
	
	public static char getX_DOT() {
		return X_DOT;
	}
	
	public static char getO_DOT() {
		return O_DOT;
	}
	
	public static GameField getGameField() {
		return gameField;
	}
	
	public static GUI getGui() {
		return gui;
	}
	
	public static WinChecker getWinChecker() {
		return winChecker;
	}
	
	public static Human getHuman() {
		return human;
	}
	
	public static Computer getComputer() {
		return computer;
	}
	
	public static int getFieldSize() {
		return FIELD_SIZE;
	}
	
	public static Game getGame() {
		return game;
	}
}
