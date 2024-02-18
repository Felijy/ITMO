package commandsHendler.commands;

import commandsHendler.Command;
import handler.IOHandler;

public class Save extends Command {
    public Save() {
        super(false);
    }

    @Override
    public boolean execute(String args) {
        IOHandler handler = new IOHandler("data.json");
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
