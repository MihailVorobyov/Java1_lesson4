/**
 * @Author Михаил Воробьёв
 * @version 1.0
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

    public static void main(String[] args) throws InterruptedException {

        initMap();

        while (true) {
            humanTurn();
            if (winCheck(X_DOT)) {
                System.out.println("Победил пользователь");
                break;
            }
            if (isMapFilled()) {
                break;
            }

            aiTurn();
            if (winCheck(O_DOT)) {
                System.out.println("Победил компьютер");
                break;
            }
            if (isMapFilled()) {
                break;
            }
        }
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

        // проверяем диагонали
        for (int i = 0; i < SIZE; i++) {
            if (map[i][i] == dot && ++winCombo == SIZE) {
                return true;
            }
        }
        winCombo = 0;

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

//        if (winCheck(X_DOT)) {
//            System.out.println("Победил пользователь");
//            return true;
//        } else if (winCheck(O_DOT)) {
//            System.out.println("Победил компьютер");
//            return true;
//        }

        System.out.println("Ничья");
        return true;
    }

    public static void humanTurn() {
        int xCoordinate = -1;
        int yCoordinate = -1;
        Scanner sc = new Scanner(System.in);
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

    public static void aiTurn() throws InterruptedException {
        Thread.sleep(300);

        int xCoordinate;
        int yCoordinate;
        Random random = new Random();

        do {
            xCoordinate = random.nextInt(SIZE);
            yCoordinate = random.nextInt(SIZE);
        } while (!isCellValid(xCoordinate, yCoordinate));

        map[yCoordinate][xCoordinate] = O_DOT;
        printMap();
    }

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

