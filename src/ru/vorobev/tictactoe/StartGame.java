package ru.vorobev.tictactoe;

public class StartGame {
	
	public static void main(String[] args) {
		start();
	}
	
	public static void start() {
		Util.initialize();
		Game game = new Game();
	}
}
