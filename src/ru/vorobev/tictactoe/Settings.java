package ru.vorobev.tictactoe;

public class Settings {
	
	private static final int FIELD_SIZE = 3;
	private static final char X_DOT = 'X';
	private static final char O_DOT = 'O';

	public static char getX_DOT() {
		return X_DOT;
	}
	
	public static char getO_DOT() {
		return O_DOT;
	}
	
	public static int getFieldSize() {
		return FIELD_SIZE;
	}
}
