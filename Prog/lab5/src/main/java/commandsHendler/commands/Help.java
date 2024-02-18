package commandsHendler.commands;

import commandsHendler.Command;
import handler.CommandHandler;


public class Help extends Command {
    public Help() {
        super(false);
    }

    @Override
    public boolean execute(String args) {
        var commands = CommandHandler.getMap();
        var keys = commands.keySet();
        System.out.printf("%-30s%20s\n", "Команда", "Описание");
        for (String i: keys) {
            System.out.printf("%-30s", commands.get(i).getName());
            System.out.printf("%20s\n", commands.get(i).getDescription());
        }
        return true;
    }

    @Override
    public String getDescription() {
        return "вывод справку по командам";
    }

    @Override
    public String getName() {
        return "help";
    }
}
