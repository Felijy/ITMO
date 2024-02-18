import commandsHendler.commands.*;
import data.Ticket;
import handler.CommandHandler;
import handler.IOHandler;
import handler.mapHandler;
import handler.Run;
import commandsHendler.commands.Insert;

import java.util.HashMap;


public class Main {
    public static void main(String[] args) {
        IOHandler ioHandler = new IOHandler("data.json");
        Run run = new Run();
        mapHandler.setCollection((HashMap<String, Ticket>) ioHandler.getJSON());
        CommandHandler.addCommand("insert", new Insert());
        CommandHandler.addCommand("show", new Show());
        CommandHandler.addCommand("update", new Update());
        CommandHandler.addCommand("remove_key", new Remove());
        CommandHandler.addCommand("clear", new Clear());
        CommandHandler.addCommand("exit", new Exit());
        CommandHandler.addCommand("help", new Help());
        CommandHandler.addCommand("save", new Save());
        CommandHandler.addCommand("execute_script", new Execute());
        CommandHandler.addCommand("history", new History());
        CommandHandler.addCommand("count_by_price", new CountPrice());
        run.userCommandFetch();
    }
}