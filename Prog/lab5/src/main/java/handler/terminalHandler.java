package handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class terminalHandler {
    private static Scanner in;
    private static Scanner inFile;
    private static boolean fromFile = false;
    private static File currentFile;

    static {
        in = new Scanner(System.in);
    }

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

    public static void setFile(File file) throws FileNotFoundException {
        fromFile = true;
        currentFile = file;
        inFile = new Scanner(currentFile);
        in = inFile;
    }

    public static boolean getFromFile() {
        return fromFile;
    }

    public static void print(String str) {
        if (!fromFile) System.out.print(str);
    }

    public static void println(String str) {
        if (!fromFile) System.out.println(str);
    }

    public static void printlnA(String str) {
        System.out.println(str);
    }

}
