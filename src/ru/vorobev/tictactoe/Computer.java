package ru.vorobev.tictactoe;

import java.util.Arrays;
import java.util.Random;

public class Computer {
	
	private final int SIZE;
	private final char O_DOT = 'O';
	private final char X_DOT = 'X';
	
	private final GameField gameField;
//	private final GUI gui;
	
	private final Random random;
	
	public Computer() {
		SIZE = Util.getFieldSize();
		this.gameField = Util.getGameField();
//		this.gui = Util.getGui(); //TODO
		this.random = new Random();
	}

	public void makeStep() {
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
		
//		gui.setButtonsValues(gameField.getField());
	}
	
	private void trickyStep () {
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
			} else {
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
		
//		checkForWin();
	}
	
	private boolean humanCanWin () {
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
		return result[0] != -1;
	}
	
	private int[] checkRows(){
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
	
	private int[] checkColumns() {
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
	
	private int[] checkDiagonalTopToBottom() {
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
	
	private int[] checkDiagonalBottomToTop() {
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
}
