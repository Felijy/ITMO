package commandsHendler.commands;

import commandsHendler.Command;
import handler.mapHandler;

public class Remove extends Command {
    public Remove() {
        super(true);
    }

    @Override
    public boolean execute(String args) {
        String key = args;
        if (mapHandler.checkKey(key)) return false;
        mapHandler.deleteElement(key);
        return true;
    }

    @Override
    public String getDescription() {
        return "удаляет элемент по заданному ключу";
    }

    @Override
    public String getName() {
        return "remove_key";
    }
}
