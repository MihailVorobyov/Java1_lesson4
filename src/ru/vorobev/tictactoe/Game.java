package ru.vorobev.tictactoe;

public class Game {

	private final GameField gameField;
	private final GUI gui;
	private final WinChecker winChecker;

	private final Human human;
	private final Computer computer;
	
	public Game(GUI gui) {
		this.gameField = GameField.getInstance();
		this.gui = gui;
		this.winChecker = new WinChecker();
		this.human = new Human();
		this.computer = new Computer();
	}
	
	void play(int hx, int hy) {
		if (!human.makeStep(hx, hy)) {
			return;
		}
		gui.setButtonsValues();
		if (checkForWin(Settings.getX_DOT())) {
			return;
		}
		
		computer.makeStep();
		gui.setButtonsValues();
		checkForWin(Settings.getO_DOT());
	}
	
	private boolean checkForWin(char character) {
		if (winChecker.isWin(character)) {
			if (character == Settings.getX_DOT()) {
				gui.setLabelText("Победил пользователь");
			} else {
				gui.setLabelText("Победил компьютер");
			}
			gui.setButtonsDisabled();
			return true;
		} else if (gameField.isFilled()) {
			gui.setLabelText("Ничья");
			gui.setButtonsDisabled();
			return true;
		}
		return false;
	}
}
