package ru.vorobev.tictactoe;

public class Human implements Player {
	
	private final char X_DOT = 'X';
	
	private final GameField gameField;
	private final GUI gui;
	private final WinChecker winChecker;
	
	public Human(GameField gameField, GUI gui, WinChecker winChecker) {
		this.gameField = gameField;
		this.gui = gui;
		this.winChecker = winChecker;
	}
	
	@Override
	public void makeStep(int x, int y) {
		if (gameField.isCellValid(x, y)) {
			gameField.setCell(x,y,X_DOT);
			gui.setButtonsValues(gameField.getField());
			checkForWin();
		}
	}
	
	@Override
	public void checkForWin() {
		if (winChecker.checkForWin(X_DOT)) {
			gui.setLabelText("Победил пользователь");
			gui.setButtonsDisabled();
		} else if (gameField.isFilled()) {
			gui.setButtonsDisabled();
			gui.setLabelText("Ничья");
		} else {
			aiStep();
		}
	}
}
