package ru.vorobev.tictactoe;

import java.util.Arrays;
import java.util.Random;

public class Game {
	
	private final char X_DOT = 'X';
	private final char O_DOT = 'O';
	
	private final int SIZE;
	private GUI gui;
	
	private final Random random = new Random();
	
	GameField gameField;
	
	public Game(GameField gameField) {
		this.SIZE = gameField.getField().length;
		this.gameField = gameField;
		gui = new GUI(this, gameField);
	}
	
	
	
	public char getX_DOT() {
		return X_DOT;
	}
	
	public char getO_DOT() {
		return O_DOT;
	}
	
	public void setGui (GUI g) {
		this.gui = g;
	}
	
	//==================== winCheck ====================//
	public boolean winCheck(char dot) {
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
			}else if (map[SIZE - i - 1][i] == dot && ++winCombo == SIZE) {
				return true;
			}
		}
		
		return false;
	}
	
	//==================== isMapFilled ====================//
//	public boolean isMapFilled() {
//		if (gameField.isFilled()) {
//			gui.setLabelText("Ничья");
//			return true;
//		}
//		return false;
//	}
	
	//==================== humanStep ====================//
	public  void humanStep(int x, int y) {
		if (gameField.isCellValid(x, y)) {
			gameField.setCell(x,y,X_DOT);
			gui.setButtonsValues(gameField.getField());
			humanCheck();
		}
	}
	
	
	
	public void humanCheck () {
		if (winCheck(X_DOT)) {
			gui.setLabelText("Победил пользователь");
			gui.setButtonsDisabled();
		} else if (gameField.isFilled()) {
			gui.setButtonsDisabled();
			gui.setLabelText("Ничья");
		} else {
			aiStep();
		}
	}
	
	public void aiCheck () {
		if (winCheck(O_DOT)) {
			gui.setLabelText("Победил компьютер");
			gui.setButtonsDisabled();
		} else if (gameField.isFilled()) {
			gui.setButtonsDisabled();
			gui.setLabelText("Ничья");
		}
	}
	
	//==================== Логика ИИ ====================//
	public  void aiStep() {
		int xCoordinate;
		int yCoordinate;
		
		if (humanCanWin()) {
			trickyStep();
		} else {
			
			do {
				xCoordinate = random.nextInt(SIZE);
				yCoordinate = random.nextInt(SIZE);
			} while (!gameField.isCellValid(xCoordinate, yCoordinate));
			
			gameField.setCell(xCoordinate, yCoordinate, O_DOT);
		}
		
		gui.setButtonsValues(gameField.getField());
	}
	
	public  void trickyStep () {
		int[] result;
		boolean isStepMade = false;
		int rnd;
		int rand = -1;
		int randomDot;
		
		while (!isStepMade) {
			rnd = random.nextInt(4);
			
			// ********** ИИ ходит по горизонтали **********
			if (rnd == 0) {
				while (true) {
					rand = random.nextInt(SIZE);
					result = checkRows();
					if (result[0] == -1) {
						break;
					} else if (result[rand] != -1) {
						while (true) {
							randomDot = random.nextInt(SIZE);
							if (gameField.isCellValid(randomDot, result[rand])) {
								// randomDot - номер столбца (x),
								// [result[rand]] - номер строки (y)
								gameField.setCell(randomDot, result[rand], O_DOT);
//								map[result[rand]][randomDot] = O_DOT;
								isStepMade = true;
								break;
							}
						}
						break;
					}
				}
				
				// ********** ИИ ходит по вертикали **********
			} else if (rnd == 1) {
				while (true) {
					rand = random.nextInt(SIZE);
					result = checkColumns();
					if (result[0] == -1) {
						break;
					} else if (result[rand] != -1) {
						while (true) {
							randomDot = random.nextInt(SIZE);
							if (gameField.isCellValid(result[rand], randomDot)) {
								// randomDot - номер строки (y),
								// [result[rand]] - номер столбца (x)
								gameField.setCell(result[rand], randomDot, O_DOT);
								isStepMade = true;
								break;
							}
						}
						break;
					}
				}
				// ********** ИИ ходит по диагонали лево-верх вправо-вниз **********
			} else if (rnd == 2) {
				while (true) {
					rand = random.nextInt(SIZE);
					result = checkDiagonalTopToBottom();
					if (result[0] == -1) {
						break;
					} else if (gameField.isCellValid(rand, rand)) {
						gameField.setCell(rand, rand, O_DOT);
						isStepMade = true;
						break;
					}
				}
				
				// ********** ИИ ходит по диагонали лево-низ вправо-вверх **********
			} else if (rnd == 3) {
				while (true) {
					rand = random.nextInt(SIZE);
					result = checkDiagonalBottomToTop();
					if (result[0] == -1) {
						break;
					} else if (gameField.isCellValid(rand, (SIZE - rand - 1))) {
						gameField.setCell(rand, SIZE - rand - 1, O_DOT);
						isStepMade = true;
						break;
					}
				}
			}
		}
		
		aiCheck();
	}
	
	
	public boolean humanCanWin () {
		int[] result = new int[SIZE];
		Arrays.fill(result, -1);
		
		result = checkRows();
		if (result[0] != -1) {
			return true;
		}
		
		result = checkColumns();
		if (result[0] != -1) {
			return true;
		}
		
		result = checkDiagonalTopToBottom();
		if (result[0] != -1) {
			return true;
		}
		
		result = checkDiagonalBottomToTop();
		if (result[0] != -1) {
			return true;
		}
		
		return false;
	}
	
	
	public  int[] checkRows(){
		int humanWinCombo = 0;
		int rowIndex = 0;
		
		int[] row = new int[SIZE];
		Arrays.fill(row, -1);
		
		for (int y = 0; y < SIZE; y++) {
			humanWinCombo = 0;
			for (int x = 0; x < SIZE; x++) {
				if (gameField.getCell(x,y) == X_DOT) {
					humanWinCombo++;
				} else if (gameField.getCell(x,y) == O_DOT) {
					humanWinCombo = 0;
					break;
				}
			}
			if (humanWinCombo >= 2) {
				row[rowIndex++] = y;
			}
		}
		return row;
	}
	
	public  int[] checkColumns() {
		int humanWinCombo = 0;
		int columnIndex = 0;
		
		int[] column = new int[SIZE];
		Arrays.fill(column, -1);
		
		for (int x = 0; x < SIZE; x++) {
			humanWinCombo = 0;
			for (int y = 0; y < SIZE; y++) {
				if (gameField.getCell(x,y) == X_DOT) {
					humanWinCombo++;
				} else if (gameField.getCell(x,y) == O_DOT) {
					humanWinCombo = 0;
					break;
				}
			}
			if (humanWinCombo >= 2) {
				column[columnIndex++] = x;
			}
		}
		return column;
	}
	
	public  int[] checkDiagonalTopToBottom() {
		int humanWinCombo = 0;
		int[] topToBottomDiagonal = {-1};
		
		for (int x = 0; x < SIZE; x++) {
			if (gameField.getCell(x,x) == X_DOT) {
				humanWinCombo++;
			} else if (gameField.getCell(x,x) == O_DOT) {
				humanWinCombo = 0;
				break;
			}
		}
		if (humanWinCombo >= 2) {
			topToBottomDiagonal[0] = 1;
		}
		return topToBottomDiagonal;
	}
	
	public  int[] checkDiagonalBottomToTop() {
		int humanWinCombo = 0;
		int[] bottomToTopDiagonal = {-1};
		
		for (int x = 0; x < SIZE; x++) {
			if (gameField.getCell(x,SIZE - x -1) == X_DOT) {
				humanWinCombo++;
			} else if (gameField.getCell(x,SIZE - x -1) == O_DOT) {
				humanWinCombo = 0;
				break;
			}
		}
		if (humanWinCombo >= 2) {
			bottomToTopDiagonal[0] = 1;
		}
		return bottomToTopDiagonal;
	}
	
	//==================== Конец логики ИИ ====================//

}
