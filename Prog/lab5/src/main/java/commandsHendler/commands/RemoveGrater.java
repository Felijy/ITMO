package commandsHendler.commands;

import commandsHendler.Command;
import data.Ticket;
import handler.mapHandler;

/**
 * Команда для удаления всех элементов коллекции, которые больше данного (по id класса Ticket)
 */
public class RemoveGrater extends Command {
    public RemoveGrater() {
        super(true);
    }

    @Override
    public boolean execute(String args) {
        if (args.equals("")) return false;
        Long id = Long.parseLong(args);
        var elements = mapHandler.getCollection().values();
        boolean isDelete = false;
        var collection = mapHandler.getCollection();
        for (Ticket i : elements) {
            if (isDelete) {
                var keys = collection.keySet();
                for (String j : keys) {
                    if (collection.get(j) == i) {
                        collection.remove(j);
                        break;
                    }
                }
            }
            if (i.getId().equals(id)) {
                isDelete = true;
            }
        }
        return isDelete;
    }

    @Override
    public String getDescription() {
        return "удаляет все элементы, которые больше заданного (текущий элемент определяется по id)";
    }

    @Override
    public String getName() {
        return "remove_grater <id>";
    }
}
