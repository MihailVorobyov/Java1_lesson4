package ru.vorobev.tictactoe;

import java.util.Arrays;

public class GameField {
	private char[][] field;
	private final char EMPTY_DOT = '•';
	
	public GameField(int fieldSize) {
		this.field = new char[fieldSize][fieldSize];
		initField();
	}
	
	//==================== Инициализация игрового поля ====================//
	public  void initField() {
		for (char[] c : field) {
			Arrays.fill(c, EMPTY_DOT);
		}
	}
	
	public char[][] getField() {
		return Arrays.copyOf(field, field.length);
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
