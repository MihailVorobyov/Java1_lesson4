package ru.vorobev.tictactoe;

public interface Player {
	
	void makeStep(int x, int y);
	void checkForWin();

}
