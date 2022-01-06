package ru.vorobev.tictactoe;

public class StartGame {
	
	//TODO если все поля заполнены - ничья (4x4)
	private static final int FIELD_SIZE = 4;
	
	public static void main(String[] args) {
		start();
	}
	
	public static void start() {
		GameField gameField = new GameField(FIELD_SIZE);
		Game game = new Game(gameField);
	}
}
