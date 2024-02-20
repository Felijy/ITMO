package handler;

import commandsHendler.Command;
import exceptions.IncorrectInputException;
import exceptions.ArgumentException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс для работы с коммандами пользователя
 */
public class CommandHandler {
    /**
     * Коллекция для хранения всех команд с объектами этих команд
     */
    private static final Map<String, Command> commands;

    /**
     * Коллекция для хранения истории команд
     */
    private static List<String> history;

    static {
        commands = new HashMap<>();
        history = new ArrayList<>();
    }

    /**
     * Метод для добавления в коллекцию команды
     * @param commandName имя команды, то, как её будет вводить пользователь
     * @param command ссылка на объект этой команды
     */
    public static void addCommand(String commandName, Command command) {
        commands.put(commandName, command);
    }

    /**
     * Метод для получения команды по её названию
     * @param arg название команды, введенное пользователем
     * @return ссылку на объект команды
     * @throws IncorrectInputException выбрасывается, если такой команды не существует
     */
    public static Command getCommand(String arg) throws IncorrectInputException {
        var command = commands.get(arg);
        if (command == null) throw new IncorrectInputException();
        else history.add(arg);
        return commands.get(arg);
    }

    public static Map<String, Command> getMap() {
        return commands;
    }

    /**
     * Метод для печати истории ввода последних N команд
     * @param count количество команд, которые должны быть выведены
     */
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
