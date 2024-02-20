package commandsHendler.commands;

import commandsHendler.Command;

import data.Ticket;
import handler.mapHandler;

import java.util.HashMap;

/**
 * Команда для вывода всех элементов коллекции в строковом представлении
 */
public class Show extends Command {

    public Show() {
        super(false);
    }
    /**
     * Выводит все элементы коллекции в строковом представлении
     */
    @Override
    public boolean execute(String args) {
        if (mapHandler.getCollection().isEmpty()) {
            System.out.println("Коллекция пуста");
        } else {
            System.out.println("Все элементы коллекции, по одному в строке:");
            var keys = mapHandler.getCollection().keySet();
            for (String i: keys) {
                System.out.println("Ticket " + i + ":" + mapHandler.getCollection().get(i).toString());
            }
        }
        return true;
    }

    @Override
    public String getDescription() {
        return "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }

    @Override
    public String getName() {
        return "show";
    }
}
