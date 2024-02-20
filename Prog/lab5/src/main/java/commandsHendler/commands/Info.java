package commandsHendler.commands;

import commandsHendler.Command;
import handler.mapHandler;
import handler.terminalHandler;

import java.time.LocalDateTime;

/**
 * Команда для вывода основной информации о коллекции
 */
public class Info extends Command {
    public Info() {
        super(false);
    }

    @Override
    public boolean execute(String args) {
        terminalHandler.printlnA("Основная информация о коллекции:");
        terminalHandler.printlnA("Инициализация: " + mapHandler.getInitTime());
        terminalHandler.printlnA("Последний доступ: " + mapHandler.getAccessTime());
        terminalHandler.printlnA("Размер: " + mapHandler.getCollection().size());
        terminalHandler.printlnA("Тип данных: " + mapHandler.getCollection().getClass().getName());
        return true;
    }

    @Override
    public String getDescription() {
        return "выводит основную информацию о коллекции";
    }

    @Override
    public String getName() {
        return "info";
    }
}
