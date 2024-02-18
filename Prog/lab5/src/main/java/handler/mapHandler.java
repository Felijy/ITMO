package handler;

import data.Coordinates;
import data.Ticket;
import data.Venue;
import data.VenueType;

import java.util.HashMap;

public class mapHandler {

    private static HashMap<String, Ticket> collection;

    static {
        setCollection(new HashMap<>());
    }

    public static HashMap<String, Ticket> getCollection() {
        return collection;
    }

    public static void setCollection(HashMap<String, Ticket> collection) {
        mapHandler.collection = collection;
    }

    public static boolean checkKey(String key) {
        return !collection.containsKey(key);
    }

    public static void makeNewTicket(String key, Ticket obj) {
        collection.put(key, obj);
    }

    public static void deleteElement(String key) {
        collection.remove(key);
    }
}
