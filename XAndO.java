/**
 * @Author Михаил Воробьёв
 * @version 1.2
 *
 * "Искуственный интелект" проверяет выигрышные комбинации человека и мешает ему хитрыми ходами :)
 *
 * Проверка выигрышных комбинаций производится при помощи циклов, что даёт возможность создавать игровое поле любого
 * размера.
 *
 *
 */

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class XAndO {
    private static final char X_DOT = 'X';
    private static final char O_DOT = 'O';
    private static final char EMPTY_DOT = '•';
    private static final int SIZE = 3;

    private static char[][] map = new char[SIZE][SIZE];
    private static Scanner sc = new Scanner(System.in);
    private static Random random = new Random();

    public static void main(String[] args) throws InterruptedException {

        initMap();

        while (true) {
            humanStep();
            if (winCheck(X_DOT)) {
                System.out.println("Победил пользователь");
                break;
            }
            if (isMapFilled()) {
                break;
            }

            aiStep();
            if (winCheck(O_DOT)) {
                System.out.println("Победил компьютер");
                break;
            }
            if (isMapFilled()) {
                break;
            }
        }
        sc.close();
    }

    public static boolean winCheck(char dot) {
        int winCombo = 0;

        // Проверяем выигрышные комбинации

        // проверяем горизонтали
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == dot) {
                    if (++winCombo == SIZE) {
                        return true;
                    }
                }
            }
            winCombo = 0;
        }

        // проверяем вертикали
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[j][i] == dot) {
                    if (++winCombo == SIZE) {
                        return true;
                    }
                }
            }
            winCombo = 0;
        }

        // проверяем диагональ слева сверху направо вниз
        for (int i = 0; i < SIZE; i++) {
            if (map[i][i] == dot && ++winCombo == SIZE) {
                return true;
            }
        }
        winCombo = 0;

        // проверяем диагональ слева сниузу направо вверх
        for (int i = 0; i < SIZE; i++) {
            if (map[SIZE - i - 1][i] == dot && ++winCombo == SIZE) {
                return true;
            }
        }

        return false;
    }

    public static boolean isMapFilled() {

        // Проверяет, все ли поля заполнены
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == EMPTY_DOT) {
                    return false;
                }
            }
        }

        System.out.println("Ничья");
        return true;
    }

    public static void humanStep() {
        int xCoordinate = -1;
        int yCoordinate = -1;

        do {
            System.out.println("Введите коодинаты в формате X Y");
            if (sc.hasNextInt()) {
                xCoordinate = sc.nextInt() - 1;
            }
            if (sc.hasNextInt()) {
                yCoordinate = sc.nextInt() - 1;
            }
            sc.nextLine();

        } while (!isCellValid(xCoordinate, yCoordinate));

        map[yCoordinate][xCoordinate] = X_DOT;
        printMap();
    }

    /***************************** Логика ИИ *******************************/
    public static void aiStep() throws InterruptedException {
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
    }

    public static void trickyStep () {
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
    }


    public static boolean humanCanWin () {
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


    public static int[] checkRows(){
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
    
    public static int[] checkColumns() {
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

    public static int[] checkDiagonalTopToBottom() {
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

    public static int[] checkDiagonalBottomToTop() {
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

    public static boolean isCellValid(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
            System.out.println("Неверные координаты!");
            return false;
        }
        if (map[y][x] != EMPTY_DOT) {
            return false;
        }
        return true;
    }

    public static void initMap() {
        // Инициализация пустого массива
        for (char[] c : map) {
            Arrays.fill(c, EMPTY_DOT);
        }
        printMap();
    }

    public static void printMap () {
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

