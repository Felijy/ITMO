package commandsHendler.commands;

import commandsHendler.Command;
import handler.mapHandler;

/**
 * Команда для удаления элемента по совпадению в нем поля comment с аргументом
 */
public class RemoveByComment extends Command {
    public RemoveByComment() {
        super(true);
    }

    @Override
    public boolean execute(String args) {
        var keys = mapHandler.getCollection().keySet();
        var collection = mapHandler.getCollection();
        for (String i : keys) {
            if (collection.get(i).getComment().equals(args)) {
                collection.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getDescription() {
        return "удаляет первый объект, значение поля comment которого совпадает с аргументом";
    }

    @Override
    public String getName() {
        return "remove_any_by_comment <comment>";
    }
}
