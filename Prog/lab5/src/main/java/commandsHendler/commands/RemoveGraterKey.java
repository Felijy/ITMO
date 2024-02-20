package commandsHendler.commands;

import commandsHendler.Command;
import handler.mapHandler;

/**
 * Команда для удаления всех элементов коллекции, которые больше данного по ключу
 */
public class RemoveGraterKey extends Command {
    public RemoveGraterKey() {
        super(true);
    }

    @Override
    public boolean execute(String args) {
        var keys = mapHandler.getCollection().keySet();
        var collection = mapHandler.getCollection();
        if (!keys.contains(args)) return false;
        boolean isDelete = false;
        for (String i : keys) {
            if (isDelete) collection.remove(i);
            if (i.equals(args)) isDelete = true;
        }
        return true;
    }

    @Override
    public String getDescription() {
        return "удаляет элементы, ключ которых больше заданного";
    }

    @Override
    public String getName() {
        return "remove_grater_key <key>";
    }
}
