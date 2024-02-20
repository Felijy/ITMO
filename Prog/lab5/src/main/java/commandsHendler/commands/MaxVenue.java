package commandsHendler.commands;

import commandsHendler.Command;
import handler.mapHandler;
import handler.terminalHandler;

/**
 * Команда для нахождения элемента с максимальным VenueID в коллекции
 */
public class MaxVenue extends Command {
    public MaxVenue() {
        super(false);
    }

    @Override
    public boolean execute(String args) {
        var collection = mapHandler.getCollection();
        var keys = collection.keySet();
        long maxId = 0;
        for (String i : keys) {
            if (collection.get(i).getVenue() != null) {
                if (collection.get(i).getVenue().getId() > maxId) maxId = collection.get(i).getVenue().getId();
            }
        }
        if (maxId == 0) return false;
        terminalHandler.printlnA("Элемент с максимальным venue:");
        for (String i : keys) {
            if (collection.get(i).getVenue() != null) {
                if (collection.get(i).getVenue().getId() == maxId) {
                    terminalHandler.printlnA("Ticket " + i + ":" + mapHandler.getCollection().get(i).toString());
                }
            }
        }
        return true;
    }

    @Override
    public String getDescription() {
        return "выводит любой элемент коллекции с максимальным venue (по id)";
    }

    @Override
    public String getName() {
        return "max_by_venue";
    }
}
