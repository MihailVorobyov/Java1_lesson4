package x_o;

import java.util.Arrays;
import java.util.Random;

public class Game {

    private final char X_DOT = 'X';
    private final char O_DOT = 'O';
    private final char EMPTY_DOT = '•';

    private int SIZE;
    private GUI gui;

    private char[][] map;
    private Random random = new Random();

    public Game(int size) {
        this.SIZE = size;
        initMap();
    }

    public char[][] getMap() {
        return map;
    }

    public char getX_DOT() {
        return X_DOT;
    }

    public char getO_DOT() {
        return O_DOT;
    }

    public void setGui (GUI g) {
        this.gui = g;
    }

    /************************_initMap_****************************/
    public  void initMap() {
        // Инициализация пустого массива
         map = new char[SIZE][SIZE];
        for (char[] c : map) {
            Arrays.fill(c, EMPTY_DOT);
        }
    }

    /************************_winCheck_****************************/
    public boolean winCheck(char dot) {
        int winCombo = 0;

        // Проверяем выигрышные комбинации

        // проверяем горизонтали
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == dot) {
                    if (SIZE == 5) {
                        if (++winCombo == SIZE - 1) {
                            return true;
                        }
                    } else {
                        if (++winCombo == SIZE) {
                            return true;
                        }
                    }
                }
            }
            winCombo = 0;
        }

        // проверяем вертикали
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[j][i] == dot) {
                    if (SIZE == 5) {
                        if (++winCombo == SIZE - 1) {
                            return true;
                        }
                    } else {
                        if (++winCombo == SIZE) {
                            return true;
                        }
                    }
                }
            }
            winCombo = 0;
        }

        // проверяем диагональ слева сверху направо вниз
        for (int i = 0; i < SIZE; i++) {
            if (SIZE == 5) {
                if (map[i][i] == dot && ++winCombo == SIZE - 1) {
                    return true;
                }
            } else if (map[i][i] == dot && ++winCombo == SIZE) {
                return true;
            }
        }
        winCombo = 0;

        // проверяем диагональ слева сниузу направо вверх
        for (int i = 0; i < SIZE; i++) {
            if (SIZE == 5) {
                if (map[SIZE - i - 1][i] == dot && ++winCombo == SIZE - 1) {
                    return true;
                }
            }else if (map[SIZE - i - 1][i] == dot && ++winCombo == SIZE) {
                return true;
            }
        }

        return false;
    }

    /************************_isMapFilled_****************************/
    public  boolean isMapFilled() {

        // Проверяет, все ли поля заполнены
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == EMPTY_DOT) {
                    return false;
                }
            }
        }

        gui.setLabelText("Ничья");
        System.out.println("Ничья");
        return true;
    }

    /************************_humanStep_****************************/
    public  void humanStep(int x, int y) {
        if (isCellValid(x, y)) {
            map[y][x] = X_DOT;
            printMap();
            gui.setButtonsValues(map);
            humanCheck();
        }
    }



    public void humanCheck () {
        if (winCheck(X_DOT)) {
            gui.setLabelText("Победил пользователь");
            gui.setButtonsDisabled();
        } else if (isMapFilled()) {
            gui.setButtonsDisabled();
        } else {
            try {
                aiStep();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void aiCheck () {
        if (winCheck(O_DOT)) {
            gui.setLabelText("Победил компьютер");
            gui.setButtonsDisabled();
        } else if (isMapFilled()) {
            gui.setButtonsDisabled();
        }
    }

    /***************************** Логика ИИ *******************************/
    public  void aiStep() throws InterruptedException {
        Thread.sleep(300);
        int xCoordinate;
        int yCoordinate;

        if (humanCanWin()) {
            trickyStep();
        } else {

            do {
                xCoordinate = random.nextInt(SIZE);
                yCoordinate = random.nextInt(SIZE);
            } while (!isCellValid(xCoordinate, yCoordinate));

            map[yCoordinate][xCoordinate] = O_DOT;
            System.out.println("Случайный ход компьютера: x = " + (xCoordinate + 1) + ", y =" + (yCoordinate + 1));
        }

        printMap();
        gui.setButtonsValues(map);
    }

    public  void trickyStep () {
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
                            if (isCellValid(randomDot, result[rand])) {
                                map[result[rand]][randomDot] = O_DOT; // randomDot - номер столбца (x),
                                // [result[rand]] - номер строки (y)
                                isStepMade = true;
                                System.out.println("Компьютер сделал хитрый ход #1. x = " + (randomDot + 1) + ", y = " +
                                        " " + (result[rand] + 1));
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
                            if (isCellValid(result[rand], randomDot)) {
                                map[randomDot][result[rand]] = O_DOT; // randomDot - номер строки (y),
                                // [result[rand]] - номер столбца (x)
                                isStepMade = true;
                                System.out.println("Компьютер сделал хитрый ход #2. x = " + (result[rand] + 1) + ", y = " + (randomDot + 1));
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
                    } else if (isCellValid(rand, rand)) {
                        map[rand][rand] = O_DOT;
                        isStepMade = true;
                        System.out.println("Компьютер сделал хитрый ход #3. x = " + (rand + 1) + ", y = " + (rand + 1));
                        break;
                    }
                }

                // ********** ИИ ходит по диагонали лево-низ вправо-вверх **********
            } else if (rnd == 3) {
                while (true) {
                    rand = random.nextInt(SIZE);
                    result = checkDiagonalBottomToTop();
                    if (result[0] == -1) {
                        break;
                    } else if (isCellValid(rand, (SIZE - rand - 1))) {
                        map[SIZE - rand - 1][rand] = O_DOT;
                        isStepMade = true;
                        System.out.println("Компьютер сделал хитрый ход #4. x = " + (rand + 1) + ", y = " + (SIZE - rand));
                        break;
                    }
                }
            }
        }

        aiCheck();
    }


    public  boolean humanCanWin () {
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
        if (result[0] != -1) {
            return true;
        }

        return false;
    }


    public  int[] checkRows(){
        int humanWinCombo = 0;
        int rowIndex = 0;

        int[] row = new int[SIZE];
        Arrays.fill(row, -1);

        for (int y = 0; y < SIZE; y++) {
            humanWinCombo = 0;
            for (int x = 0; x < SIZE; x++) {
                if (map[y][x] == X_DOT) {
                    humanWinCombo++;
                } else if (map[y][x] == O_DOT) {
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
    
    public  int[] checkColumns() {
        int humanWinCombo = 0;
        int columnIndex = 0;

        int[] column = new int[SIZE];
        Arrays.fill(column, -1);

        for (int x = 0; x < SIZE; x++) {
            humanWinCombo = 0;
            for (int y = 0; y < SIZE; y++) {
                if (map[y][x] == X_DOT) {
                    humanWinCombo++;
                } else if (map[y][x] == O_DOT) {
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

    public  int[] checkDiagonalTopToBottom() {
        int humanWinCombo = 0;
        int[] topToBottomDiagonal = {-1};

        for (int x = 0; x < SIZE; x++) {
            if (map[x][x] == X_DOT) {
                humanWinCombo++;
            } else if (map[x][x] == O_DOT) {
                humanWinCombo = 0;
                break;
            }
        }
        if (humanWinCombo >= 2) {
            topToBottomDiagonal[0] = 1;
        }
        return topToBottomDiagonal;
    }

    public  int[] checkDiagonalBottomToTop() {
        int humanWinCombo = 0;
        int[] bottomToTopDiagonal = {-1};

        for (int x = 0; x < SIZE; x++) {
            if (map[SIZE - x -1][x] == X_DOT) {
                humanWinCombo++;
            } else if (map[SIZE - x -1][x] == O_DOT) {
                humanWinCombo = 0;
                break;
            }
        }
        if (humanWinCombo >= 2) {
            bottomToTopDiagonal[0] = 1;
        }
        return bottomToTopDiagonal;
    }

    /*************************** Конец логики ИИ *********************************/

    public  boolean isCellValid(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
            System.out.println("Неверные координаты!");
            return false;
        }
        if (map[y][x] != EMPTY_DOT) {
            return false;
        }
        return true;
    }

    private void printMap() {
        // Вывод шапки
        System.out.print(" ");
        for (int i = 0; i < SIZE; i++) {
            System.out.print(" " + (i + 1));
        }

        System.out.println();

        // Вывод строк
        for (int i = 0; i < SIZE; i++) {
            System.out.print((i + 1) + " ");

            for (int j = 0; j < SIZE; j++) {
                System.out.print(map[i][j] + " ");
            }

            System.out.println("");
        }
        System.out.println("");
    }

}

