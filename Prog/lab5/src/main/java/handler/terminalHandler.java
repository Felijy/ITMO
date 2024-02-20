package handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Класс для работы с терминалом (вводом-выводом)
 */
public class terminalHandler {
    /**
     * Поле, которое хранит Scanner для ввода из терминала
     */
    private static Scanner in;

    /**
     * Поле, которое хранит Scanner для ввода из файла
     */
    private static Scanner inFile;

    /**
     * Поле, сообщающее, происходит сейчас чтение из файла (true) или из терминала (false)
     */
    private static boolean fromFile = false;

    /**
     * Поле, которые хранит файл с командами
     */
    private static File currentFile;

    static {
        in = new Scanner(System.in);
    }

    /**
     * Метод для чтения строки из терминала или файла
     * @return строка, которая была прочитана
     */
    public static String readLine() {
        if (in.hasNextLine()) {
            String input = in.nextLine();
            if (input.isEmpty()) return null;
            return input;
        } else {
            if (fromFile) {
                in = new Scanner(System.in);
                fromFile = false;
            } else System.exit(0);
        }
        return null;
    }

    /**
     * Метод, который устанавливает файл, из которого будет производится чтение команд
     * @param file файл, из которого читаются команды
     * @throws FileNotFoundException выбрасывается, если такого файла не существует или к нему нет доступа
     */
    public static void setFile(File file) throws FileNotFoundException {
        fromFile = true;
        currentFile = file;
        inFile = new Scanner(currentFile);
        in = inFile;
    }

    public static boolean getFromFile() {
        return fromFile;
    }

    /**
     * Выводит в терминал без знака нового абзаца в конце строки (не выводит ничего, если происходит чтение из файла)
     * @param str то, что нужно вывести в терминал
     */
    public static void print(String str) {
        if (!fromFile) System.out.print(str);
    }

    /**
     * Выводит в терминал со знаком нового абзаца в конце (не выводит ничего, если происходит чтение из файла)
     * @param str то, что нужно вывести в терминал
     */
    public static void println(String str) {
        if (!fromFile) System.out.println(str);
    }

    /**
     * Выводит в терминал всегда (обычно это либо результат команды, либо ошибка)
     * @param str то, что нужно вывести
     */
    public static void printlnA(String str) {
        System.out.println(str);
    }

}
