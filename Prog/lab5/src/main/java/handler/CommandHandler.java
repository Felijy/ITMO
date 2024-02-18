package handler;

import commandsHendler.Command;
import exceptions.IncorrectInputException;
import exceptions.ArgumentException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandHandler {
    private static final Map<String, Command> commands;
    private static List<String> history;

    static {
        commands = new HashMap<>();
        history = new ArrayList<>();
    }

    public static void addCommand(String commandName, Command command) {
        commands.put(commandName, command);
    }

    public static Command getCommand(String arg) throws IncorrectInputException {
        var command = commands.get(arg);
        if (command == null) throw new IncorrectInputException();
        else history.add(arg);
        return commands.get(arg);
    }

    public static Map<String, Command> getMap() {
        return commands;
    }

    public static void printHistory(int count) {
        terminalHandler.printlnA("Список последних 15 команд:");
        if (history.size() < count) {
            for (int i=0; i<history.size(); i++) {
                terminalHandler.printlnA(i + 1 + ". " + history.get(i));
            }
        } else {
            for (int i=history.size()-count; i<history.size(); i++) {
                terminalHandler.printlnA(i- history.size()+count + 1 + ". " + history.get(i));
            }
        }
    }
}
