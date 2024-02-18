package commandsHendler.commands;

import commandsHendler.Command;

public class Exit extends Command {
    public Exit() {
        super(false);
    }

    @Override
    public boolean execute(String args) {
        System.exit(0);
        return true;
    }

    @Override
    public String getDescription() {
        return "выход без сохранения";
    }

    @Override
    public String getName() {
        return "exit";
    }
}
