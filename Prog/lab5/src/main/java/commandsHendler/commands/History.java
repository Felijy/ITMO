package commandsHendler.commands;

import commandsHendler.Command;
import handler.CommandHandler;

/**
 * Команда для вывода истории -- последних 15 выполненных команд
 */
public class History extends Command {
    public History() {
        super(false);
    }

    @Override
    public boolean execute(String args) {
        CommandHandler.printHistory(15);
        return true;
    }

    @Override
    public String getDescription() {
        return "выводит последние 15 команд";
    }

    @Override
    public String getName() {
        return "history";
    }
}
