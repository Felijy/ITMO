package commandsHendler.commands;

import commandsHendler.Command;
import handler.mapHandler;

import java.util.HashMap;

/**
 * Команда для очистки коллекции
 */
public class Clear extends Command {
    public Clear() {
        super(false);
    }

    @Override
    public boolean execute(String args) {
        mapHandler.setCollection(new HashMap<>());
        return true;
    }

    @Override
    public String getDescription() {
        return "удаляет всю коллекцию целиком";
    }

    @Override
    public String getName() {
        return "clear";
    }
}
