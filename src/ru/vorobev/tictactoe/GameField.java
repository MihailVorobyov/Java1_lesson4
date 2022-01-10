package ru.vorobev.tictactoe;

import java.util.Arrays;

public class GameField {
	private final char[][] field;
	private final char EMPTY_DOT = '•';
	
	private static GameField gameField;
	
	public static GameField getInstance() {
		if (gameField == null) {
			gameField = new GameField();
		}
		return gameField;
	}
	
	public GameField() {
		int fieldSize = Settings.getFieldSize();
		this.field = new char[fieldSize][fieldSize];
		initField();
	}
	
	public void reset() {
		gameField = null;
	}
	
	public  void initField() {
		for (char[] c : field) {
			Arrays.fill(c, EMPTY_DOT);
		}
	}
	
	public char[][] getField() {
		return field;
	}
	
	public void setCell(int x, int y, char symbol) {
		this.field[y][x] = symbol;
	}
	
	public char getCell(int x, int y) {
		return field[y][x];
	}
	
	public boolean isFilled() {
		for (int y = 0; y < field.length; y++) {
			for (int x = 0; x < field.length; x++) {
				if (field[y][x] == EMPTY_DOT) {
					return false;
				}
			}
		}
		return true;
	}
	
	public  boolean isCellValid(int x, int y) {
		if (x < 0 || x >= field.length || y < 0 || y >= field.length) {
			return false;
		}
		if (field[y][x] != EMPTY_DOT) {
			return false;
		}
		return true;
	}
	
}
