package ru.vorobev.tictactoe;

public class StartGame {
	
	//TODO если все поля заполнены - ничья
	private static final int FIELD_SIZE = 4;
	
	public static void main(String[] args) {
		start();
	}
	
	public static void start() {
		Game game = new Game(FIELD_SIZE);
		GUI gui = new GUI(FIELD_SIZE, game);
	}
}
