package x_o;

public class Main {
	public static void main(String[] args) {
		int size = 3;

		Main main = new Main();
		main.go(size);
	}

	void go (int size) {

		Game game = new Game(size);
		GUI gui = new GUI(size, game);

		gui.setButtonsValues(game.getMap());

	}
}


