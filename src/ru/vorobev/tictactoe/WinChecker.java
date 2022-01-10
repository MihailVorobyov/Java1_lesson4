package ru.vorobev.tictactoe;

public class WinChecker {
	
	private final GameField gameField;
	private final int SIZE;
	
	public WinChecker() {
		this.gameField = GameField.getInstance();
		SIZE = Settings.getFieldSize();
	}
	
	boolean isWin(char dot) {
		char[][] map = gameField.getField();
		int winCombo = 0;
		
		// Проверяем выигрышные комбинации
		
		// проверяем горизонтали
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (map[i][j] == dot) {
					if (SIZE == 5) {
						if (++winCombo == SIZE - 1) {
							return true;
						}
					} else {
						if (++winCombo == SIZE) {
							return true;
						}
					}
				}
			}
			winCombo = 0;
		}
		
		// проверяем вертикали
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (map[j][i] == dot) {
					if (SIZE == 5) {
						if (++winCombo == SIZE - 1) {
							return true;
						}
					} else {
						if (++winCombo == SIZE) {
							return true;
						}
					}
				}
			}
			winCombo = 0;
		}
		
		// проверяем диагональ слева сверху направо вниз
		for (int i = 0; i < SIZE; i++) {
			if (SIZE == 5) {
				if (map[i][i] == dot && ++winCombo == SIZE - 1) {
					return true;
				}
			} else if (map[i][i] == dot && ++winCombo == SIZE) {
				return true;
			}
		}
		winCombo = 0;
		
		// проверяем диагональ слева снизу направо вверх
		for (int i = 0; i < SIZE; i++) {
			if (SIZE == 5) {
				if (map[SIZE - i - 1][i] == dot && ++winCombo == SIZE - 1) {
					return true;
				}
			} else if (map[SIZE - i - 1][i] == dot && ++winCombo == SIZE) {
				return true;
			}
		}
		
		return false;
	}
}
