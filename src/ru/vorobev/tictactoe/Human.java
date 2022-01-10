package ru.vorobev.tictactoe;

public class Human {
	
	private final char X_DOT = 'X';
	
	private final GameField gameField;
	
	public Human() {
		this.gameField = GameField.getInstance();
	}
	
	public boolean makeStep(int x, int y) {
		if (gameField.isCellValid(x, y)) {
			gameField.setCell(x,y,X_DOT);
			return true;
		}
		return false;
	}
}
