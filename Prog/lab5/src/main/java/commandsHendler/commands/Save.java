package commandsHendler.commands;

import commandsHendler.Command;
import handler.IOHandler;

/**
 * Команда для сохранения коллекции в JSON файл
 */
public class Save extends Command {
    public Save() {
        super(false);
    }

    @Override
    public boolean execute(String args) {
        IOHandler handler = new IOHandler();
        handler.makeJSON();
        return true;
    }

    @Override
    public String getDescription() {
        return "сохраняет коллекцию в JSON файл";
    }

    @Override
    public String getName() {
        return "save";
    }
}
