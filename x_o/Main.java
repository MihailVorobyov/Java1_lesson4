package x_o;

public class Main {
	public static int getSIZE() {
		return SIZE;
	}

	private static final int SIZE = 3;

	static Game game;
	static GUI gui;

	public static void main(String[] args) {
		go(SIZE);
	}

	static void go (int size) {

		game = new Game(size);
		gui = new GUI(size, game);

		gui.setButtonsValues(game.getMap());

	}
}


